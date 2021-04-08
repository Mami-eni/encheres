<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="template/head.jsp" %>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="template/navLogo.jsp"%>
      
      <div class="row">
      <div class="col-sm-4"></div>
      <div class="col-sm-6"><p class="title">Vous avez remporté la vente</p></div>
      <div class="col-sm-2"></div>
      </div>
      
      <%@include file="template/messageErreur.jsp"%>
      
      <div class="row">
       <div class="col-sm-2">
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
        
        <div class="row">
        <div class="col-sm-12">
        <p class="displayArticle">${article.nom}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Description</p>
        </div>
        <div class="col-sm-6">
        <p>${article.description}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Meilleure Offre</p>
        </div>
        <div class="col-sm-6">
        <p>${enchere.montant } pts</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Mise à prix</p>
        </div>
        <div class="col-sm-6">
        <p>${article.prixInitial} points</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Retrait</p>
        </div>
        <div class="col-sm-6">
        <p>${retrait.rue}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        </div>
        <div class="col-sm-6">
        <p>${retrait.codePostal} ${retrait.ville }</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Vendeur</p>
        </div>
        <div class="col-sm-6">
        <p>${article.utilisateur.pseudo}</p>
        </div>
        </div>
        
        <div class="row">
        <div class="col-sm-6">
        <p>Téléphone</p>
        </div>
        <div class="col-sm-6">
        <p>${article.utilisateur.telephone}</p>
        </div>
        </div>
        
         <div class="row">
        <div class="col-sm-6">
        <form action="VenteRemporteServlet" method="post">
        <input class="btn btn-primary me-2" type="submit" value="Back">
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