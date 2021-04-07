<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>

<head>

<%@include file="template/head.jsp"%>

</head>

<body>

	<%@include file="template/nav.jsp"%>


	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading -->
		<h1 class="my-4">Connection</h1>

		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-6 portfolio-item">
				<div class="card h-100 alert">
					<div class="card-body alert alert-danger">
						<h4 class="card-title">Information sur les erreurs</h4>
						<p class="card-text"><%=exception.getClass() %></p>
						<p class="card-text"><%=exception.getMessage() %></p>
					</div>					
				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; ENI Ecole
				2020</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->
	<%@include file="template/script.jsp"%>


</body>

</html>
