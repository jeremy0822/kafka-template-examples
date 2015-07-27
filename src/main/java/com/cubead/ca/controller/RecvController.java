package com.cubead.ca.controller;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cubead.ca.common.kafka.MessageQueue;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubead.ca.common.util.CommonFunc;
import com.cubead.ca.common.util.Constant;
import com.cubead.ca.common.util.MemCacheQueue;
import com.cubead.ca.common.web.BaseController;
import com.cubead.ca.service.IReciveLogs;
import com.cubead.ca.vo.RequestData;
import com.cubead.ca.vo.ResponseData;
import com.cubead.framework.common.StringUtils;

@Controller
public class RecvController extends BaseController{
	private final static Logger logger = Logger.getLogger(RecvController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Resource
	private IReciveLogs reciveLogs;
	private AtomicLong seq = new AtomicLong(1);
	
	@RequestMapping("recv/visit")
	public void visit(HttpServletRequest request, HttpServletResponse response, ResponseData responseData) throws Exception{
		responseData.setStatusCode(200);
    	String str ="var retvalue='false';";
    	responseData.setBody(str.getBytes());
    	sendResponse(request, response, responseData);
    	responseData.releaseResource();
		responseData = null;
	}
	
	@RequestMapping("recv/trace")
	@Scope("prototype")
	public void trace(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sessionId = request.getSession().getId();
		RequestData requestData = makeRequestData(request);
		requestData.setBody(getBody(request));
		ResponseData responseData = new ResponseData();
		Map<String, String> params = CommonFunc.parseParameter(requestData);
		
		String tenantID = params.get(Constant.REQUEST_NAME_TENANTID);
		String siteName = params.get(Constant.REQUEST_NAME_SITE); 
		setCookieAndHeader(requestData, responseData, sessionId);
		if (StringUtils.isNullOrEmpty(tenantID) || StringUtils.isNullOrEmpty(siteName)){
			reciveLogs.writeLog(requestData);
//			reciveLogs.writeKafka(requestData); // write to kafka
			MessageQueue.put(requestData.toString()); //write to buffer
			MemCacheQueue queue = new MemCacheQueue();
			String memKey = request.getParameter("ca_tenant");
			String memVal = request.getParameter("ca_dm");
			queue.setCache(memKey, sdf.format(new Date()) + "|" + memVal);
		}else{
			String str ="var CAUID='"+responseData.getCookie(Constant.CAUIDDOMAIN)+"';var clientIp='"+requestData.getClientIP()+"';"+" var time='"+requestData.getTime()+"';";
			responseData.setBody(str.getBytes());
		}
		sendResponse(request, response, responseData);
		responseData.releaseResource();
		responseData = null;
	}
	protected void sendResponse(HttpServletRequest baseRequest, HttpServletResponse servResponse, ResponseData response) {
        for (Entry<String, String> entry : response.getHeaders()){
        	servResponse.setHeader(entry.getKey(), entry.getValue());
        }

        for (Entry<String, String> entry : response.getCookies().entrySet()) {
            Cookie cc = new Cookie(entry.getKey(), entry.getValue());
            cc.setPath("/");
            cc.setMaxAge(3 *30 * 24 * 60 * 60);
            servResponse.addCookie(cc);
        }

        servResponse.setStatus(response.getStatusCode());
        try {
            if (response.getStatusCode() != HttpServletResponse.SC_OK) {
                StringBuilder sb = new StringBuilder();
                sb.append("<h1>").append(response.getStatusCode()).append(" - ");
                sb.append(response.getReason()).append("</h1>");
                servResponse.setContentType("text/html");
                servResponse.getOutputStream().write(sb.toString().getBytes("utf-8"));
            }
            else if (response.getBody() != null && response.getBody().length > 0) {
                if (response.getHeader("Content-Encoding") != null 
                        && response.getHeader("Content-Encoding").equalsIgnoreCase("gzip")) {
                    GZIPOutputStream dest = new GZIPOutputStream(servResponse.getOutputStream(), 1280 * 1024);
                    int step = 1024*1024;
                    for (int i = 0; i < response.getBody().length; i += step) {
                        if (response.getBody().length - i < step)
                            dest.write(response.getBody(), i, response.getBody().length - i);
                        else
                            dest.write(response.getBody(), i, step);
                    }
                    dest.finish();
                    dest.flush();
                }
                else {
                    servResponse.setContentLength(response.getBody().length);
                    servResponse.getOutputStream().write(response.getBody());
                }
            }
            else
                servResponse.setContentLength(0);
        }
        catch (Exception exp) {
            logger.warn(" Failed to write response body:" + exp.getMessage());
        }
    }
	protected String getBody(HttpServletRequest baseRequest) {
        if (baseRequest.getContentLength() <= 0)
            return null;

        try {
            ByteBuffer buff = ByteBuffer.allocate(baseRequest.getContentLength());
            byte[] tmp = new byte[1024];
            int len = 0;
            while ((len = baseRequest.getInputStream().read(tmp)) > 0)
                buff.put(tmp, 0, len);
            tmp = null;
            return new String(buff.array(), "utf-8");
        }
        catch (Exception exp) {
        	logger.warn(" Failed to extract request body data:" + exp.getMessage());
            return null;
        }
    }

	 private void setCookieAndHeader(RequestData requestData, ResponseData responseData,String sessionId) {
	        String cookie = requestData.getCookie();
	        StringBuilder builder = new StringBuilder();
	        if (cookie == null || cookie.indexOf(Constant.CAUID) < 0) {
		        String uidPrefix = Integer.toHexString(requestData.getDomain().hashCode()) + String.valueOf(System.currentTimeMillis()).substring(7);
	        	String uid = uidPrefix + "_" + CommonFunc.toHex(CommonFunc.toIPBytes(requestData.getClientIP())) + "_" + seq.incrementAndGet();
	        	builder.append(Constant.CAUID)
	        		   .append("=")
	        		   .append(uid)
	        		   .append(";")
	        		   .append(Constant.CAUIDDOMAIN)
	        		   .append("=")
	        		   .append(uid)
	        		   .append(";");
	            responseData.setCookie(Constant.CAUID, uid);
	            responseData.setCookie(Constant.CAUIDDOMAIN, uid);
	    
	        }else{
	        	if (cookie.contains("CAUID=")&&cookie.contains(";")&&cookie.length()>7){
	        		int fromIndex = cookie.indexOf("CAUID=")+6;
	        		responseData.setCookie(Constant.CAUIDDOMAIN, cookie.substring(fromIndex,cookie.indexOf(";",fromIndex)));
	        		builder.append(cookie);
	        	}
	        }
	        builder.append("SESSION_ID=")
		 		   .append(sessionId)
		 		   .append(";")
		 		   .append(Constant.OWN_UID)
		 		   .append("=")
		 		   .append(requestData.getOwnUid())
		 		   .append(";")
		 		   .append(Constant.OWN_SESSION_ID)
		 		   .append("=")
		 		   .append(requestData.getOwnSessionid())
		 		   .append(";")
		 		   .append(Constant.JOIN_KEY)
		 		   .append("=")
		 		   .append(requestData.getJoinKey());
	        requestData.setCookie(builder.toString());
	        responseData.setStatusCode(200);
	        
	        responseData.setHeader("Access-Control-Allow-Origin", "*");
	        responseData.setHeader("Access-Control-Allow-Credentials", "true");
	        responseData.setHeader("P3P", "CP=CAO PSA OUR");
	 }
	 protected  RequestData  makeRequestData(HttpServletRequest baseRequest) {
	        RequestData request = new RequestData();
	        String ownSessionid = baseRequest.getParameter("ca_sessionid") == null ? "" : baseRequest.getParameter("ca_sessionid");
			String ownUid = baseRequest.getParameter("ca_uid") == null ? "" : baseRequest.getParameter("ca_uid");
			String joinKey = baseRequest.getParameter("ca_join")==null ?"" : baseRequest.getParameter("ca_join");
			request.setOwnSessionid(ownSessionid);
			request.setOwnUid(ownUid);
			request.setJoinKey(joinKey);
	        request.setMethod(baseRequest.getMethod());
	        request.setBrowser(baseRequest.getHeader("User-Agent"));
	        String clientIP = baseRequest.getHeader("X-Real-IP");
	        if (clientIP == null || clientIP.isEmpty())
	            request.setClientIP(baseRequest.getRemoteAddr());
	        else
	            request.setClientIP(clientIP);
	        request.setResource(baseRequest.getRequestURL().toString());
	        request.setQuery(baseRequest.getQueryString());
	        request.setReferre(baseRequest.getHeader("Referer"));
	        Cookie[] cookies = baseRequest.getCookies();
	        if (cookies != null) {
	            StringBuilder sb = new StringBuilder();
	            for (Cookie c : cookies)
	                sb.append(c.getName()).append("=").append(c.getValue()).append(";");
	            request.setCookie(sb.toString());
	            sb = null;
	        }

	        int pos = request.getQuery().indexOf("ca_dm");
	        if (pos < 0)
	            request.setDomain("unknow");
	        else {
	            int end = request.getQuery().indexOf("&", pos);
	            if (end <= 0)
	                request.setDomain(request.getQuery().substring(pos + 6));
	            else
	                request.setDomain(request.getQuery().substring(pos + 6, end));
	        }

	        return request;
	    }
}
