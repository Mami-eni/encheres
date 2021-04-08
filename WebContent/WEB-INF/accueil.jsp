<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>

<%@ include file="template/head.jsp"%>

<body>
	<%@ include file="template/nav.jsp"%>

	
	  <div class="row">
	      <div class="col-sm-2"></div>
	      <div class="col-sm-8"><p class="title">Liste des enchères</p></div>
	      <div class="col-sm-2"></div>
      </div>
      
      <%@ include file="template/messageErreur.jsp"%>
      
      
	  <div class="row">
	      <div class="col-sm-2"></div>
	      
	      <div class="col-sm-8">
	     
	      		
      		<form method="post"action="${pageContext.request.contextPath}/encheres">
	      			
	      		<div class = "row"> 
	      		<div class = "col-sm-6"> 
	      			<h1>Filtres :  </h1>
	      			<p>
	      			
	      				
	      				<input type="search" class="form-control" id="searchFiltre"name="search">
	      			
	      			</p>
	      			
						
					<p>
						
						<label for="categories" class="form-label">Catégorie : </label> 
						
						<select
							name="categories" id="categories">
							<option value="toutes">Toutes</option>
	
							<c:forEach var="categorie" items="${listeCategorie}">
								<option value="${categorie.numero}">${categorie.libelle}</option>
							</c:forEach>
						</select>
					
					</p>	
					
					<c:if test="${!empty sessionScope.user }">
						
							<div class= "row ">
							
	
								<fieldset class = "col-sm-6">
									<input type="radio" id="radioAchat" name="filtreRadio"
									value="achats" onClick="radioDisable()" checked>Achats<br>
	
									 <input type="checkbox" name="flitreCheckboxAchat"value="ouvertes"> enchères ouvertes<br>
									 <input type="checkbox" name="flitreCheckboxAchat" value="mesEncheres">mes enchères <br> 
									 <input type="checkbox" name="flitreCheckboxAchat" value="enchereObtenues"> mes enchères remportées<br> <br>
									 
								</fieldset>
								
								<fieldset class = "col-sm-6">
									<input type="radio" id="radioVente" name="filtreRadio"
									value="ventes" onClick="radioDisable()">Mes ventes<br>
	
									<input type="checkbox" name="flitreCheckboxVente"value="encours" disabled=true> mes ventes en cours<br>
									<input type="checkbox" name="flitreCheckboxVente"value="attente" disabled=true> ventes non débutées<br>
									<input type="checkbox" name="flitreCheckboxVente" value="clos"disabled=true> ventes terminées<br> <br>
	
								</fieldset>
							</div>
							

						</c:if>		
						</div>
						<div class = "col-sm-2"> </div>
						<div class = "col-sm-4 conteneur-bouton">
						
		      
							<button type="submit" class="btn btn-primary">Rechercher</button>
			  
	     		 		</div>
	     		 		
	     		 	</div>
	      			
				 </form>
	      		
     		  
	      			
	      	 </div>
	      
	      
	      
	      <div class="col-sm-2"></div>
	      
	      
      </div>
      
      
     <div class="row">
      <div class="col-sm-2"></div>
      <div class="col-sm-8">
	      
	      
	      	<c:choose>
				<c:when test="${listeArticles.size() == 0}">
					<p>Pas d'enchères en cours</p>
				</c:when>
	
					<c:otherwise>
						<div class = "row">	
							<c:forEach var="article" items="${listeArticles}">
								<div class= "col-sm-6"> 
									<div class="card">

											<div class="row g-0">
												<div class="col-md-4 conteneur-image">
												<c:set var="booleanImage" value="false"/>
												<c:set var="compare" value="img_article_${article.numero }.jpg"/>
												<c:forEach var="image" items="${listeImages }">
												<c:if test="${fn:contains(image.getName(), compare) }">
												<c:set var="booleanImage" value="true"/>
												</c:if>
												</c:forEach>
												<c:choose>
												<c:when test="${booleanImage == false }">
												<img src="./img/interrogation.png" alt="test-image">
												</c:when>
												<c:otherwise>
												<img src="./imagesArticles/${compare }" alt="article">
												</c:otherwise>
												</c:choose>
												</div>
												<div class="col-md-8">
													<div class="card-body">

														<c:choose>
															<c:when test="${empty sessionScope.user}">
	
																<p class="card-text">${article.nom}</p>
	
																<p class="card-text">Prix: ${meilleureEnchere.get(article.getNumero())} points </p>
	
																<p class="card-text">Fin de l'enchère:${article.dateFinEncheres}</p>
																<p class="card-text">Vendeur:${article.getUtilisateur().pseudo}</p>
	
															</c:when>
	
															<c:otherwise>
	
																<c:choose>
	
																	<c:when test="${article.getUtilisateur().numero == user.numero}">
	
	
																		<c:choose>
																				<c:when test="${article.etatVente == 'encours'}">
																					<a class="card-text"
																						href="${pageContext.request.contextPath}/DetailVenteServlet?article=${article.numero}">${article.nom}
	
																						${article.etatVente} </a>
	
																				</c:when>
	
																			
																				<c:when test="${article.etatVente == 'non_debuté'}">
																					<a class="card-text"
																						href="${pageContext.request.contextPath}/UpdateSaleServlet?article=${article.numero}">${article.nom}
																						${article.etatVente} </a>
	
																				</c:when>
	
																				<c:when test="${article.etatVente== 'fini'}">
																					<a class="card-text"
																						href="${pageContext.request.contextPath}/AutreRemporteVenteServlet?article=${article.numero}">${article.nom}
																						${article.etatVente} </a>
	
																				</c:when>
	
																		</c:choose>
	
																	</c:when>
	
	
																	<c:otherwise>
	
																		<c:choose>
																			<c:when test="${article.etatVente== 'encours'}">
																				<a class="card-text"
																					href="${pageContext.request.contextPath}/DetailVenteServlet?article=${article.numero}">${article.nom}
																					${article.etatVente} </a>
	
																			</c:when>
	
																			<c:when test="${article.etatVente== 'fini'}">
																				<a class="card-text"
																					href="${pageContext.request.contextPath}/VenteRemporteServlet?article=${article.numero}">${article.nom}
																					${article.etatVente} </a>
	
																			</c:when>
																	</c:choose>
	
	
																	</c:otherwise>
	
																</c:choose>
	
																<p class="card-text">Prix: ${meilleureEnchere.get(article.getNumero())} points </p>
																<p class="card-text">Fin de l'enchère:
																	${article.dateFinEncheres}</p>
																<a class="card-text"
																	href="${pageContext.request.contextPath}/afficherProfil?vendeur=${article.getUtilisateur().numero}">Vendeur:
																	${article.getUtilisateur().pseudo}</a>
	
															</c:otherwise>
	
														</c:choose>

													</div>
																										
												</div>
												
											</div>
											
										</div>
							
									 </div>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>	
				</div>		
	      </div>

	    <div class="col-sm-2"></div>
     
	<%@ include file="template/script.jsp"%>

</body>
</html>