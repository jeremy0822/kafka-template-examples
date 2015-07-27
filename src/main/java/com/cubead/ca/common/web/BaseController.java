package com.cubead.ca.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


public abstract class BaseController {
	protected Logger log = Logger.getLogger(this.getClass());
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex,HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
}
