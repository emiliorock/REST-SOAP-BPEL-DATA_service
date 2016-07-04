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
						<tr>
							<td><h5>Applicant ProfileID:</h5> <a href="userprofile.jsp?profileID={{book.profileID}}">{{book.profileID}}</a></td>
							<td><h5>JobID:</h5>{{book.jobID}}</td>
							<td><h5>Application ID:</h5>{{book.appID}}</td>
							<td><h5>CoverLetter</h5>{{book.coverLetter}}</td>
							<td><h5>Status:</h5>{{book.status}}</td>
							<td>	<button class="btn btn-default" data-toggle="modal" ng-click="setToApp(book)" data-target="#myModal2" >Update Status</button>					
							</td>
									
						<!-- Modal -->
						<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Change Status</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										<div class="form-group">
												<label>Current Status</label>
												<select class="form-control" id="status" ng-model="app.status" >
													<option >shortlist</option>
													<option >non-shortlist</option>
												</select>											
											</div>
										</form>
																				
												
										
										<!-- <p style="color:red">{{addJobResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="update(app)">Update</button>
									</div>
								</div>
							</div>
						</div>

						
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
					$scope.app=[];
					$scope.book = {
							appID : "<%=request.getParameter("appID")%>"
					};

					$scope.queryData = function() {
						$http.post('./ApplicationController?action=getAppsByAppID', $scope.book)
								.success(function(response) {
									$scope.book = response;
								}).error(function(response) {
									$scope.books = response;
								});
					};

					$scope.update = function(app) {
						console.log(app);
						$http.post('./ApplicationController?action=updateApp', app)
								.success(function(response) {
									$scope.responseMSG = response;
									$scope.queryData();
								}).error(function(response) {
									$scope.responseMSG = response;
								});
					}
					
					$scope.setToApp = function(book) {
						console.log(book);
						$scope.app = angular.copy(book);
					};

					$scope.queryData();

				});
	</script>


</body>
</html>