<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>FoundIT</title>
<jsp:include page="head.jsp" />




</head>
<body>
	<jsp:include page="header.jsp" />
	<div align="center" style="margin-top: 5%">
		<img alt="logo" src="img/2.png" style="height: 120px">
	</div>
	<div class="container" ng-app="searchApp" ng-controller="searchCtrl">
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
				<!-- basic search -->
				<div class="row" style="margin-top: 20px;">
					<!-- left form -->
					<div
						class="form-group col-xs-4 col-lg-4 col-md-4 col-xs-offset-2 col-lg-offset-2 col-md-offset-2"
						style="margin-top: 5px;">
						<label>Company Name</label> <input type="text"
							class="form-control" placeholder="Company Name"
							ng-model="data.companyName"> <label style="margin-top: 5px;">Job
							Position</label> <input type="text" class="form-control"
							placeholder="Position Type" ng-model="data.positionType">
						<label style="margin-top: 5px;">Location</label> <input
							type="text" class="form-control" placeholder="location"
							ng-model="data.location">

					</div>
					
					<div class="form-group col-xs-4 col-lg-4 col-md-4"
						style="margin-top: 5px;">
						<label>Job Description</label> <input type="text"
							class="form-control" placeholder="Job Description"
							ng-model="data.description">
						<div style="margin-top: 5px;">
							<label>Status</label> <select class="form-control" id="status"
								ng-model="data.status">
								<option>open</option>
								<option>processed</option>
								<option>in-review</option>
								<option>sent-invitations</option>
								<option>close</option>
							</select>



						</div>


						<!-- year month row -->
						<div class="row">
							<!-- year half size -->
							<div class="col-xs-12 col-lg-12 col-md-12"
								style="margin-top: 5px;">
								<label>Salary Rate</label>
								<div class="row">

									<div class="col-xs-5 col-lg-5 col-md-5">
										<input type="number" class="form-control" id="salaryRate1"
											ng-model="data.salaryRate1">
									</div>
									<div class="col-xs-2 col-lg-2 col-md-2">to</div>
									<div class="col-xs-5 col-lg-5 col-md-5">
										<input type="number" class="form-control" id="salaryRate2"
											ng-model="data.salaryRate2">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div align="center" style="margin-top: 20px">
					<button class="btn btn-primary" ng-click="search()"
						type="button">
						<span class="glyphicon glyphicon-search"></span> Search
					</button>
				</div>

			</div>
			<!-- basic search 
			
			<p>{{page}}</p> 
			<p>{{data}}</p>-->
		</div>
	</div>

	<script>
		var app = angular.module('searchApp', []);
		app.controller('searchCtrl',
				function($scope, $http, $window, $location) {
					$scope.responseMSG = "";
					$scope.data = {
						companyName : "",
						salaryRate1 : "",
						salaryRate2 : "",
						positionType : "",
						location : "",
						description : "",
						status : ""

					};
					
					$scope.search = function($location) {
						var url = "searchResult.jsp?companyName="
								+ $scope.data.companyName + "&salaryRate1="
								+ $scope.data.salaryRate1 + "&salaryRate2="
								+ $scope.data.salaryRate2 + "&positionType="
								+ $scope.data.positionType + "&location="
								+ $scope.data.location + "&description="
								+ $scope.data.description + "&status="
								+ $scope.data.status;
						console.log(url);
						$window.location.href = url;
					}
				});
	</script>
</body>
</html>