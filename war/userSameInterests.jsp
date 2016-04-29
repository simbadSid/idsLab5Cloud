<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlet.IdsLab5CloudServlet" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.util.*"%>
<%@ page import="data.*" %>


<%
	String		userLogin			= request.getParameter(IdsLab5CloudServlet.ATTRIBUTE_NAME_LOGIN);
	Data_user	user				= IdsLab5CloudServlet.userSet.getUser(userLogin);
	List<DataInterface> iList		= IdsLab5CloudServlet.userSet.getEntity(ResourceManager_DataStoreService.KEY_USER, null);
	LinkedList<Data_user> userList	= new LinkedList<Data_user>();

	for (int i=0; i<iList.size(); i++)
	{
		Data_user nextUser = (Data_user)iList.get(i);
		if (nextUser.getCommonInterest(user) == null)
		{
			userList.add(nextUser);
		}
	}
%>




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
		<h1>Social network: Users with the same interest as: "<%= user.getKey() %>"</h1>
		<p></p>
	</section>




	<section class="container">
		<div align="center">
		<%
		for (Data_user nextUser: userList)
		{
		%>
			<tr>
				<th>
					<img src=" <%= nextUser.get_pictureName()%> " alt="Note" style="height:100px;width:100px">
				</th>
				<td>
				<a href="getFreindPage?freindToreach=<%=nextUser.getKey()%> "> <%= nextUser.getKey() %> </a>
				</td>
			</tr>
			<p></p>
		<%
		}
		%>
	</section>



</body>
</html>