<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlet.IdsLab5CloudServlet" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="data.Data_user" %>


<% String		userLogin		= request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_LOGIN); %>
<% boolean		isForeignPage	= Boolean.parseBoolean(request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_IS_FOREIGN_USER)); %>
<% String		freindLoginError= request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_FREIND_LOGIN_ERROR); %>
<% Data_user	user			= IdsLab5CloudServlet.userSet.getUser(userLogin);%>




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
	<section class="container">
		<div align="center">
		<h1>Social network: <%= user.getKey() %> 's page (<%= ((!isForeignPage)? "loged user" : "Freind") %> )</h1>
	</section>
	<section class="container">
		<div align="left">
		<table class="lamp">
			<tr>
				<th>
					<img src=" <%= user.get_pictureName() %> " alt="Note" style="height:100px;width:100px">
				</th>
			</tr>
			<tr>
				<td>
		<%if (!isForeignPage)
		{%>
						<a href="logout?login=" <%=user.getKey() %> >Logout ( <%= user.getKey() %> )</a>
		<%} %>
				</td>
			</tr>
		</table>
	</section>
<p></p>
	<section class="container">
		<div align="left">
		<table class="lamp">

		<% for (String attributeName: Data_user.attributesToPrint)
		{
			String methodName = "get_" + attributeName;
			Method m = Data_user.class.getMethod(methodName, null);
			Object res = m.invoke(user, null);
			String attributeValue = (res == null) ? "null" : (""+res);
		%>
				<tr>
					<th>
						<%= attributeName%>
					</th>
					<td>
		<%
			if (isForeignPage)
			{
		%>
							<%= attributeValue%>

		<%	}
			else
			{
		%>
							<a href="#### URL to change the label ####"> <%=attributeValue %> "</a>	
		<%
			}
		%>
					</td>
				</tr>
		<%
		}
		%>
		</table>
	</section>

	<section class="container">
		<div align="right">
		<%
		if (!isForeignPage)
		{
		%>
				<h1>Find New Freinds</h1>
				<form method="post" action="addFreind?login= <%=user.getKey() %>">
						<input name="freindToAddLogin" placeholder="Type a login">
						<input name="submit" value="Search" type="submit">
				</form>
				<p></p>
			<table class="lamp">
		<%
		}

		if (freindLoginError != null)
		{
		%>
		<section class="container">
			<div align="right">
				<h1>Can't find the freind " <%= freindLoginError %> ""</h1>
				<p></p>
		</section>
		<%
		}
		if (!user.get_freind().isEmpty())
		{
		%>
				<h1> <%= user.getKey()%> 's Freinds:</h1>
		<%
		}

		for (String freindLogin: user.get_freind())
		{
			Data_user freindUser= IdsLab5CloudServlet.userSet.getUser(freindLogin);
		%>
					<tr>
						<th>
							<img src=" <%= freindUser.get_pictureName()%> " alt="Note" style="height:100px;width:100px">
						</th>
						<td>
						<a href="getFreindPage?freindToreach= <%= freindLogin %> "> <%= freindLogin %> </a>
						</td>
					</tr>
		<%
		}
		%>
		</div>
	</section>
</body>
</html>
