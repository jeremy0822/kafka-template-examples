package com.cubead.ca.common.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class ImageUtil {
	private final static Logger logger = Logger.getLogger(ImageUtil.class);
	
	public static void writeToResponse(String basePath,HttpServletResponse response) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(basePath);
			response.getOutputStream().write(sb.toString().getBytes());
		} catch (Exception e) {
			logger.error("writeToResponse error! ", e);
		} 
	}
}
