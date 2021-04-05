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
			<%@include file="template/messageErreur.jsp"%>

			<form action="./modifierUser" method="post">
				<div class="form-group">
					<label for="pseudo">Pseudo : </label> <input class="form-control"
						id="pseudo" name="pseudo" value="${sessionScope.user.pseudo}">
				</div>
				<div class="form-group">
					<label for="nom">Nom : </label> <input class="form-control"
						id="nom" name="nom" value="${sessionScope.user.nom}">

				</div>
				<div class="form-group">
					<label for="prenom">Prénom : </label> <input class="form-control"
						id="prenom" name="prenom" value="${sessionScope.user.prenom}">
				</div>
				<div class="form-group">
					<label for="telephone">Téléphone: </label> <input
						class="form-control" id="telephone" type="tel" name="telephone"
						value="${sessionScope.user.telephone}">
				</div>

				<div class="form-group">
					<label for="codePostal">Code postal : </label> <input
						class="form-control" id="codePostal" name="codePostal"
						value="${sessionScope.user.codePostal}">
				</div>


				<div class="form-group">
					<label for="mdp">Mot de passe : </label> <input
						class="form-control" id="mdp" type="password" name="mdp"
						value="${sessionScope.user.motDePasse}">
				</div>

				<div class="form-group">
					<label for="email">Email : </label> <input class="form-control"
						id="email" type="email" name="email"
						value="${sessionScope.user.email}">
				</div>
				<div class="form-group">
					<label for="rue">Rue : </label> <input class="form-control"
						id="rue" name="rue" value="${sessionScope.user.rue}">
				</div>
				<div class="form-group">
					<label for="ville">Ville : </label> <input class="form-control"
						id="ville" name="ville" value="${sessionScope.user.ville}">
				</div>
				<div class="form-group">
					<label for="confirmation">Confirmation : </label> <input
						class="form-control" id="confirmation" type="password"
						name="confirmation" value="${sessionScope.user.motDePasse}">
				</div>


				<div class="form-group">
					<input type="submit" value="Enregistrer" class="btn btn-primary">
				</div>
			</form>

			<a href="./"><button class="btn btn-primary">Annuler</button></a>
			<form action="./supprimerUser" method="post">
				<div class="form-group">
					<input
						onclick="return confirm('Etes-vous sûr de vouloir supprimer votre compte?');"
						class="btn btn-primary" type="submit" value="Supprimer mon compte">
				</div>
			</form>
		</div>
	</div>
	<div class="col-sm-4"></div>
	<%@include file="template/script.jsp"%>
</body>

<!-- POPUP POUR LE BOUTON SUPPRIMER -->
<script type="text/javascript">
	
</script>
</html>

