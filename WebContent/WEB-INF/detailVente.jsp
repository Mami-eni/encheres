<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Détail Vente</title>
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="font.css" rel="stylesheet">
</head>
<body>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<nav class="navbar navbar-light bg-light">
        <form class="container-fluid justify-content-start">
          <button class="btn btn-outline-success me-2" type="button">Main button</button>
          <button class="btn btn-sm btn-outline-secondary" type="button">Smaller button</button>
        </form>
      </nav>
      
      <div class="row">
      <div class="col-sm-4"></div>
      <div class="col-sm-4"><p class="title">Détail Vente</p></div>
      <div class="col-sm-4"></div>
      </div>
      
      <c:if test="${!empty erreur}">
      <c:forEach var="error" items="${erreur }">
      <div class="row">
      <div class="col-sm-4"></div>
      <div class="col-sm-8"><p class="erreur">${error}</p></div>
      </div>
      </c:forEach>
      </c:if>
      
      <div class="row">
       <div class="col-sm-2">
        </div>
      <div class="col-sm-2">
        <img class="img" src="./img/interrogation.png" alt="point interrogation"
				width="50px">
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
        
        <div class="row">
        <div class="col-sm-6">
        <label for="proposition">Ma proposition</label>
        </div>
        <div class="col-sm-6">
        <form class="detailventeform" action="DetailVenteServlet" method="post">
        <input type="hidden" value="${article.numero }" name="article">
        <c:choose>
        <c:when test="${empty enchere.date }">
        <input type="number" name="proposition" id="proposition" min="${article.prixInitial }" step="1" style="width: 80px" required>
        </c:when>
        <c:otherwise>
        <input type="number" name="proposition" id="proposition" min="${enchere.montant }" step="1" style="width: 80px" required>
        </c:otherwise>
        </c:choose>
        <input type="submit" value="encherir">
        </form>
        </div>
        </div>
        
        </div>
    <div class = "col-sm-2">
    </div>
    </div>

</body>
</html>