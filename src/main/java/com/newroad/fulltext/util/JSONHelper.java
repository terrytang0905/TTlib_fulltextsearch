package com.newroad.fulltext.util;

import static org.elasticsearch.common.io.Streams.copyToStringFromClasspath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

/**
 * @info
 * @author tangzj1
 * @date  Aug 26, 2013 
 * @version 
 */
public class JSONHelper {

	private static Logger logger = LoggerFactory.getLogger(JSONHelper.class);
	
	public static final String JSON_REQUEST_FORMAT=MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8";
	
	public static String getJsonSettings(String jsonDefinition, Object... args)
			throws Exception {
		//logger.debug("Get setting");
		String setting = copyToStringFromClasspath(jsonDefinition);
		if (args != null) {
			setting = String.format(setting, args);
		}
		logger.info("JSON setting [{}]", setting);
		return setting;
	}
}
