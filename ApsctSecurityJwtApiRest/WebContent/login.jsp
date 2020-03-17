<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Autenticacion de usuario ...</title>
</head>
<body>
	<form method="post" action="j_security_check">
		Usuario: <input id="username" type="text" name="j_username" value="user"/> <br />
		Password: <input id="password" type="password" name="j_password" value="opencan"/> <br />
		<button type="submit">Enviar</button>
	</form>
</body>
</html>

