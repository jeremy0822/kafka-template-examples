<%@page import="org.apache.http.impl.conn.*"%>
<%@page import="com.cubead.framework.http.HttpClientManager"%>
<%@page import="org.apache.http.pool.PoolStats"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>httpClient 链接池</title>
</head>
<body>
<%
PoolingClientConnectionManager tcc=(PoolingClientConnectionManager)(HttpClientManager.getInstance().getConnectionManager());
PoolStats ps=tcc.getTotalStats();	
	out.println("最大连接数: " + ps.getMax()+"<br>");
	out.println("pending: " + ps.getPending()+"<br>"); 
	out.println("空闲: " + ps.getAvailable()+"<br>"); 
	out.println("leased: "+ps.getLeased());
 
%>
</body>
</html>