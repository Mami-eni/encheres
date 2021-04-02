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
			<c:if test="${empty sessionScope.user}">
				<form action="./connection" method="post">
					<div class="form-group">
						<label for="login">Login : </label> <input class="form-control"
							id="login" required name="login">
					</div>
					<div class="form-group">
						<label for="password">Password : </label> <input
							class="form-control" id="password" required name="password"
							type="password">
					</div>
					<div class="form-group">
						<input type="submit" value="Connexion"
							class="btn btn-lg btn-primary">
					</div>

					<!-- 	TODO Implémenter Remember me 		-->
					<!-- 			<div class="form-group"> -->
					<!-- 				<input type="checkbox" name="remember-me"><label -->
					<!-- 					for="remember-me">Se souvient de moi </label> -->
					<!-- 			</div> -->
				</form>

				<a href="./inscription">
					<button class="btn btn-lg btn-primary btn-block" value="">Créer
						un compte</button>
				</a>
			</c:if>
			<c:if test="${!empty sessionScope.user}">
				<span>
					<h1>Bienvenu ${user.pseudo}!</h1> 
					<a href="./reussi">
						<button class="btn btn-primary" value="">Mon profil</button>
					</a>
				<form action="./deconnexion" method="post">
					<input type="submit" value="Déconnexion" class="btn btn-primary">
				</form>
				</span>
			</c:if>
		</div>
		<div class="col-sm-4"></div>
	</div>
	<%@include file="template/script.jsp"%>

</body>
</html>