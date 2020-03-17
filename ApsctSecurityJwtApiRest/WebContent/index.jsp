<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contenido de la aplicación</title>
</head>

<body>
	<form action="security/generar" method="post" target="_blank">
		<h2>Solicitar nuevo token:</h2> <br/>
		
		Usuario: <input type="text" name="username" value="user" />
		Password: <input type="password" name="password" value="opencan" />
		
		<button type="submit">Solicitar</button>
	</form>

	<form action="security/validar" method="get" target="_blank">
		<h2>Validar almacenado en las cookies</h2> <br/>
		
		<button type="submit">Validar</button>
	</form>
</body>
</html>