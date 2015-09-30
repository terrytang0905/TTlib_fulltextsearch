package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

import com.newroad.util.cosure.Convertable;

/**
 * @author tangzj1
 * @version 2.0
 * @since Apr 2, 2014
 */
public class Note implements Serializable {

  private static final long serialVersionUID = 7818017582689579040L;

  /** The _id. */
  private String noteID;
  // clientNoteID don't need to store in DB
  private String clientNoteID;
  // clientCategoryID don't need to store in DB
  private String clientCategoryID;

  private Integer noteType;

  private String title;
  /** 摘要 */
  private String summary;

  private String categoryID;

  /** The content. */
  private String content;
  /** The content format template. */
  private String contentTemplateID;
  /** The thumbnail type of note. */
  private Integer styleType;

  private ListStyle listStyle;

  private Share share;

  /** The is marked. */
  private Boolean isMarked;

  /** More to more */
  private List<Tag> tagList;

  /**
   * tag id list 
   * for store db
   */
  private List<String> tagIDList;
  
  private List<String> clientResourceList;

  /** The resource. Many to many */
  private List<Resource> resourceList;

  /** The spot. */
  private Spot spot;

  /** The weather. */
  private Weather weather;

  /** The mood. 心情 */
  private Integer mood;

  /** The background. */
  private Integer backgroundID;

  /** The access password. */
  private Encryption encryption;

  private String userID;
  private Integer status;
  private Long noteVersion;

  private Long userCreateTime;
  private Long createTime;
  private Long lastUpdateTime;

  public static final Convertable<Note, String> TO_ID = new Convertable<Note, String>() {
    @Override
    public String convert(Note q) {
      return q.getNoteID();
    }
  };

  public String getNoteID() {
    return noteID;
  }

  public void setNoteID(String noteID) {
    this.noteID = noteID;
  }

  public String getClientNoteID() {
    return clientNoteID;
  }

  public void setClientNoteID(String clientNoteID) {
    this.clientNoteID = clientNoteID;
  }

  public String getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(String categoryID) {
    this.categoryID = categoryID;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public Integer getBackgroundID() {
    return backgroundID;
  }

  public void setBackgroundID(Integer backgroundID) {
    this.backgroundID = backgroundID;
  }

  public Integer getNoteType() {
    return noteType;
  }

  public void setNoteType(Integer noteType) {
    this.noteType = noteType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getContentTemplateID() {
    return contentTemplateID;
  }

  public void setContentTemplateID(String contentTemplateID) {
    this.contentTemplateID = contentTemplateID;
  }

  public Integer getStyleType() {
    return styleType;
  }

  public void setStyleType(Integer styleType) {
    this.styleType = styleType;
  }

  public Share getShare() {
    return share;
  }

  public void setShare(Share share) {
    this.share = share;
  }

  public Long getNoteVersion() {
    return noteVersion;
  }

  public void setNoteVersion(Long noteVersion) {
    this.noteVersion = noteVersion;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Spot getSpot() {
    return spot;
  }

  public void setSpot(Spot spot) {
    this.spot = spot;
  }

  public Weather getWeather() {
    return weather;
  }

  public void setWeather(Weather weather) {
    this.weather = weather;
  }

  public Boolean getIsMarked() {
    return isMarked;
  }

  public void setIsMarked(Boolean isMarked) {
    this.isMarked = isMarked;
  }

  public List<Tag> getTagList() {
    return tagList;
  }

  public void setTagList(List<Tag> tagList) {
    this.tagList = tagList;
  }
  
  public List<String> getTagIDList() {
    return tagIDList;
  }

  public void setTagIDList(List<String> tagIDList) {
    this.tagIDList = tagIDList;
  }

  public List<String> getClientResourceList() {
    return clientResourceList;
  }

  public void setClientResourceList(List<String> clientResourceList) {
    this.clientResourceList = clientResourceList;
  }

  public List<Resource> getResourceList() {
    return resourceList;
  }

  public void setResourceList(List<Resource> resourceList) {
    this.resourceList = resourceList;
  }

  public Integer getMood() {
    return mood;
  }

  public void setMood(Integer mood) {
    this.mood = mood;
  }

  public Encryption getEncryption() {
    return encryption;
  }

  public void setEncryption(Encryption encryption) {
    this.encryption = encryption;
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

  public ListStyle getListStyle() {
    return listStyle;
  }

  public void setListStyle(ListStyle listStyle) {
    this.listStyle = listStyle;
  }

  public String getClientCategoryID() {
    return clientCategoryID;
  }

  public void setClientCategoryID(String clientCategoryID) {
    this.clientCategoryID = clientCategoryID;
  }

  /**
   * toString方法 返回对象的json形式.
   * 
   * @return the string
   */
  @Override
  public String toString() {
    return JSONObject.fromObject(this).toString();
  }

}
