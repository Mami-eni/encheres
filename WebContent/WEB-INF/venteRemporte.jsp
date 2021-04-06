<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vente Remportée</title>
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
      <div class="col-sm-6"><p class="title">Vous avez remporté la vente</p></div>
      <div class="col-sm-2"></div>
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
        <input type="submit" value="Back">
        </form>
        </div>
        </div>
        
        </div>
    <div class = "col-sm-2">
    </div>
    </div>

</body>
</html>