<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp" isErrorPage="false"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="template/head.jsp"%>

</head>
<body>
	<%@include file="template/nav.jsp"%>
	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<h1>${vendeur.pseudo}</h1>
			<p>Nom: ${vendeur.nom}</p>
			<p>Prenom: ${vendeur.prenom}</p>
			<p>Email: ${vendeur.email}</p>
			<p>Telephone: ${vendeur.telephone}</p>
			<p>Rue: ${vendeur.rue}</p>
			<p>Code postal: ${vendeur.codePostal}</p>
			<p>Ville: ${vendeur.ville}</p>
			<c:if test="${sessionScope.user.numero == vendeur.numero}">
			<a href="./modifierUser">
				<button class="btn btn-primary btn-lg">Modifier mon compte</button>
			</a>
		</c:if>
		</div>
		
		<div class="col-sm-4"></div>
	</div>

	<%@include file="template/script.jsp"%>

</body>
</html>