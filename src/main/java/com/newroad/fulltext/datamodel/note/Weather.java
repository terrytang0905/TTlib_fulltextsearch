package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * The Class Weather.
 * 天气信息,保存到笔记
 */
public class Weather implements Serializable{

	private static final long serialVersionUID = -8841339296849562196L;

	/** The status. */
	private Integer status;
	
	/** The temperature. */
	private Double temperature;
	
	/**
	 * Instantiates a new weather.
	 */
	public Weather(){
		
	}
	
	/**
	 * Instantiates a new weather.
	 * 
	 * 从JSONObject初始化Weather对象
	 * @param weather the weather JSONObject,为null时,抛出IllegalArgumentException
	 */
	public Weather(JSONObject weather){
		if(weather == null) {
			throw new IllegalArgumentException("The JSONObject of weather is null.");
		}
		if(weather.get("status") != null) {
			this.status = weather.getInt("status");
		}
		if(weather.get("temperature") != null) {
			this.temperature = weather.getDouble("temperature");
		}
	}
	/**
	 * Instantiates a new weather.
	 * 
	 * @param status
	 *            the status
	 * @param temperature
	 *            the temperature
	 */
	public Weather(Integer status, Double temperature){
		this.status = status;
		this.temperature = temperature;
	}
	
	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * Gets the temperature.
	 * 
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}
	
	/**
	 * Sets the temperature.
	 * 
	 * @param temperature
	 *            the new temperature
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * toString方法
	 * 返回对象的json形式.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
