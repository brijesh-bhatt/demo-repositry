<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>My JSP 'login.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<div align="center">
			<h2>Login into M.A.R.S.</h2>
			<form action="/testingApp/verify.html" method="post" commandName="user">
				User Name: <input type="text" name="username" /><br/><br/>
				Password: <input type="password" name="password" /><br/><br/>
				<input type="submit" name="Submit" value="Submit" /><br/><br/>
			</form>
		</div>
	</body>
</html>
