<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="servlet.IdsLab5CloudServlet" %>


<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Social network</title>
			<link rel="stylesheet" type="text/css" href="login.css">
	</head>


	<body>

		<section class="container">
			<div align="center">
			<h1>Social network: Login</h1>


			<form action="loginSubmit" method="post">
					<label>Login</label><br/>
					<input name="login" placeholder="Type your login"/><br/>

					<p></p>

					<label>Password</label><br/>
					<input name="password" type="password" placeholder="Type your password" /><br/>

					<p></p>

					<input type="submit"	name="login"			value="Login"/>
					<input type="submit"	name="createAccount"	value="Create an account" formaction="<%=IdsLab5CloudServlet.FILE_CREATE_ACCOUNT_HTML%>"/>

			</form>
			</div>

		</section>
	</body>
</html>
