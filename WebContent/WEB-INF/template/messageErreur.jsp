<c:if test="${!empty errors}">
	<div class="portfolio-item">
		<div class="card alert">
			<div class="card-body alert alert-danger">
				<h4 class="card-title">Erreurs</h4>
				<c:forEach var="msg" items="${errors}">
					<p class="card-text">${msg}</p>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>