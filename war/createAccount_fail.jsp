<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.*" %>
<%@ page import="servlet.IdsLab5CloudServlet" %>



<!DOCTYPE html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Social network</title>
			<link rel="stylesheet" type="text/css" href="createAccount.css">
	</head>


	<body>
		<section class="container">
			<div align="center">
			<h2>Incoherent informations, or account already exists</h2>
			</div>
		</section>

		<p></p>

		<section class="container">
			<div align="center">
			<h1>Social network: Account creation</h1>
			<form method="post" action="createAccountSubmit">
					<label>Login</label>
					<input name="login" placeholder="Type your login"/><br/>

					<p></p>

					<label>Password</label>
					<input name = "password" type="password" placeholder="Type your password" /><br/>

					<p></p>

					<label>Password Confirmation</label>
					<input name="passwordConfirmation" type="password" placeholder="Type your password" /><br/>

					<p></p>

					<label>Name</label>
					<input name="name" placeholder="Name" /><br/>

					<p></p>

					<label>Surname</label>
					<input name="surname" placeholder="Surname" /><br/>

					<p></p>

					<label>Age</label>
					<input name="age" type="number" placeholder="Age" /><br/>

					<p></p>
<!--- ----------------------------------------------------------------------------------- -->
					<label>Location</label>
					<p></p>
					<% for (DataInterface city: IdsLab5CloudServlet.userSet.getEntity(ResourceManager_DataStoreService.keyAvailableCity, null))
					{%>
						<input type="radio" name="location" value="<%=city.getKey()%>" checked> <%=city.getKey()%> <br>
					<% } %>
					<p></p>

					<label>Interest</label>
						<p></p>
					<% for (DataInterface interest: IdsLab5CloudServlet.userSet.getEntity(ResourceManager_DataStoreService.keyAvailableInterest, null))
					{%>
						<input type="radio" name="interest" value="<%=interest.getKey()%>" checked> <%=interest.getKey()%> <br>
					<% } %>
<!--- ----------------------------------------------------------------------------------- -->


					<p></p>

					<input type="submit"	name="create"	value="Create"/>

			</form>
			</div>
		</section>	
	</body>
</html>