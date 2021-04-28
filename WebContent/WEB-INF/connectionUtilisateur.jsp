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

			<c:if test="${empty sessionScope.user}">
				<form action="./connection" method="post">
					<div class="form-group">
						<label for="login">Login : </label> <input class="form-control"
							id="login" required name="login" value="${login}">
					</div>
					<div class="form-group">
						<label for="password">Mot de passe : </label> <input
							class="form-control" id="password" required name="password"
							type="password">
					</div>
					<div class="form-group">
						<input type="submit" value="Connexion"
							class="btn btn-lg btn-primary">
					</div>

					<div class="form-group">
						<input type="checkbox" name="remember-me"><label
							for="remember-me"> Se souvenir de moi </label>
					</div>
				</form>

				<a href="./inscription">
					<button class="btn btn-lg btn-primary btn-block" value="">Créer un compte</button>
				</a>
			</c:if>

		</div>
		<div class="col-sm-4"></div>
	</div>
	<%@include file="template/script.jsp"%>

</body>
</html>