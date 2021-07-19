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
		<div class="col-2"></div>
		<div class="col-6">



			<%@include file="template/messageErreur.jsp"%>

			<c:if test="${empty sessionScope.user}">
				<form action="./connection" method="post">
				
					<div class="form-group row">
						<div class = "col-sm-4">
				
							<label for="login">Login : </label>
						</div>
						
						<div class = "col-sm-8 ">
						 	<input class="form-control"
							id="login" required name="login" value="${login}">
						</div>
						
					</div>
					
					<div class="form-group row">
					
						<div class = "col-sm-4">
						
							<label for="password">Mot de passe : </label> 
						</div>
						
						<div class = "col-sm-8">
							<input
								class="form-control" id="password" required name="password"
								type="password">
						</div>
						
					</div>
					
					
					<div class="form-group row">
					
						<input type="submit" value="Connexion"class="btn btn-lg btn-primary col-sm-4 ">
						<div  class="col-sm-4" >
							<a href="./modifierUser">
							<button class="btn btn-lg btn-primary btn-block" value="">Créer un compte</button>
							</a>
						</div>
					</div>

					<div class="form-group">
						<input type="hidden" name="remember-me"><label
							for="remember-me">  </label>
						
							
					</div>
						
				</form>

			
			</c:if>

		</div>
		<div class="col-2"></div>
	</div>
	<%@include file="template/script.jsp"%>

</body>
</html>