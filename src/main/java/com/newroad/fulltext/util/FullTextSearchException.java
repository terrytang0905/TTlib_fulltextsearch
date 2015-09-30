package com.newroad.fulltext.util;

/**
 * @info
 * @author tangzj1
 * @date  Sep 6, 2013 
 * @version 
 */
public class FullTextSearchException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String returnMessage ;
	
	public FullTextSearchException(){
		
	}
	
	public FullTextSearchException(String message) {
		super(message);
		this.returnMessage =message;
	}
	
	public FullTextSearchException(Throwable e) {
		super(e);
		this.returnMessage = e.getMessage();
	}
	
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
}
