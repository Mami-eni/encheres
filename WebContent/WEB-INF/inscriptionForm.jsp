<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp" isErrorPage="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${!empty errors}">
		<div class="col-lg-6 col-md-4 col-sm-6 portfolio-item">
			<div class="card h-100 alert">
				<div class="card-body alert alert-danger">
					<h4 class="card-title">Erreurs</h4>
					<c:forEach var="msg" items="${errors}">
						<p class="card-text">${msg}</p>
					</c:forEach>
				</div>
			</div>
		</div>

	</c:if>
	<form action="./inscription" method="post">
		<div class="form-group">
			<label for="pseudo">Pseudo : </label> <input class="form-control"
				id="pseudo" name="pseudo" value="pseudo">
		</div>
		<div class="form-group">
			<label for="nom">Nom : </label> <input class="form-control" id="nom"
				type="nom" name="nom" value="nom">
		</div>
		</div>
		<div class="form-group">
			<label for="prenom">Prénom : </label> <input class="form-control"
				id="prenom" type="prenom" name="prenom" value="prenom">
		</div>
		<div class="form-group">
			<label for="telephone">Téléphone: </label> <input
				class="form-control" id="telephone" type="tel" name="telephone"
				value="telephone">
		</div>

		<div class="form-group">
			<label for="codePostal">Code postal : </label> <input
				class="form-control" id="codePostal" type="codePostal"
				name="codePostal" value="codePostal">
		</div>
		<div class="form-group">
			<label for="mdp">Mot de passe : </label> <input class="form-control"
				id="mdp" type="password" name="mdp" value="mdp">

			<div class="form-group">
				<label for="email">Email : </label> <input class="form-control"
					id="email" type="email" name="email" value="email">
			</div>
			<div class="form-group">
				<label for="rue">Rue : </label> <input class="form-control" id="rue"
					type="rue" name="rue" value="rue">
			</div>
			<div class="form-group">
				<label for="ville">Ville : </label> <input class="form-control"
					id="ville" type="ville" name="ville" value="ville">
			</div>
			<div class="form-group">
				<label for="confirmation">Confirmation : </label> <input
					class="form-control" id="confirmation" type="password"
					name="confirmation" value="confirmation">
			</div>
			<div class="form-group">
				<input type="submit" value="Créer" class="btn btn-primary">
			</div>
	</form>
	<button>
		<a href="./">Annuler</a>
	</button>
</body>
</html>