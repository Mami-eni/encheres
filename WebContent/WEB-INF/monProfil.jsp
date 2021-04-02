<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="error.jsp" isErrorPage="false"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="template/head.jsp"%>

</head>
<body>
<%@include file="template/nav.jsp"%>

<h1>Connection réussi</h1>
<p>${user.pseudo} est connecté</p>
<p>Nom: ${user.nom} </p>
<p>Prenom: ${user.prenom} </p>
<p>Email: ${user.email} </p>
<p>Telephone: ${user.telephone} </p>
<p>Rue: ${user.rue} </p>
<p>Code postal: ${user.codePostal} </p>
<p>Ville: ${user.ville} </p>


<a href = "./modifierUser">
<button class ="btn btn-primary btn-lg">
	Modifier mon compte
</button>
</a>

<a href = "./">
<button class ="btn btn-primary btn-lg">
	Retourner
</button>
</a>
<%@include file="template/script.jsp"%>

</body>
</html>