package com.cubead.ca.vo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ResponseData {
    private int statusCode;
    private String reason;
    private boolean isDownload = false;
    private Map<String, String> headers;
    private byte[] body;
    private Map<String, String> cookies;

    public ResponseData() {
        statusCode = 200;
        reason = "OK";
        headers = new HashMap<String, String>();
        cookies = new HashMap<String, String>();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public Set<Entry<String, String>> getHeaders() {
        return Collections.unmodifiableSet(headers.entrySet());
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    /**
     * Indicate response will output a stream to transmit file, invoke getBody to get filename
     * 
     * @return
     */
    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        this.isDownload = download;
    }

    public void setCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public String getCookie(String key) {
        return this.cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }

    public void releaseResource() {
        if (headers != null)
            headers.clear();

        body = null;
        headers = null;
    }
}
