package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

import com.newroad.util.cosure.Convertable;

public class FileData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8758508746358605847L;
	
	private String token;
	private String bucketName;
	private String keyID;
	private String fileName;
	private String contentType;
	private String link;
	private String publicLink;
	private String fileCachePath;

	private Long cloudSize;
	private Long offset;
	// callback status
	private Integer cloudStatus;

	public static final Convertable<FileData, String> TO_KEY = new Convertable<FileData, String>() {
	    @Override
	    public String convert(FileData q) {
	      return q.getKeyID();
	    }
	  };
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getKeyID() {
		return keyID;
	}

	public void setKeyID(String keyID) {
		this.keyID = keyID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPublicLink() {
		return publicLink;
	}

	public void setPublicLink(String publicLink) {
		this.publicLink = publicLink;
	}

	public String getFileCachePath() {
		return fileCachePath;
	}

	public void setFileCachePath(String fileCachePath) {
		this.fileCachePath = fileCachePath;
	}

	public Long getCloudSize() {
		return cloudSize;
	}

	public void setCloudSize(Long cloudSize) {
		this.cloudSize = cloudSize;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Integer getCloudStatus() {
		return cloudStatus;
	}

	public void setCloudStatus(Integer cloudStatus) {
		this.cloudStatus = cloudStatus;
	}

}
