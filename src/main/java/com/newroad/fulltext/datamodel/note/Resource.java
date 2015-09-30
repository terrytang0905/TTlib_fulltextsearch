package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

import com.newroad.util.cosure.Convertable;
import com.newroad.util.cosure.Statisticable;

/**
 * resource data
 */
public class Resource implements Serializable {
  private static final long serialVersionUID = 4487488225155611401L;

  private String resourceID;
  private String clientResourceID;
  private String resourceName;
  /** file content type or mix type */
  private Integer resourceType;
  private String description;
  private Long size;

  private FileData fileData;
  //private List<ThumbnailCache> thumbnailCacheList;
  // For note resource info
  /** pertain to the specific mixLocalId */
  private String clientMixID;
  private String position;
  private Long startTime;
  /** 录音文件总时长（mix需求） */
  private Integer fullTime;

  // Don't need to be set every time
  private String noteID;
  private String clientNoteID;
  private String userID;
  private Integer status;
  private Long resourceVersion;

  private Long userCreateTime;
  private Long createTime;
  private Long lastUpdateTime;

  /**
   * 闭包 统计大小
   */
  public static final Statisticable<Long, Resource> STATISTIC_SIZE = new Statisticable<Long, Resource>() {
    @Override
    public Long statistic(Long totalSize, Resource resource) {
      if (totalSize == null) {
        totalSize = 0L;
      }
      if (resource == null || resource.getSize() == null) {
        return totalSize;
      }
      return totalSize + resource.getSize();
    }
  };

  public static final Convertable<Resource, String> TO_CLIENT_ID = new Convertable<Resource, String>() {
    @Override
    public String convert(Resource q) {
      return q.getClientResourceID();
    }
  };
  
  public static final Convertable<Resource, FileData> TO_FILE = new Convertable<Resource, FileData>() {
    @Override
    public FileData convert(Resource q) {
      return q.getFileData();
    }
  };


  public String getResourceID() {
    return resourceID;
  }

  public void setResourceID(String resourceID) {
    this.resourceID = resourceID;
  }

  public String getClientResourceID() {
    return clientResourceID;
  }

  public void setClientResourceID(String clientResourceID) {
    this.clientResourceID = clientResourceID;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public Integer getResourceType() {
    return resourceType;
  }

  public void setResourceType(Integer resourceType) {
    this.resourceType = resourceType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public FileData getFileData() {
    return fileData;
  }

  public void setFileData(FileData fileData) {
    this.fileData = fileData;
  }

//  public List<ThumbnailCache> getThumbnailCacheList() {
//    return thumbnailCacheList;
//  }
//
//  public void setThumbnailCacheList(List<ThumbnailCache> thumbnailCacheList) {
//    this.thumbnailCacheList = thumbnailCacheList;
//  }

  public String getClientMixID() {
    return clientMixID;
  }

  public void setClientMixID(String clientMixID) {
    this.clientMixID = clientMixID;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  public Integer getFullTime() {
    return fullTime;
  }

  public void setFullTime(Integer fullTime) {
    this.fullTime = fullTime;
  }

  public String getNoteID() {
    return noteID;
  }

  public void setNoteID(String noteID) {
    this.noteID = noteID;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getResourceVersion() {
    return resourceVersion;
  }

  public void setResourceVersion(Long resourceVersion) {
    this.resourceVersion = resourceVersion;
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

  public String getClientNoteID() {
    return clientNoteID;
  }

  public void setClientNoteID(String clientNoteID) {
    this.clientNoteID = clientNoteID;
  }


}
