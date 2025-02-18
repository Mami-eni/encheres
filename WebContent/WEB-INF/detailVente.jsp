<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page errorPage="error.jsp" isErrorPage="false"%>
<!DOCTYPE html>
<html>
<%@ include file="template/head.jsp" %>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@include file="template/navLogo.jsp"%>

      
      <div class="row">
      <div class=" col-sm-4"></div>
      <div class=" col-sm-6"><p class="title">Détail Vente</p></div>
      <div class=" col-sm-4"></div>
      </div>
      
      <%@include file="template/messageErreur.jsp"%>
      
      <div class="row">
       <div class="col-sm-2">
        </div>
      <div class="col-sm-2 text-center">
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
        <div class="col-sm-6 text-center">
        
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
	        <p>Catégorie</p>
	        </div>
	        <div class="col-sm-6">
	        <p>${article.categorie.libelle}</p>
	        </div>
	        </div>
	        
	        <c:if test="${!empty enchere.date }">
	        <div class="row">
	        <div class="col-sm-6">
	        <p>Meilleure Offre</p>
	        </div>
	        <div class="col-sm-6">
	        <p>${enchere.montant } pts par ${enchere.utilisateur.pseudo}</p>
	        </div>
	        </div>
	        </c:if>
	        
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
	        <p>Fin de l'enchère</p>
	        </div>
	        <div class="col-sm-6">
	        <p>${dateFin}</p>
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
	        
	        <c:if test="${article.utilisateur.numero != user.numero }">
	        <div class="row">
	        <div class="col-sm-6">
	        <label for="proposition">Ma proposition</label>
	        </div>
	        <div class="col-sm-6">
	        <form class="detailventeform" action="DetailVenteServlet" method="post">
	        <input type="hidden" value="${article.numero }" name="article">
	        <c:choose>
	        <c:when test="${empty enchere.date }">
	        <input type="number" name="proposition" id="proposition" min="${article.prixInitial + enchMin }" step="1" style="width: 80px" required>
	        </c:when>
	        <c:otherwise>
	        <input type="number" name="proposition" id="proposition" min="${enchere.montant + enchMin }" step="1" style="width: 80px" required>
	        </c:otherwise>
	        </c:choose>
	        <input class="btn btn-primary me-2" type="submit" value="encherir">
	        </form>
	        </div>
	        </div>
	        </c:if>
        
        </div>
    <div class = "col-sm-2">
    </div>
    </div>
<%@ include file="template/script.jsp" %>
</body>
</html>