<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Company Profile</title>
<jsp:include page="head.jsp" />

</head>
<body>
<jsp:include page="header.jsp" />

	<div class="container" ng-app="result" ng-controller="resultCtrl" style="margin-top:5%;">
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
				<!-- search result
				<p>{{data}}</p>
				<p>{{responseMSG}}</p>  -->

				<!-- result table -->
				
					<div class="row">
						<!-- left 4 for img -->
						<div class="col-lg-5 col-md-5 col-lg-offset-1 col-md-offset-1">
							<img class="img-responsive" src="{{book.img}}" style="width:275px"/>
						</div>
						<!-- right 8 for content -->
						<div class="col-lg-6 col-md-6">
							<h4>Company Name: {{book.name}}</h4>
							 <br/>
							<h4>Introduction: {{book.intro}}</h4>
							 <br/>	
							<h4>Url: <a href="{{book.url}}">{{book.url}}</a></h4>
					
						</div>
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
						companyID : "<%=request.getParameter("companyID")%>",
						companyName: "<%=request.getParameter("companyName")%>"
					};

					$scope.queryData = function() {
						$http.post('./CompanyController?action=getpro',
								$scope.data).success(function(response) {
							$scope.book = response;
							$scope.responseMSG = response;
						}).error(function(response) {
							$scope.responseMSG = response;
						});
					};
					
					
					$scope.queryData();
				});
	</script>


</body>
</html>