package com.newroad.fulltext.search;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.newroad.fulltext.client.SearchServerClient;

/**
 * @info Search Manager
 * @author tangzj1
 * @date Aug 26, 2013
 * @version
 */

public class SearchManager {

  public static float similarity = 0.5f;

  private SearchServerClient searchClient;

  public void setSearchClient(SearchServerClient searchClient) {
    this.searchClient = searchClient;
  }

  public List<?> simpleSearch(String query, SearchCriteria searchCriteria) {
    SearchRequestBuilder searchbuilder =
        searchClient.getESClient().prepareSearch(searchCriteria.getIndex());
    // Query String Builder
    QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(query);
    String[] fields = searchCriteria.getFieldNames();
    if (fields != null) {
      for (String entry : fields) {
        qsqb.field(entry);
      }
    }
    searchbuilder.setQuery(qsqb);

    int searchFrom = searchCriteria.getSearchFrom();
    int searchRange = searchCriteria.getSearchRange();
    searchbuilder.addHighlightedField(query).setFrom(searchFrom).setSize(searchRange)
        .setExplain(true);
    SearchResponse response = searchbuilder.execute().actionGet();
    List<?> result = searchHits(response, searchFrom, searchRange);
    return result;
  }

  public List<?> conditionSearch(String query, SearchCriteria searchCriteria) {
    SearchRequestBuilder searchbuilder =
        searchClient.getESClient().prepareSearch(searchCriteria.getIndex());
    BoolQueryBuilder queryBuilder = boolQuery();

    Map<String, Object> conditions = searchCriteria.getLimitCondition();
    if (conditions != null) {
      Set<Entry<String, Object>> conditionSet = conditions.entrySet();
      Iterator<Entry<String, Object>> iter = conditionSet.iterator();
      while (iter.hasNext()) {
        Entry<String, Object> entry = iter.next();
        queryBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
      }
    }

    queryBuilder.minimumNumberShouldMatch(1);
    String[] fields = searchCriteria.getFieldNames();
    if (fields != null) {
      // Fuzziness fuzz=Fuzziness.fromSimilarity(similarity);
      for (String field : fields) {
        // queryBuilder.should(QueryBuilders.fuzzyQuery(field,
// query).fuzziness(fuzz).prefixLength(0));
        queryBuilder.should(QueryBuilders.fuzzyQuery(field, query).fuzziness(Fuzziness.AUTO));
      }
      queryBuilder.boost(1.0f);
    }

    int searchFrom = searchCriteria.getSearchFrom();
    int searchRange = searchCriteria.getSearchRange();

    searchbuilder.setQuery(queryBuilder);
    searchbuilder.addHighlightedField(query).setFrom(searchFrom).setSize(searchRange)
        .setExplain(true);
    SearchResponse response = searchbuilder.execute().actionGet();
    List<?> result = searchHits(response, searchFrom, searchRange);
    return result;
  }

  public List<?> multiSearch(String query, SearchCriteria... searchCriterias) {
    MultiSearchRequestBuilder multiSBuilder = searchClient.getESClient().prepareMultiSearch();
    for (SearchCriteria searchCriteria : searchCriterias) {
      SearchRequestBuilder searchbuilder =
          searchClient.getESClient().prepareSearch(searchCriteria.getIndex());
      QueryStringQueryBuilder qsqb = new QueryStringQueryBuilder(query);
      String[] fields = searchCriteria.getFieldNames();
      for (String entry : fields) {
        qsqb.field(entry);
      }
      searchbuilder.setQuery(qsqb);
      searchbuilder.setFrom(searchCriteria.getSearchFrom())
          .setSize(searchCriteria.getSearchRange()).setExplain(true);
      multiSBuilder.add(searchbuilder);
    }
    MultiSearchResponse multiresponse = multiSBuilder.execute().actionGet();
    List<?> result = multiSearchHits(multiresponse);
    return result;
  }

  public List<Map<String, Object>> searchHits(SearchResponse response, int from, int size) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    SearchHits searchHits = response.getHits();
    // long hitsCount = searchHits.getTotalHits();
    SearchHit[] hitList = searchHits.hits();
    if (hitList != null) {
      int hitNum = hitList.length;
      for (int i = 0; i < hitNum; i++) {
        Map<String, Object> hitObj = searchHits.getAt(i).getSource();
        // Set<Map.Entry<String, Object>> set=hitObj.entrySet();
        // Iterator<Entry<String, Object>> iter=set.iterator();
        // while(iter.hasNext()){
        // Entry<String, Object> entry=iter.next();
        // System.out.println(entry.getKey()+":"+entry.getValue());
        // }
        resultList.add(hitObj);
      }
    }
    return resultList;

  }

  public List<?> multiSearchHits(MultiSearchResponse multiresponse) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    Iterator<Item> iter = multiresponse.iterator();
    while (iter.hasNext()) {
      Item item = iter.next();
      SearchResponse response = item.getResponse();
      SearchHits hits = response.getHits();
      long hitsCount = hits.getTotalHits();
      for (int i = 0; i < hitsCount; i++) {
        Map<String, Object> hitObj = hits.getAt(i).getSource();
        resultList.add(hitObj);
      }
    }
    return resultList;

  }

}
