<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<%@ include file="template/head.jsp"%>

<body>
	<%@ include file="template/nav.jsp"%>






	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">

			<div class="card mb-3">

				<div class="card-body">
					<h5 class="card-title">Liste des enchères</h5>
					<p class="card-text">
					<form method="post"
						action="${pageContext.request.contextPath}/encheres">
						<div class="container-fluid justify-content-between">
							<div>
								<div class="mb-3">
									<label for="searchFiltre" class="form-label">Filtre: </label> <input
										type="search" class="form-control" id="searchFiltre"
										name="search">
								</div>
								<div class="mb-3">
									<label for="categories" class="form-label">Categorie</label> <select
										name="categories" id="categories">
										<option value="toutes">Toutes</option>

										<c:forEach var="categorie" items="${listeCategorie}">
											<option value="${categorie.numero}">${categorie.libelle}</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div>
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>

						</div>
						<!--  affichage de ces data si un user est connecté -->
						<c:if test="${!empty sessionScope.user }">

							<input type="radio" id="radioAchat" name="filtreRadio"
								value="achats" onClick="radioDisable()" checked>Achats<br>
							<input type="radio" id="radioVente" name="filtreRadio"
								value="ventes" onClick="radioDisable()">Mes ventes<br>

							<fieldset>
								<legend>achats</legend>

								<input type="checkbox" name="flitreCheckboxAchat"
									value="ouvertes" > enchères ouvertes<br> <input
									type="checkbox" name="flitreCheckboxAchat" value="mesEncheres">
								mes enchères <br> <input type="checkbox"
									name="flitreCheckboxAchat" value="enchereObtenues">
								mes enchères remportées<br> <br>
							</fieldset>
							<fieldset>
								<legend>ventes</legend>

								<input type="checkbox" name="flitreCheckboxVente"
									value="encours" disabled=true> mes ventes en cours<br> <input
									type="checkbox" name="flitreCheckboxVente" value="attente" disabled=true>
								ventes non débutées<br> <input type="checkbox"
									name="flitreCheckboxVente" value="clos" disabled=true> ventes
								terminées<br> <br>

							</fieldset>

						</c:if>
					</form>
					</p>

					<p class="card-text">
					<div class="row">

						<c:choose>
							<c:when test="${listeArticles.size() == 0}">
								<p>Pas d'enchères en cours</p>
							</c:when>

							<c:otherwise>

								<c:forEach var="article" items="${listeArticles}">
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

																<p class="card-text">${article.nom}</p>
																<p class="card-text">Prix:
																	${meilleureEnchere.get(article.getNumero())}</p>
																<p class="card-text">Fin de l'enchère:
																	${article.dateFinEncheres}</p>
																<p class="card-text">Vendeur:
																	${article.getUtilisateur().pseudo}</p>

															</c:when>
															<c:otherwise>

																<c:choose>

																	<c:when
																		test="${article.getUtilisateur().numero == user.numero}">



																		<c:choose>
																			<c:when test="${article.etatVente == 'encours'}">
																				<a class="card-text"
																					href="${pageContext.request.contextPath}/UpdateSaleServlet?article=${article.numero}">${article.nom}
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



																<p class="card-text">Prix:
																	${meilleureEnchere.get(article.getNumero())}</p>
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

					</div>

					</c:forEach>

					</c:otherwise>
					</c:choose>
					</p>


				</div>
			</div>
		</div>
		<div class="col-sm-2"></div>
	</div>

	<%@ include file="template/script.jsp"%>

</body>
</html>