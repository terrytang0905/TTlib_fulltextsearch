package com.newroad.fulltext.service;

/**
 * @info
 * @author tangzj1
 * @date Oct 22, 2013
 * @version
 */
public enum IndexAnalyzerDict {
	NOTE("noteindex"), NOTECN("notecnindex"), TAG("tagindex"), RESOURCE("resourceindex");

	String index;

	IndexAnalyzerDict(String index) {
		this.index = index;
	}

	public String getIndex() {
		return index;
	}

	public static IndexAnalyzerDict getNoteIndex(String index) {
		for (IndexAnalyzerDict dict : IndexAnalyzerDict.values()) {
			if (index.equals(dict.getIndex())) {
				return dict;
			}
		}
		return null;
	}
}