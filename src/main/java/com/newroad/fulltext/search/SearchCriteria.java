package com.newroad.fulltext.search;

import java.util.Map;

/**
 * @info SearchCriteria
 * @author tangzj1
 * @date  Sep 8, 2013 
 * @version 
 */
public class SearchCriteria {

	private String searchKeyword;
	
	private String[] index;
	
	private Map<String,Object> fieldMap;
	
	private String[] fieldNames;
	
	private Map<String,Object> limitCondition;

	private int searchFrom = 0;

	private int searchRange = 60;

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String[] getIndex() {
		return index;
	}

	public void setIndex(String... index) {
		this.index = index;
	}

	public Map<String, Object> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public Map<String, Object> getLimitCondition() {
		return limitCondition;
	}

	public void setLimitCondition(Map<String, Object> limitCondition) {
		this.limitCondition = limitCondition;
	}

	public String[] getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String[] fieldNames) {
		this.fieldNames = fieldNames;
	}
	
	public int getSearchFrom() {
		return searchFrom;
	}

	public void setSearchFrom(int searchFrom) {
		this.searchFrom = searchFrom;
	}

	public int getSearchRange() {
		return searchRange;
	}

	public void setSearchRange(int searchRange) {
		this.searchRange = searchRange;
	}
	
	
}
