package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

/**
 * @author tangzj1
 * @version 2.0
 * @since May 6, 2014
 */
public class ListStyle implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -3245571183966309879L;

  private String pictureLink;

  private String noteAudioTime;

  private String noteAudioID;
  
  private String thumbnailKeyID;
  
  private String thumbnailName;
  
  private Integer thumbnailType;

  public String getPictureLink() {
    return pictureLink;
  }

  public void setPictureLink(String pictureLink) {
    this.pictureLink = pictureLink;
  }

  public String getNoteAudioTime() {
    return noteAudioTime;
  }

  public void setNoteAudioTime(String noteAudioTime) {
    this.noteAudioTime = noteAudioTime;
  }

  public String getNoteAudioID() {
    return noteAudioID;
  }

  public void setNoteAudioID(String noteAudioID) {
    this.noteAudioID = noteAudioID;
  }

  public String getThumbnailKeyID() {
    return thumbnailKeyID;
  }

  public void setThumbnailKeyID(String thumbnailKeyID) {
    this.thumbnailKeyID = thumbnailKeyID;
  }

  public String getThumbnailName() {
    return thumbnailName;
  }

  public void setThumbnailName(String thumbnailName) {
    this.thumbnailName = thumbnailName;
  }

  public Integer getThumbnailType() {
    return thumbnailType;
  }

  public void setThumbnailType(Integer thumbnailType) {
    this.thumbnailType = thumbnailType;
  }


}
