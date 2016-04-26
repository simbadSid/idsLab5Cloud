<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlet.IdsLab5CloudServlet" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="data.Data_user" %>


<% String		userLogin		= request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_LOGIN); %>
<% boolean		isForeignPage	= Boolean.parseBoolean(request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_IS_FOREIGN_USER)); %>
<% String		freindLoginError= request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_FREIND_LOGIN_ERROR); %>
<% /*Data_user	user			= IdsLab5CloudServlet.userSet.getUser(userLogin);*/%>




<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Social Network: user page</title>
			<link rel="stylesheet" type="text/css" href="login_fichiers/login.html">
</head>
<body>
	blabla
	<section class="container">
		<div align="center">
	 		<h2>userLogin: <%=userLogin%></h2>
			</br>
			<h2>isForeignPage = <%=(""+isForeignPage)%></h2>
		</div>
	</section>
	
</body>
</html>
