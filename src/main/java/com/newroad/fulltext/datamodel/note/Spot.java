package com.newroad.fulltext.datamodel.note;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * The Class Spot.
 * 地理位置信息,保存到笔记中
 */
public final class Spot implements Serializable{

	private static final long serialVersionUID = 7274764315428667507L;

	/** 
	 * The x.
	 * 经度,负数表示西经,正数表示东经 
	 */
	private Double x;
	
	/** 
	 * The y.
	 * 维度,负数表示南纬,正数表示北纬 
	 */
	private Double y;
	
	/** The city. */
	private Integer city;
	
	/** 
	 * The address.
	 * 街道地址,位置识别信息 
	 */
	private String address;
	
	/**
	 * Instantiates a new spot.
	 */
	public Spot(){
		
	}
	
	/**
	 * Instantiates a new spot.
	 * 从JSONObject初始化Spot对象
	 * 
	 * @param spot the spot JSONObject,为null时,抛出IllegalArgumentException
	 *
	 */
	public Spot(JSONObject spot){
		if(null == spot) {
			throw new IllegalArgumentException("The JSONObject of spot is null.");
		}
		if(spot.get("x") != null) {
			this.x = spot.getDouble("x");
		}
		if(spot.get("y") != null) {
			this.y = spot.getDouble("y");
		}
		if(spot.get("city") != null) {
			this.city = spot.getInt("city");
		}
		if(spot.get("address") != null) {
			this.address = spot.getString("address");
		}
	}
	
	/**
	 * Instantiates a new spot.
	 *
	 * @param x the x
	 * @param y the y
	 * @param city the city
	 * @param address the address
	 */
	public Spot(Double x, Double y, Integer city, String address){
		this.x = x;
		this.y = y;
		this.city = city;
		this.address = address;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public Double getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(Double x) {
		this.x = x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public Double getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(Double y) {
		this.y = y;
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(Integer city) {
		this.city = city;
	}
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
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
