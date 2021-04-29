    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid justify-content-between">
       	  <a href= "${pageContext.request.contextPath}/encheres">
       	  <img alt="logo" src="img/logo.png">
       	  </a>
       	  
       	  <c:choose>
	       	  <c:when test="${empty sessionScope.user }">
	       	  	<div>
		       	  	<a  class="btn btn-primary me-2" href= "${pageContext.request.contextPath}/modifierUser">S'inscrire</a>
		            <a class="btn btn-primary me-2"href= "${pageContext.request.contextPath}/connection">Se connecter</a>
		       	  </div>
	       	  </c:when>
	       	  
	       	  <c:otherwise>
	       	  	<div>
		       	  	<a class="btn btn-primary me-2" href= "${pageContext.request.contextPath}/encheres">Enchères</a>
		            <a class="btn btn-primary me-2"href= "${pageContext.request.contextPath}/NewSaleServlet">Vendre un article</a>
		            <a class="btn btn-primary me-2"href= "${pageContext.request.contextPath}/monprofil">Mon profil</a>
		            <a class="btn btn-primary me-2"href= "${pageContext.request.contextPath}/deconnexion">Déconnexion</a>
		       	  </div>
	       	  
	       	  </c:otherwise>
       	  </c:choose>
       	  </div>
       	  </nav>
