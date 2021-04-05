<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<%@ include file="template/head.jsp"%>

<body>
<%@ include file="template/nav.jsp"%>

       	  
          
          
     

      <div class = "row">
        <div class = "col-sm-2">
        </div>
        <div class = "col-sm-8">
        
    <div class="card mb-3">
       
        <div class="card-body">
          <h5 class="card-title"> Liste des enchères </h5>
          <p class="card-text">
            
				<form method="post" action="${pageContext.request.contextPath}/encheres">
				 <div class="container-fluid justify-content-between">
					<div>
						  <div class="mb-3">
						    <label for="searchFiltre" class="form-label">Filtre: </label>
						    <input type="search" class="form-control" id="searchFiltre" name="search">
						  </div>
							 <div class="mb-3">
						     <label for="categories" class="form-label">Categorie</label>
						     <select name="categories" id="categories">
						   	 <option value="toutes">Toutes</option>
						   	 
						   	 <c:forEach var="categorie" items="${listeCategorie}">
							  <option value="${categorie.libelle}">${categorie.libelle}</option>
							  </c:forEach>
							</select>
							</div>
					 </div>
					 
					 <div>
					 	 <button type="submit" class="btn btn-primary">Submit</button>
					  </div>
					  
					 </div>
					 <!--  affichage de ces data si quelqu'un est connecté -->
					 <c:if test="${!empty sessionScope.user }">
					 
					  <input type="radio" name="filtreRadio" value="achats">Achats<br>
					    <input type="radio" name="filtreRadio" value="ventes">Mes ventes<br>
					    					 
					  <fieldset>      
				        <legend>achats</legend>      
				        <input type="checkbox" name="flitreCheckboxAchat" value="1" ${disabledAchat}>1<br>      
				        <input type="checkbox" name="flitreCheckboxAchat" value="2" ${disabledAchat}>2<br>      
				        <input type="checkbox" name="flitreCheckboxAchat" value="3" ${disabledAchat}>3<br>      
				        <br> 
				        </fieldset> 
				        <fieldset>      
				        <legend>ventes</legend>      
				        <input type="checkbox" name="flitreCheckboxVente" value="1" ${disabledVentes}>1<br>      
				        <input type="checkbox" name="flitreCheckboxVente" value="2" ${disabledVentes}>2<br>      
				        <input type="checkbox" name="flitreCheckboxVente" value="3" ${disabledVentes}>3<br>      
				        <br>       
				          
				    </fieldset> 
					 
					</c:if>
				</form>
          </p>
          
          <p class="card-text">
          	
          	<div class="row">
          	
          	 <c:choose>
          	 <c:when test="${listeEnchere.size() == 0}">
          	 <p> Pas d'enchères en cours</p>
          	 </c:when>
          	 
          	 <c:otherwise>
          	 
          	 <c:forEach var="enchere" items="${listeEnchere}">
			  <div class="col-sm-6">
			  
			 
			  
			    <div class="card mb-3">
			    
				  <div class="row g-0">
				    <div class="col-md-4">
				      <img src="images/test2" alt="test-image">
				    </div>
				    <div class="col-md-8">
				      <div class="card-body">
				       
				       <c:choose>
					       <c:when test="${empty sessionScope.user}">
					       	
					      	 <p class="card-text">${enchere.getArticle().nom} ${enchere.getArticle().description}</p>
				        	 <p class="card-text">${enchere.montant}</p>
				          	<p class="card-text">${enchere.getArticle().dateFinEncheres}</p>
				           	<p class="card-text">${enchere.getArticle().getUtilisateur().pseudo}</p>
					       
					       </c:when>
					       
					       <c:otherwise>
					       
					       	
					       	 <a  class="card-text" href= "${pageContext.request.contextPath}/DetailVenteServlet?article=${enchere.getArticle().numero}">${enchere.getArticle().nom} ${enchere.getArticle().description}</a>
				        	 <p class="card-text">${enchere.montant}</p>
				          	<p class="card-text">${enchere.getArticle().dateFinEncheres}</p>
				           	<a  class="card-text" href= "${pageContext.request.contextPath}/afficherProfil?vendeur=${enchere.getArticle().getUtilisateur().numero}">${enchere.getArticle().getUtilisateur().pseudo}</a>
					       
					       </c:otherwise>
				       
				      	   </c:choose>
				       
				       
				       
					      </div>
					    </div>
					  </div>
					</div>
				    </div>
				 	</div>
			 	
			 	</c:forEach>
			 	
			 	</c:otherwise>
			 </c:choose>
          </p>
         
         
        </div>
    </div>
    </div>
    <div class = "col-sm-2">
    </div>
</div>

<%@ include file="template/script.jsp"%>
</body>
</html>