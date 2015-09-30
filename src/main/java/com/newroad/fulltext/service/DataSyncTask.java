package com.newroad.fulltext.service;

import org.springframework.stereotype.Service;

import com.newroad.fulltext.index.IndexManager;

/**
 * @info Data sync task
 * @author tangzj1
 * @date  Sep 2, 2013 
 * @version 
 */
@Service
public class DataSyncTask {

	private IndexManager indexManager;

	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	
	public void scheduledDataSync(){
		indexManager.refreshIndexes();
	}
	
}
