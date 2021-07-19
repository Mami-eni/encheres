<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<div class="col-sm-2 "></div>
		<div class="col-sm-8 ">
		
			
			<h1 class='title'>${user.pseudo}</h1>
			<div class = 'detail-profil text-center'>
				
				<p>Nom: ${user.nom}</p>
				<p>Prenom: ${user.prenom}</p>
				<p>Email: ${user.email}</p>
				<p>Telephone: ${user.telephone}</p>
				<p>Rue: ${user.rue}</p>
				<p>Code postal: ${user.codePostal}</p>
				<p>Ville: ${user.ville}</p>
			</div>
			
				<a href="./modifierUser">
					<button class="btn btn-primary btn-lg boutons-profil">Modifier mon compte</button>
				</a>
			
			
			
		</div>

		<div class="col-sm-2"></div>
	</div>

	<%@include file="template/script.jsp"%>

</body>
</html>