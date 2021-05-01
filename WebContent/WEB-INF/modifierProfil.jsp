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
	      <div class="col-sm-3"></div>
	      <div class="col-sm-6"><p class="title">Mon profil</p></div>
	      <div class="col-sm-3"></div>
      </div>

	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<%@include file="template/messageErreur.jsp"%>
			
		

			<form action="./modifierUser" method="post">
			
			<div class = "row">
			<div class=" col-sm-6 col-lg-6 col-xs-12 ">
			
				<div class="row">
					<div class="  col-sm-4 "> 
						<label for="pseudo">Pseudo : </label>
					</div>
					<div class="col-sm-8 ">
						 <input class="form-control"
							id="pseudo" name="pseudo" value="${sessionScope.user.pseudo}">
					</div>
					
				</div>
		
									
					<div class="row">
						<div class="  col-sm-4 "> 
							<label for="nom">Nom : </label> 
						</div>	
						
						<div class="  col-sm-8 "> 
							<input class="form-control"
								id="nom" name="nom" value="${sessionScope.user.nom}">
						</div>	
						
					</div>
			
			
			
			
				<div class="row">
					<div class="  col-sm-4 "> 
						<label for="prenom">Prénom : </label> 
					</div>
					<div class="  col-sm-8 "> 
						<input class="form-control"
							id="prenom" name="prenom" value="${sessionScope.user.prenom}">
					</div>
					
				</div>
			
			
			
			
				<div class="row">
					<div class="  col-sm-4 "> 
						<label for="telephone">Téléphone: </label>
					</div>
					<div class="  col-sm-8"> 
					
						 <input class="form-control" id="telephone" type="tel" name="telephone"
							value="${sessionScope.user.telephone}">
					</div>
				</div>
				
				
				
				<div class="row">
					<div class=" col-sm-4 ">
						<label for="email">Email : </label> 
					</div>
					<div class=" col-sm-8 ">
						<input class="form-control"
							id="email" type="email" name="email"
							value="${sessionScope.user.email}">
					</div>
				</div>
				
				
				<c:if test="${!empty sessionScope.user }">
				
					<div class="col-lg-6 col-xs-12 col-sm-6 credit">
						<div class="form-group">
							Crédit: ${sessionScope.user.credit}
						</div>
					</div>
				</c:if>	
			
			
				
			</div>
				
				
			<div class="col-lg-6 col-xs-12 col-sm-6"> 
			
		
				<div class="row">
					<div class=" col-sm-4 ">
						<label for="rue">Rue : </label> 
					</div>
					<div class=" col-sm-8 ">
						<input class="form-control"
							id="rue" name="rue" value="${sessionScope.user.rue}">
					</div>
					
				</div>
			
				
			
				<div class="row">
					<div class=" col-sm-4 ">
						<label for="ville">Ville : </label>
					</div>
					<div class=" col-sm-8">
						 <input class="form-control"
							id="ville" name="ville" value="${sessionScope.user.ville}">
					</div>
				</div>
				
				
					<div class="row">
					<div class="  col-sm-4 "> 
				
						<label for="codePostal">Code postal : </label>
					</div>	
					<div class="  col-sm-8 ">
						 <input class="form-control" id="codePostal" name="codePostal"
							value="${sessionScope.user.codePostal}">
					</div>
					
				</div>
				
				
				<div class="row">
					<div class=" col-sm-4 "> 
						<label for="mdp">Mot de passe : </label> 
					</div>
					<div class=" col-sm-8 "> 
						<input class="form-control" id="mdp" type="password" name="mdp"
							value="${sessionScope.user.motDePasse}">
					</div>
					
				</div>
			
			
			
				<div class="row">
					<div class=" col-sm-4">
				
						<label for="confirmation">Confirmation : </label> 
					</div>
					<div class=" col-sm-8">
					
						<input class="form-control" id="confirmation" type="password"
							name="confirmation" value="${sessionScope.user.motDePasse}">
					</div>
				</div>
			</div>
		</div>
		
			
				
			<div class="col-lg-12 col-xs-12 col-sm-12 boutons-profil">
			
				
				
					<c:choose>
					<c:when test="${empty sessionScope.user }">
						
				
				 	 <input type="submit" value="Créer" name = "creer" class="btn btn-primary">
				 
				 	 
				 
				 	 
				 	 <a href="./" class="btn btn-primary">Annuler</a>
				 	
					</c:when>
					<c:otherwise>
					
						<input type="submit" value="Enregistrer" name = "enregistrer" class="btn btn-primary">
					
						<input
						onclick="return confirm('Etes-vous sûr de vouloir supprimer votre compte?');"
						class="btn btn-primary" type="submit" value="Supprimer mon compte" name = "supprimer">
						
						<a href="./" class="btn btn-primary">Annuler</a>
					</c:otherwise>
					</c:choose>				
				
				 
			
				
				
			</div>
				
			</form>

			
			
		</div>
	</div>
	<div class="col-sm-3"></div>
	<%@include file="template/script.jsp"%>
</body>


</html>

