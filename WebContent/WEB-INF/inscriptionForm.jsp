<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp" isErrorPage="false"%>

<!DOCTYPE html>
<html>
<%@ include file="template/head.jsp"%>
<body>
	<%@ include file="template/nav.jsp"%>

	<div class="row">
		<div class="col-lg-3 col-sm-3 col-md-3"></div>
		<div class="col-xs-12 col-lg-6 col-sm-6 col-md-6">

			
			<br>
			<h1>Mon profil</h1>
			<br>
			
			<%@include file="template/messageErreur.jsp"%>
			
			<form action="./inscription" method="post">
				<div class="row">
					<div class="col-lg-6 col-xs-12 col-sm-6">

						<div class="form-group">
							<label for="pseudo" >Pseudo : </label> <input class="form-control"
								id="pseudo" name="pseudo">
						</div>
					</div>
					<div class="col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="nom">Nom : </label> <input class="form-control"
								id="nom" name="nom">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="prenom">Prénom : </label> <input class="form-control"
								id="prenom" name="prenom">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="telephone">Téléphone: </label> <input
								class="form-control" id="telephone" type="tel" name="telephone">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="codePostal">Code postal : </label> <input
								class="form-control" id="codePostal" name="codePostal">
						</div>
					</div>

				
					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="email">Email : </label> <input class="form-control"
								id="email" type="email" name="email">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="rue">Rue : </label> <input class="form-control"
								id="rue" name="rue">
						</div>
					</div>


					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">

						<div class="form-group">
							<label for="ville">Ville : </label> <input class="form-control"
								id="ville" name="ville">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">
						<div class="form-group">
							<label for="mdp">Mot de passe : </label> <input
								class="form-control" id="mdp" type="password" name="mdp">
						</div>
					</div>

					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">

						<div class="form-group">
							<label for="confirmation">Confirmation mot de passe : </label> <input
								class="form-control" id="confirmation" type="password"
								name="confirmation">
						</div>
					</div>


					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">

						<div class="form-group">
							<input type="submit" value="Créer" class="btn btn-primary btn-block">
						</div>
					</div>
					<div class="col-lg-offset-0 col-lg-6 col-xs-12 col-sm-6">

						<a href="./"  class="btn btn-primary btn-block">Annuler</a>
					</div>
				</div>

			</form>


		</div>
	</div>
	<div class="col-lg-3 col-sm-3 col-md-3"></div>
	<%@ include file="template/script.jsp"%>
</body>
</html>