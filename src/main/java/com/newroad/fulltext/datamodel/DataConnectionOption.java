package com.newroad.fulltext.datamodel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @info
 * @author tangzj1
 * @date  Sep 5, 2013 
 * @version 
 */
@Component("dataConnectionOption")
public class DataConnectionOption {

	@Value("${product.mongo.db.nodeiplist}")
	private String[] nodeiplist;
	@Value("${product.mongo.db.nodeportlist}")
	private String[] nodeportlist;
	@Value("${product.mongo.db.dbname}")
	private String dbName;
	@Value("${product.mongo.db.username}")
	private String userName;
	@Value("${product.mongo.db.password}")
	private String passWord;
	@Value("${product.mongo.db.connectionsperhost}")
	private Integer connectionsPerHost;
	@Value("${product.mongo.db.threadsallowedtoblock}")
	private Integer threadsAllowedToBlock;
	@Value("${product.mongo.db.connectiontimeout}")
	private Integer connectionTimeOut = 5 * 1000;
	@Value("${product.mongo.db.maxretrytime}")
	private Integer maxRetryTime = 5 * 1000;
	@Value("${product.mongo.db.sockettimeout}")
	private Integer socketTimeOut = 60 * 1000;
	
	public Boolean isUseDynamicPorts() {
		return true;
	}
	public String[] getNodeiplist() {
		return nodeiplist;
	}

	public void setNodeiplist(String[] nodeiplist) {
		this.nodeiplist = nodeiplist;
	}

	public String[] getNodeportlist() {
		return nodeportlist;
	}

	public void setNodeportlist(String[] nodeportlist) {
		this.nodeportlist = nodeportlist;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public Integer getThreadsAllowedToBlock() {
		return threadsAllowedToBlock;
	}

	public void setThreadsAllowedToBlock(Integer threadsAllowedToBlock) {
		this.threadsAllowedToBlock = threadsAllowedToBlock;
	}

	public Integer getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(Integer connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public Integer getMaxRetryTime() {
		return maxRetryTime;
	}

	public void setMaxRetryTime(Integer maxRetryTime) {
		this.maxRetryTime = maxRetryTime;
	}

	public Integer getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(Integer socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}

}
