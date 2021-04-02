<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nouvelle Vente</title>
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
      <div class="col-sm-4"><p class="title">Nouvelle Vente</p></div>
      <div class="col-sm-4"></div>
      </div>
      <c:choose>
      <c:when test="${!empty erreur}">
      <c:forEach var="error" items="${erreur }">
      <div class="row">
      <div class="col-sm-4"></div>
      <div class="col-sm-8"><p class="erreur">${error}</p></div>
      </div>
      </c:forEach>
      <div class = "row">
        <div class = "col-sm-2">
        </div>
        <div class="col-sm-2">
        <img class="img" src="./img/interrogation.png" alt="point interrogation"
				width="50px">
        </div>
        <div class="col-sm-6">
    
          
          <form class="form" action="NewSaleServlet" method="post">
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="article">Article</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="article" id="article" value="${article.nom }" required>
          </div>
          </div>
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="description">Description</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="description" id="description" value="${article.description }" required>
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
			<label for="map">Mise à Prix</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="number" min="0" step="1" name="map" id="map" value="${map }" required>
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="debutEnchere">Début de l'enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="debutEnchere"id="debutEnchere" value="${dateDebut }" required>
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="finEnchere">Fin de l'Enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="finEnchere" id="finEnchere" value="${dateFin }" required>
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
			<input class="form-control" type="text" name="rue" id="rue" value="${rue}" required>
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
			<input class="form-control" type="text" name="cp" id="cp" value="${cp}" required>
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
			<input class="form-control" type="text" name="ville" id="ville" value="${ville}" required>
			</div>
			<div class="col-sm-2">
			</div>
			</div>
			</fieldset>
			<div class="form-group row">
			<div class="col-sm-6">
			<input class="submit" type="submit" value="Enregistrer">
			</div>
			<div class="col-sm-6">
			<button class="annuler">Annuler</button>
			</div>
			</div>
          </form>
          </div>
    <div class = "col-sm-2">
    </div>
    </div>
      </c:when>
      <c:otherwise>

      <div class = "row">
        <div class = "col-sm-2">
        </div>
        <div class="col-sm-2">
        <img class="img" src="./img/interrogation.png" alt="point interrogation"
				width="50px">
        </div>
        <div class="col-sm-6">
    
          
          <form class="form" action="NewSaleServlet" method="post">
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="article">Article</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="article" id="article" required>
          </div>
          </div>
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="description">Description</label>
          </div>
          <div class="col-sm-6">
          <input class="form-control" type="text" name="description" id="description" required>
          </div>
          </div>
          <div class="form-group row">
          <div class="col-sm-6">
          <label for="catselect">Catégorie</label>
          </div>
          <div class="col-sm-6">
          <select class="selectcategorie" name="selectcat">
					<c:forEach var="cat" items="${categories }">
						<option value="${cat.numero }">${cat.libelle}</option>
					</c:forEach>
				</select> 
				</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="map">Mise à Prix</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="number" min="0" step="1" name="map" id="map" required>
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="debutEnchere">Début de l'enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="debutEnchere"id="debutEnchere" required>
			</div>
			</div>
			<div class="form-group row">
			<div class="col-sm-6">
			<label for="finEnchere">Fin de l'Enchère</label>
			</div>
			<div class="col-sm-6">
			<input class="form-control" type="date" name="finEnchere" id="finEnchere" required>
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
			<input class="form-control" type="text" name="rue" id="rue" value="${user.rue}" required>
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
			<input class="form-control" type="text" name="cp" id="cp" value="${user.codePostal}" required>
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
			<input class="form-control" type="text" name="ville" id="ville" value="${user.ville}" required>
			</div>
			<div class="col-sm-2">
			</div>
			</div>
			</fieldset>
			<div class="form-group row">
			<div class="col-sm-6">
			<input class="submit" type="submit" value="Enregistrer">
			</div>
			<div class="col-sm-6">
			<button class="annuler">Annuler</button>
			</div>
			</div>
          </form>
          </div>
          
       
    
   
    <div class = "col-sm-2">
    </div>
    </div>
    </c:otherwise>
	</c:choose>



    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


</body>
</html>