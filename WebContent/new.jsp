<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
		我是一个新窗口
		companyId:<%=request.getParameter("companyID") %> <br/>
		configID:<%=request.getParameter("configID") %><br />
		sessionId:<%=request.getParameter("sessionId") %><br />
		Uid:<%=request.getParameter("uid") %><br />
	</body>
</html>