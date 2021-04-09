<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page errorPage="error.jsp" isErrorPage="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modifier une vente</title>
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="font.css" rel="stylesheet">
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="template/navLogo.jsp"%>
      
      <div class="row">
      <div class="col-sm-4"></div>
      <div class="col-sm-4"><p class="title">Modifier Vente</p></div>
      <div class="col-sm-4"></div>
      </div>
      
      <%@include file="template/messageErreur.jsp"%>

      <div class = "row">
        <div class = "col-sm-2">
        </div>
        <div class="col-sm-2">
        <c:choose>
      	<c:when test="${empty image}">
        <img class="img" src="./img/interrogation.png" alt="point interrogation"
				width="50px">
				</c:when>
		<c:otherwise>
		<img class="img" src="./imagesArticles/${image }" alt="article"
				width="200px">
		</c:otherwise>
		</c:choose>
        </div>
        <div class="col-sm-6">
    
          
          <form class="form" action="UpdateSaleServlet" method="post" enctype="multipart/form-data">
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="article">Article</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="article" id="article" value="${article.nom}">
          </div>
          </div>
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="description">Description</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="description" id="description" value="${article.description}">
          </div>
          </div>
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="catselect">Catégorie</label>
          </div>
          <div class="col-sm-6">
          <select class="selectcategorie" name="selectcat" >
					<c:forEach var="cat" items="${categories }">
						<c:if test="${cat.numero == article.categorie.getNumero()}">
						<option value="${cat.numero }" selected>${cat.libelle}</option>
						</c:if>
						<option value="${cat.numero }">${cat.libelle}</option>
					</c:forEach>
				</select> 
				</div>
			</div>
			
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="map">Photo de l'article</label>
			</div>
			<div class="col-sm-6">
			<input class="btn btn-primary me-2" type="file" name="upload" id="upload">
			</div>
			</div>
			
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="map">Mise à Prix</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="number"name="map" id="map" value="${article.prixInitial}">
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="debutEnchere">Début de l'enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="debutEnchere"id="debutEnchere" value="${dateDebut}">
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="finEnchere">Fin de l'Enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="finEnchere" id="finEnchere" value="${dateFin}">
			</div>
			</div>
			<fieldset class="fieldset">
					<legend align="left">Retrait</legend>
			
			<div class="form-group row">
			<div class="col-sm-2">
			</div>
			<div class="col-sm-4">	
			<label for="rue">Rue</label>
			</div>
			<div class="col-sm-4">
			<input class="form-control" type="text" name="rue" id="rue" value="${retrait.rue}">
			</div>
			<div class="col-sm-2">
			</div>
			</div>	
			<div class="form-group row">
			<div class="col-sm-2">
			</div>
			<div class="col-sm-4">
			<label for="cp">Code Postal</label> 
			</div>
			<div class="col-sm-4">
			<input class="form-control" type="text" name="cp" id="cp" value="${retrait.codePostal}">
			</div>
			<div class="col-sm-2">
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-2">
			</div>
			<div class="col-sm-4">
			<label for="ville">Ville</label>
			</div>
			<div class="col-sm-4">
			<input class="form-control" type="text" name="ville" id="ville" value="${retrait.ville}">
			</div>
			<div class="col-sm-2">
			</div>
			</div>
			</fieldset>
			<div class="form-group row">
			<div class="col-sm-4">
			<input class="btn btn-primary me-2" class="submit" type="submit" value="Enregistrer">
			</div>
			</form>
			<div class="col-sm-4">
			<form action="/encheres">
			<button class="btn btn-primary me-2" class="annuler">Annuler</button>
			</form>
			</div>
			<div class="col-sm-4">
			<form action="DeleteSaleServlet" method="get">
			<input type="hidden" name="article" value="${article.numero }">
			<input class="btn btn-primary me-2" class="submit" type="submit" value="Annuler la vente">
			</form>
			</div>
			</div>
			
          
          </div>
          
       
    
   
    <div class = "col-sm-2">
    </div>
    </div>




    <%@ include file="template/script.jsp" %>


</body>
</html>