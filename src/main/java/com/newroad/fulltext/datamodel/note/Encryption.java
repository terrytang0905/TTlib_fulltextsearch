package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

/**
 * @author tangzj1
 * @version 2.0
 * @since May 15, 2014
 */
public class Encryption implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 8931335312657404481L;
  private String encryptPassword;
  private String passwordHint;
  private Integer status;

  public String getEncryptPassword() {
    return encryptPassword;
  }

  public void setEncryptPassword(String encryptPassword) {
    this.encryptPassword = encryptPassword;
  }

  public String getPasswordHint() {
    return passwordHint;
  }

  public void setPasswordHint(String passwordHint) {
    this.passwordHint = passwordHint;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
