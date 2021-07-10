    <nav class="navbar navbar-light bg-light ">
        <div class=" inside_navBar container-fluid justify-content-between">
       	  <a href= "${pageContext.request.contextPath}/encheres">
       	  <img alt="logo" src="img/logo.png">
       	  </a>
       	  
       	  <c:choose>
	       	  <c:when test="${empty sessionScope.user }">
	       	  	
		       	  
		       	  <div>
		       	  
		       	  	<nav class="navbar navbar-expand-lg navbar-light bg-light">
					 
					  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					    <span class="navbar-toggler-icon"></span>
					  </button>
					  <div class="collapse navbar-collapse" id="navbarSupportedContent">
					    <ul class="navbar-nav mr-auto">
					      <li class="nav-item active">
					        <a class="nav-link  me-2" href="${pageContext.request.contextPath}/modifierUser">S'inscrire</a>
					      </li>
					      <li class="nav-item">
					        <a class="nav-link  primary me-2" href="${pageContext.request.contextPath}/connection">Se connecter</a>
					      </li>
					    					     
					    </ul>
					  </div>
					</nav>
					
		       	  </div>
	       	  </c:when>
	       	  
	       	  <c:otherwise>
	       	  
		       	  
		       	   	  <div>
		       	  
			       	  	<nav class="navbar navbar-expand-lg navbar-light bg-light">
						 
						  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
						    <span class="navbar-toggler-icon"></span>
						  </button>
						  <div class="collapse navbar-collapse" id="navbarSupportedContent">
						    <ul class="navbar-nav mr-auto">
						      <li class="nav-item active">
						        <a class="nav-link btn btn-primary me-2" href= "${pageContext.request.contextPath}/encheres">Enchères</a>
						      </li>
						      
						       <li class="nav-item active">
						        <a class="nav-link btn btn-primary me-2" href= "${pageContext.request.contextPath}/NewSaleServlet">Vendre un article</a>
						      </li>
						      
						       <li class="nav-item active">
						        <a class="nav-link btn btn-primary me-2" href= "${pageContext.request.contextPath}/monprofil">Mon profil</a>
						      </li>
						      
						      <li class="nav-item active">
						        <a class="nav-link btn btn-primary me-2" href= "${pageContext.request.contextPath}/deconnexion">Déconnexion</a>
						      </li>
						     
						    					     
						    </ul>
						  </div>
						</nav>
					
		       	  </div>
	       	  
	       	  </c:otherwise>
       	  </c:choose>
       	</div>
    </nav>
