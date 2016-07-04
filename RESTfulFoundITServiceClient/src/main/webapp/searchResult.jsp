<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Search result</title>
<jsp:include page="head.jsp" />

</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="container" ng-app="result" ng-controller="resultCtrl" style="margin-bottom:3%;margin-top:2%;min-height: 800px;">
	<div id="noresult" align="center" style="font-size:30px;"></div>
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
				<!-- search result -->

				<!-- search result -->
				<!-- <p>{{data}}</p> 
				<h4 ng-show="count>0">Total Result: {{count}}</h4>
				<p>{{noresult}}</p> -->
				<!-- <p>{{responseMSG}}</p> -->
				<!-- result table -->
				<div class="table-responsive">
					<table class="table table-striped">
						<col width="15%">
						<col width="30%">
						<col width="15%">

						<tr ng-repeat="book in books">
							<td><h5>Job Description:</h5>{{book.description}}</td>
							<td><h5>Company Name:</h5> <a href="companyprofile.jsp?companyID={{book.companyID}}">{{book.companyName}}</a></td>
							<td><h5>Salary Rate:</h5>{{book.salaryRate}}</td>
							<td><h5>Position Type:</h5>{{book.positionType}}</td>
							<td><h5>Location:</h5>{{book.location}}</td>
							
							<td><h5>status:</h5>{{book.status}} </td>
							<td><a class="btn btn-default" href="apply.jsp?jobID={{book.jobID}}&status={{book.status}}"> Apply </a></td>
				
							
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
					$scope.data = {
							companyName : "<%=request.getParameter("companyName")%>",
							salaryRate1 : "<%=request.getParameter("salaryRate1")%>",
							salaryRate2 : "<%=request.getParameter("salaryRate2")%>",
							positionType : "<%=request.getParameter("positionType")%>",
							location : "<%=request.getParameter("location")%>",
							description : "<%=request.getParameter("description")%>",
							status : "<%=request.getParameter("status")%>"
					};


					$scope.queryData = function() {
						$http.post('./SearchController?action=search',
								$scope.data).success(function(response) {
							$scope.books = response;
						}).error(function(response) {
							$scope.books = response;
						});
					};
					
					$scope.checkstatus = function(status) {
						console.log(status);
						if(status=="open")
							$scope.responseMSG=true;
						else
							$scope.responseMSG=false;
					};

					$scope.queryData();

				});
	</script>
</body>
</html>