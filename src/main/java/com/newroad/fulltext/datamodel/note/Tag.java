package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class Tag implements Serializable {
	private static final long serialVersionUID = 3395264204796428399L;
	private String tagID;
	// clientTagID don't need to store in DB
	private String clientTagID;
	private String tagName;
	private String tagPath;
	private Integer tagType;
	private Integer tagIcon;
	private Integer tagNoteCount;
	/** The is marked. */
	private Boolean isMarked;
	private Integer tagHash;

	private String userID;
	private Long tagVersion;
	private Integer status;

	private Long userCreateTime;
	private Long createTime;
	private Long lastUpdateTime;

	public String getTagID() {
		return tagID;
	}

	public void setTagID(String tagID) {
		this.tagID = tagID;
	}

	public String getClientTagID() {
		return clientTagID;
	}

	public void setClientTagID(String clientTagID) {
		this.clientTagID = clientTagID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagPath() {
		return tagPath;
	}

	public void setTagPath(String tagPath) {
		this.tagPath = tagPath;
	}

	public Integer getTagNoteCount() {
		return tagNoteCount;
	}

	public void setTagNoteCount(Integer tagNoteCount) {
		this.tagNoteCount = tagNoteCount;
	}

	public Boolean getIsMarked() {
		return isMarked;
	}

	public void setIsMarked(Boolean isMarked) {
		this.isMarked = isMarked;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Integer getTagIcon() {
		return tagIcon;
	}

	public void setTagIcon(Integer tagIcon) {
		this.tagIcon = tagIcon;
	}

    public Integer getTagHash() {
    return tagHash;
  }

  public void setTagHash(Integer tagHash) {
    this.tagHash = tagHash;
  }

	public Long getTagVersion() {
		return tagVersion;
	}

	public void setTagVersion(Long tagVersion) {
		this.tagVersion = tagVersion;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserCreateTime() {
		return userCreateTime;
	}

	public void setUserCreateTime(Long userCreateTime) {
		this.userCreateTime = userCreateTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * toString方法 返回对象的json形式
	 */
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
		result = prime * result + ((tagIcon == null) ? 0 : tagIcon.hashCode());
		result = prime * result + ((tagType == null) ? 0 : tagType.hashCode());
		return result;
	}
}
