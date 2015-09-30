package com.newroad.fulltext.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.newroad.fulltext.index.IndexManager;
import com.newroad.fulltext.search.SearchCriteria;
import com.newroad.fulltext.search.SearchManager;
import com.newroad.fulltext.util.FullTextSearchConstants;
import com.newroad.fulltext.util.LanguageAnalyer;
import com.newroad.fulltext.util.NoteConstants;
import com.newroad.fulltext.util.TransferObjectAssembler;
import com.newroad.util.apiresult.ServiceResult;

/**
 * @info Search Service Implementation
 * @author tangzj1
 * @date Aug 26, 2013
 * @version
 */

public class SearchService implements SearchServiceIf {

  private static Logger logger = LoggerFactory.getLogger(SearchService.class);

  @Value("${es.search.fields}")
  private String[] fieldNames;

  private String userID;

  private String queryKeyword;

  private SearchManager searchManager;

  private IndexManager indexManager;

  public void setSearchManager(SearchManager searchManager) {
    this.searchManager = searchManager;
  }

  public void setIndexManager(IndexManager indexManager) {
    this.indexManager = indexManager;
  }

  @Override
  public ServiceResult<JSONObject> search(String userID, String queryKeyword, Integer searchFrom, Integer searchRange) {
    ServiceResult<JSONObject> sr = new ServiceResult<JSONObject>();
    this.userID = userID;
    this.queryKeyword = queryKeyword;
    String[] indexes = indexManager.getIndexes();
    JSONObject resultJSON = searchNotes(indexes, searchFrom, searchRange);
    JSONObject jsonResult = new JSONObject();
    jsonResult.put(FullTextSearchConstants.SEARCH_RESULT, resultJSON);
    sr.setBusinessResult(jsonResult);
    return sr;
  }

  @SuppressWarnings("unchecked")
  public JSONObject searchNotes(String[] indexes, Integer from, Integer range) {
    JSONArray noteArray = new JSONArray();
    if (userID != null && queryKeyword != null) {
      Map<IndexAnalyzerDict, SearchCriteria> searchCriteriaMap = loadNoteSearchCriterias(indexes, from, range);
      // Search note using the specific index according to language analyzer
      IndexAnalyzerDict indexAnalyzer = LanguageAnalyer.chooseAnalyzer(queryKeyword);
      logger.info("Index Analyzer:" + indexAnalyzer);
      SearchCriteria noteCriteria = searchCriteriaMap.get(indexAnalyzer);

      List<Map<String, Object>> noteResults = (List<Map<String, Object>>) searchManager.conditionSearch(queryKeyword, noteCriteria);
      for (Map<String, Object> note : noteResults) {
        noteAttachInfo(note,searchCriteriaMap.get(IndexAnalyzerDict.TAG),searchCriteriaMap.get(IndexAnalyzerDict.RESOURCE));
        noteArray.add(TransferObjectAssembler.transformSearchNote(note));
      }
    }

    JSONObject object = new JSONObject();
    object.put("notes", noteArray);
    return object;
  }
  
  @SuppressWarnings("unchecked")
  private void noteAttachInfo(Map<String, Object> note,SearchCriteria tagCriteria,SearchCriteria resourceCriteria){
      // Search note related information
      List<String> tagIDList = (List<String>) note.get(NoteConstants.TAG_ID_LIST);
      List<Map<String, Object>> tagresults = null;
      if (tagIDList != null && tagIDList.size() > 0) {
        tagresults = new ArrayList<Map<String, Object>>();
        for (String tagID : tagIDList) {
          List<Map<String, Object>> tagInfo = (List<Map<String, Object>>) searchManager.simpleSearch(tagID, tagCriteria);
          Map<String, Object> tagMap=new HashMap<String,Object>(2);
          tagMap.put(NoteConstants.TAG_ID, tagInfo.get(0).get(NoteConstants.TAG_ID));
          tagMap.put(NoteConstants.TAG_NAME, tagInfo.get(0).get(NoteConstants.TAG_NAME));
          tagresults.add(tagMap);
        }
        note.put(NoteConstants.TAGS, tagresults);
      }
      List<String> clientResourceList = (List<String>) note.get(NoteConstants.RESOURCE_LIST);
      List<Map<String, Object>> resourceInfo=null;
      if(clientResourceList!=null&&clientResourceList.size()>0){
        String styleResourceID=clientResourceList.get(clientResourceList.size()-1);
        resourceInfo = (List<Map<String, Object>>) searchManager.simpleSearch(styleResourceID, resourceCriteria);
        Map<String, Object> resourceMap=new HashMap<String,Object>(3);
        resourceMap.put(NoteConstants.CLIENT_RESOURCE_ID, styleResourceID);
        resourceMap.put(NoteConstants.RESOURCE_NAME, resourceInfo.get(0).get(NoteConstants.RESOURCE_NAME));
        resourceMap.put(NoteConstants.RESOURCE_TYPE, resourceInfo.get(0).get(NoteConstants.RESOURCE_TYPE));
        note.put(NoteConstants.LIST_STYLE, resourceMap);
      }
  }

  private Map<IndexAnalyzerDict, SearchCriteria> loadNoteSearchCriterias(String[] indexes, Integer from, Integer range) {
    Map<IndexAnalyzerDict, SearchCriteria> searchCriteriaMap = new HashMap<IndexAnalyzerDict, SearchCriteria>();
    for (String index : indexes) {
      SearchCriteria criteria = new SearchCriteria();
      Map<String, Object> conditions = new HashMap<String, Object>();
      IndexAnalyzerDict dict = IndexAnalyzerDict.getNoteIndex(index);
      switch (dict) {
        case NOTE:
          criteria.setIndex(index);
          criteria.setFieldNames(fieldNames);
          conditions.put(NoteConstants.USER_ID, userID);
          conditions.put(NoteConstants.STATUS, 1);
          criteria.setLimitCondition(conditions);
          if (from != null && range != null) {
            criteria.setSearchFrom(from);
            criteria.setSearchRange(range);
          }
          break;
        case NOTECN:
          criteria.setIndex(index);
          criteria.setFieldNames(fieldNames);
          conditions.put(NoteConstants.USER_ID, userID);
          conditions.put(NoteConstants.STATUS, 1);
          criteria.setLimitCondition(conditions);
          if (from != null && range != null) {
            criteria.setSearchFrom(from);
            criteria.setSearchRange(range);
          }
          break;
        case RESOURCE:
          criteria.setIndex(index);
          criteria.setFieldNames(new String[] {NoteConstants.CLIENT_RESOURCE_ID});
          break;
        case TAG:
          criteria.setIndex(index);
          criteria.setFieldNames(new String[] {"_id"});
          break;
      }
      searchCriteriaMap.put(dict, criteria);
    }
    return searchCriteriaMap;
  }
}
