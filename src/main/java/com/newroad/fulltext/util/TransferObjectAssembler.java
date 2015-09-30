package com.newroad.fulltext.util;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;



/**
 * tangzj1 Apr 1, 2014
 */
public class TransferObjectAssembler {


  
  public static JSONObject transformSearchNote(Map<String, Object> noteMap) {
    JSONObject jo = new JSONObject();
    String noteID = (String) noteMap.get("_id");
    jo.put(NoteConstants.NOTE_ID, noteID);
    jo.put(NoteConstants.TITLE, noteMap.get(NoteConstants.TITLE));
    jo.put(NoteConstants.CONTENT, noteMap.get(NoteConstants.CONTENT));
    jo.put(NoteConstants.CATEGORY_ID, noteMap.get(NoteConstants.CATEGORY_ID));
    jo.put(NoteConstants.IS_MARKED, noteMap.get(NoteConstants.IS_MARKED));
    jo.put(NoteConstants.USER_CREATE_TIME,  noteMap.get(NoteConstants.USER_CREATE_TIME));
    jo.put(NoteConstants.LAST_UPDATE_TIME,  noteMap.get(NoteConstants.LAST_UPDATE_TIME));
    jo.put(NoteConstants.STYLE_TYPE, noteMap.get(NoteConstants.STYLE_TYPE));
    
    Object weather = noteMap.get(NoteConstants.WEATHER);
    if (weather != null) {
      JSONObject jsWeather = JSONObject.fromObject(weather);
      jo.put(NoteConstants.WEATHER, jsWeather);
    }
    Object spot =noteMap.get(NoteConstants.SPOT);
    if (spot != null) {
      JSONObject jsSpot = JSONObject.fromObject(spot);
      jo.put(NoteConstants.SPOT, jsSpot);
    }
    jo.put(NoteConstants.SUMMARY, noteMap.get(NoteConstants.SUMMARY));

    Object share = noteMap.get(NoteConstants.SHARE);
    if (share != null) {
      //need to update
      jo.put(NoteConstants.SHARE_TOKEN, null);
    }

    Object tags = noteMap.get(NoteConstants.TAGS);
    if (tags != null) {
      JSONObject tagJs = JSONObject.fromObject(tags);
      jo.put(NoteConstants.TAGS, tagJs);
    }
    
    Object listStyle = noteMap.get(NoteConstants.LIST_STYLE);
    if (listStyle != null) {
      JSONObject styleJs = JSONObject.fromObject(listStyle);
      jo.put(NoteConstants.LIST_STYLE, styleJs);
    }
    jo.put(NoteConstants.NOTE_VERSION, noteMap.get(NoteConstants.VERSION));
    return jo;
  }

  /**
   * 将指定的笔记转换成JSONObject对象
   * 
   * @param Note
   * @return
   */
  public static JSONObject transformNote(Map<String, Object> note, List<?> tagList) {
    JSONObject jo = new JSONObject();
    jo.put(NoteConstants.NOTE_ID, note.get("_id"));
    jo.put(NoteConstants.TITLE, note.get(NoteConstants.TITLE));
    jo.put(NoteConstants.IS_MARKED, note.get(NoteConstants.IS_MARKED));
    jo.put(NoteConstants.LAST_UPDATE_TIME, note.get(NoteConstants.LAST_UPDATE_TIME));

    Object weather = note.get(NoteConstants.WEATHER);
    if (weather != null) {
      JSONObject jsWeather = JSONObject.fromObject(weather);
      jo.put(NoteConstants.WEATHER, jsWeather);
    }
    Object spot = note.get(NoteConstants.SPOT);
    if (spot != null) {
      JSONObject jsSpot = JSONObject.fromObject(spot);
      jo.put(NoteConstants.SPOT, jsSpot);
    }
    jo.put(NoteConstants.SUMMARY, note.get(NoteConstants.SUMMARY));

    // tags
    if (!CollectionUtils.isEmpty(tagList)) {
      jo.put(NoteConstants.TAG, tagList);
    }

    Object listStyle = note.get(NoteConstants.LIST_STYLE);
    if (listStyle != null) {
      JSONObject jsSpot = JSONObject.fromObject(listStyle);
      jo.put(NoteConstants.LIST_STYLE, jsSpot);
    }
    jo.put(NoteConstants.NOTE_VERSION, note.get(NoteConstants.VERSION));
    return jo;
  }

}
