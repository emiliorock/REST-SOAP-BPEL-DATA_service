<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Applications</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.beans.Reviewer"%>
<%@page import="au.edu.unsw.soacourse.beans.Review"%>
<%
	Reviewer rw = (Reviewer) session.getAttribute("reviewerSession");
	if (rw == null) {
		String site = new String("reviewer.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		rw = new Reviewer();
	}
%>
</head>
<body>
	<jsp:include page="header.jsp" />


	<div class="container" ng-app="result" ng-controller="resultCtrl"
		style="margin-bottom: 3%; margin-top: 2%; min-height: 800px;">
		<div id="noresult" align="center" style="font-size: 30px;"></div>
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
				<!-- search result -->

				<!-- search result -->
				<!-- <p>{{data}}</p> -->
				<!-- <p>{{responseMSG}}</p> -->
				<!-- result table -->

				<div class="table-responsive">
					<table class="table table-striped">
						<col width="15%">
						<col width="30%">
						<col width="15%">
						<tr ng-repeat="book in books">
							<td><h5>reviewID:</h5>{{book.reviewID}}</td>
							<td><h5>AppID:</h5><a href="./review_app.jsp?appID={{book.appID}}">{{book.appID}}</a></td>
						</tr>

					</table>
				</div>

				<!-- result table -->
			</div>
		</div>
	</div>


	<script>
		var app = angular.module('result', []);
		app.controller('resultCtrl',
				function($scope, $http, $window, $location) {
					$scope.responseMSG = "";

					$scope.queryData = function() {
						$http.post('./ReviewController?action=getReview')
								.success(function(response) {
									$scope.books = response;
								}).error(function(response) {
									$scope.books = response;
								});
					};

					$scope.review = [];
					$scope.setToUpd = function(book) {
						console.log(book);
						$scope.review = angular.copy(book);
					};

					$scope.queryData();

				});
	</script>


</body>
</html>