<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp" isErrorPage="false"%>

<!DOCTYPE html>
<html>
<%@ include file="template/head.jsp"%>
<body>
	<%@ include file="template/nav.jsp"%>

	<div class="row">
		<div class="col-sm-4"></div>
		<div class="col-sm-4">
			<%@include file="template/messageErreur.jsp"%>

			<form action="./inscription" method="post">
				<div class="form-group">
					<label for="pseudo">Pseudo : </label> <input class="form-control"
						id="pseudo" name="pseudo">
				</div>
				<div class="form-group">
					<label for="nom">Nom : </label> <input class="form-control"
						id="nom" name="nom">
				</div>

				<div class="form-group">
					<label for="prenom">Prénom : </label> <input class="form-control"
						id="prenom" name="prenom">
				</div>
				<div class="form-group">
					<label for="telephone">Téléphone: </label> <input
						class="form-control" id="telephone" type="tel" name="telephone">
				</div>

				<div class="form-group">
					<label for="codePostal">Code postal : </label> <input
						class="form-control" id="codePostal" name="codePostal">
				</div>
				<div class="form-group">
					<label for="mdp">Mot de passe : </label> <input
						class="form-control" id="mdp" type="password" name="mdp">
				</div>

				<div class="form-group">
					<label for="email">Email : </label> <input class="form-control"
						id="email" type="email" name="email">
				</div>
				<div class="form-group">
					<label for="rue">Rue : </label> <input class="form-control"
						id="rue" name="rue">
				</div>
				<div class="form-group">
					<label for="ville">Ville : </label> <input class="form-control"
						id="ville" name="ville">
				</div>
				<div class="form-group">
					<label for="confirmation">Confirmation : </label> <input
						class="form-control" id="confirmation" type="password"
						name="confirmation">
				</div>
				<div class="form-group">
					<input type="submit" value="Créer" class="btn btn-primary">
				</div>
			</form>

			<a href="./"><button class="btn btn-primary">Annuler</button></a>

		</div>
		<div class="col-sm-4"></div>
	</div>
	<%@ include file="template/script.jsp"%>
</body>
</html>