package com.cubead.ca.vo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestData {
    private long timestamp;
    private String hour;
    private String time;
    private String method;
    private String resource;
    private String query;
    private String domain;
    private String browser;
    private String referre;
    private String clientIP;
    private String cookie;
    private String contentType;
    private String ownSessionid;
    private String ownUid;
    private String joinKey;
    
    public String getJoinKey() {
		return joinKey;
	}

	public void setJoinKey(String joinKey) {
		this.joinKey = joinKey;
	}

	private String body;

    private boolean isDownload = false;
    private String savePath;

    private int timeout = 30;

    public RequestData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Date now = new Date();
        hour = sdf.format(now);
        sdf.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        time = sdf.format(now);
        timestamp = now.getTime();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Calendar.getInstance();
        sb.append("[CARAWDATA ").append(" - ").append(time).append(" ]");
        sb.append("[Timestamp: ").append(timestamp).append(" ]");
        sb.append("[Browser: ").append(clientIP).append(" - ").append(browser).append(" ]");
        sb.append("[Referre: ").append(referre).append(" ]");
        sb.append("[Request: ").append(resource).append("?").append(query).append(" ]");
        sb.append("[Cookie: ").append(cookie).append(" ]");
        sb.append("[Body: ").append(body).append(" ]");
        sb.append("\r\n");

        return sb.toString();
    }
    
    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        if (domain == null)
            this.domain = "ca";
        else
            this.domain = domain;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        if (query == null)
            this.query = "";
        else
            this.query = query;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        if (cookie == null)
            this.cookie = "";
        else
            this.cookie = cookie;
    }

    public String getReferre() {
        return referre;
    }

    public void setReferre(String referre) {
        if (referre == null)
            this.referre = "";
        else
            this.referre = referre;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        if (body == null)
            this.body = "";
        else
            this.body = body;
    }

    public String getHour() {
        return hour;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getOwnSessionid() {
		return ownSessionid;
	}

	public void setOwnSessionid(String ownSessionid) {
		this.ownSessionid = ownSessionid;
	}

	public String getOwnUid() {
		return ownUid;
	}

	public void setOwnUid(String ownUid) {
		this.ownUid = ownUid;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
}
