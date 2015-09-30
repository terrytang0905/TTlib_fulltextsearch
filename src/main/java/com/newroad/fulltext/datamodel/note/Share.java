package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

/**
 * @author tangzj1
 * @version 2.0
 * @since Apr 2, 2014
 */
public class Share implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 8929069276350216451L;
  private String shareToken;
  private Long createTime;
  private Long expireTime;
  private Integer status;

  public String getShareToken() {
    return shareToken;
  }

  public void setShareToken(String shareToken) {
    this.shareToken = shareToken;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
