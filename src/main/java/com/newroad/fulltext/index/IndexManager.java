package com.newroad.fulltext.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.newroad.fulltext.client.SearchServerClient;
import com.newroad.fulltext.datamodel.DataConnectionOption;
import com.newroad.fulltext.datamodel.RiverIndex;
import com.newroad.fulltext.util.FullTextSearchException;
import com.newroad.fulltext.util.JSONHelper;

/**
 * @info Index Manager
 * @author tangzj1
 * @date Aug 26, 2013
 * @version
 */

public class IndexManager implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(IndexManager.class);

	@Value("${es.river.path}")
	private String riverPath;

	@Value("${es.mapping.path}")
	private String mappingPath;

	private static String RIVER = "_river";

	@Autowired
	private DataConnectionOption dataConnectionOption;

	private List<RiverIndex> riverList = null;

	private Map<String, JSONObject> mappingObjects = null;

	private SearchServerClient searchClient;

	public void setSearchClient(SearchServerClient searchClient) {
		this.searchClient = searchClient;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String riverFilePath = this.getClass().getResource(riverPath).getPath();
		String mappingFilePath = this.getClass().getResource(mappingPath)
				.getPath();
		riverList = listRiverSetting(riverFilePath);
		mappingObjects = listMappingSetting(mappingFilePath);
		for (RiverIndex rIndex : riverList) {
			initilizeRiver(rIndex);
		}
	}

	private List<RiverIndex> listRiverSetting(String folderName) {
		List<RiverIndex> rivers = new ArrayList<RiverIndex>();
		File dir = new File(folderName);
		File file[] = dir.listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				String fileName = file[i].getName();
				if (fileName.indexOf("river") > 0) {
					RiverIndex rIndex = new RiverIndex();
					String river = fileName.substring(0,
							fileName.lastIndexOf(".json"));
					String riverSetting = JSONHelper.getJsonSettings(riverPath
							+ "/" + fileName);
					JSONObject jsonSetting = JSONObject
							.fromObject(riverSetting);

					JSONObject jsonIndex = (JSONObject) jsonSetting
							.get("index");
					String index = (String) jsonIndex.get("name");
					String indexType = (String) jsonIndex.get("type");

					rIndex.setRiver(river);
					rIndex.setRiverSetting(riverSetting);
					rIndex.setIndex(index);
					rIndex.setIndexType(indexType);
					rivers.add(rIndex);
				}
			}
		} catch (Exception e) {
			logger.error("fail to load river settings :[" + e + "]!");
		}
		return rivers;
	}

	private Map<String, JSONObject> listMappingSetting(String folderName) {
		Map<String, JSONObject> mappings = new HashMap<String, JSONObject>();
		File dir = new File(folderName);
		File file[] = dir.listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				String fileName = file[i].getName();
				if (fileName.indexOf("mapping") > 0) {
					String mappingSetting = JSONHelper
							.getJsonSettings(mappingPath + "/" + fileName);
					JSONObject jsonSetting = JSONObject
							.fromObject(mappingSetting);
					String name = (String) jsonSetting.names().get(0);
					mappings.put(name, jsonSetting);
				}
			}
		} catch (Exception e) {
			logger.error("fail to load mapping settings :[" + e + "]!");
		}
		return mappings;
	}

	private void initilizeRiver(RiverIndex riverIndex) throws Exception {
		String index = riverIndex.getIndex();
		String indexType = riverIndex.getIndexType();
		boolean hasIndex = searchClient.getESClient().admin().indices()
				.exists(new IndicesExistsRequest(index)).actionGet().isExists();
		if (!hasIndex) {
			// Must create index with it's setting and mapping for index type
			// before creating the river.
			createIndex(index, indexType);
			createRiver(riverIndex.getRiverSetting(), riverIndex.getRiver());
		}
		// Refresh the document using _river index
		refreshRiver();
	}

	public void createRiver(String setting, String river) throws Exception {
		logger.info("Create river [{}]", river);
		// Index the document using _river index and the specific type
		searchClient.getESClient().prepareIndex(RIVER, river, "_meta")
				.setSource(setting).execute().actionGet();

		SearchServerClient.checkClusterHealth();
		GetResponse response = searchClient.getESClient()
				.prepareGet(RIVER, river, "_meta").execute().actionGet();
		if (!response.isExists()) {
			throw new FullTextSearchException("Create River fail!!!");
		}
	}

	public void deleteRiver() {
		for (RiverIndex rIndex : riverList) {
			deleteRiver(rIndex.getRiver());
		}
	}

	public void deleteRiver(String river) {
		logger.info("Delete river [{}]", river);
		//TODO need to check
		searchClient.getESClient().admin().indices()
				.prepareDelete("_river").execute()
				.actionGet();
		SearchServerClient.checkClusterHealth();
	}

	public String[] getIndexes() {
		int size = riverList.size();
		String[] indexes = new String[size];
		for (int i = 0; i < size; i++) {
			indexes[i] = riverList.get(i).getIndex();
		}
		return indexes;
	}

	public void bulkCreateIndex() {

	}

	public void createIndex(String indexName, String type) {
		CreateIndexRequestBuilder cib = searchClient.getESClient().admin()
				.indices().prepareCreate(indexName);
		CreateIndexResponse indexResponse = cib.execute().actionGet();
		SearchServerClient.checkClusterHealth();
		if (indexResponse.isAcknowledged()) {
			JSONObject mappingJSON = mappingObjects.get(indexName);
			if (mappingJSON != null) {
				PutMappingRequest mappingRequest = Requests
						.putMappingRequest(indexName).type(type)
						.source(mappingJSON);
				searchClient.getESClient().admin().indices()
						.putMapping(mappingRequest).actionGet();
			}
		}
	}

	// Mapping creation sample
	@SuppressWarnings("unused")
	private void putIndexMapping(String indexName, String type) {
		XContentBuilder mapping;
		try {
			mapping = XContentFactory.jsonBuilder().startObject()
					.startObject(indexName).startObject("properties")
					.startObject("title").field("type", "string")
					.field("store", "yes").endObject().startObject("content")
					.field("type", "string").field("index", "analyzed")
					.endObject().startObject("type").field("type", "String")
					.endObject().startObject("createTime")
					.field("type", "long").endObject()
					.startObject("lastChangeTime").field("type", "long")
					.endObject().endObject().endObject().endObject();
			PutMappingRequest mappingRequest = Requests
					.putMappingRequest(indexName).type(type).source(mapping);
			searchClient.getESClient().admin().indices()
					.putMapping(mappingRequest).actionGet();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("putIndexMapping IOException:" + e);
		}

	}

	public void refreshRiver() {
		refreshIndex(RIVER);
	}

	public void refreshIndexes() {
		refreshIndex(getIndexes());
	}

	public void refreshIndex(String... indeies) {
		for (String index : indeies) {
			RefreshResponse refreshResponse = searchClient.getESClient()
					.admin().indices().refresh(new RefreshRequest(index))
					.actionGet();
			logger.info("Total shards for refreshing index " + index + ":"
					+ refreshResponse.getTotalShards());
			logger.info("Successful shards for refreshing index " + index + ":"
					+ refreshResponse.getSuccessfulShards());
		}
	}

	public void bulkDeleteIndex() {

	}

	public void deleteIndex() {
		for (RiverIndex rIndex : riverList) {
			deleteIndex(rIndex.getIndex());
		}
	}

	public void deleteIndex(String name) {
		logger.info("Delete index [{}]", name);
		DeleteIndexResponse deleteResponse = searchClient.getESClient().admin()
				.indices().prepareDelete(name).execute().actionGet();
		if (!deleteResponse.isAcknowledged()) {
			logger.error("Counld not delete index: {}. Try waiting 1 sec...",
					name);
		}
		SearchServerClient.checkClusterHealth();
	}

}
