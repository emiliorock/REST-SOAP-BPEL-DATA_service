<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Applications</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.CompanyProfile"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	CompanyProfile cp = (CompanyProfile) session.getAttribute("companyProfileSession");
	// check user type
	if (userBean!=null&&!userBean.getUserType().equals("manager")) {
		String site = new String("login.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		userBean = new User();
	}
	if (cp==null) {
		String site = new String("login.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		cp = new CompanyProfile();
	}

%>
</head>
<body>
	<jsp:include page="header.jsp" />
	
	<div class="container" ng-app="result" ng-controller="resultCtrl" style="margin-bottom:3%;margin-top:2%;min-height: 800px;">
	<div id="noresult" align="center" style="font-size:30px;"></div>
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
				<!-- search result -->

				<!-- search result -->
				<!-- <p>{{data}}</p> -->
				<!-- <p>{{responseMSG}}</p> -->
				<!-- result table -->
				<div>
				<table>
				<tr >
				<td class="col-lg-6 col-md-6"><h3>Job ID: <%=request.getParameter("jobID") %></h3></td>
				<td class="col-lg-offset-2 col-md-offset-2"><h3>Description: <%=request.getParameter("description") %></h3></td>
				</tr>
				</table>
				</div>
				
				
				<div class="table-responsive">
					<table class="table table-striped">
						<col width="15%">
						<col width="30%">
						<col width="15%">
						<tr ng-repeat="book in books">
							<td><h5>Applicant ProfileID:</h5> <a href="userprofile.jsp?profileID={{book.profileID}}">{{book.profileID}}</a></td>
							<td><h5>Application ID:</h5>{{book.appID}}</td>
							<td><h5>CoverLetter</h5>{{book.coverLetter}}</td>
							<td><h5>Status:</h5>{{book.status}}</td>
							<td><button type="button" class="btn btn-default" ng-click="autoCheck(book)">Auto Check</button>
							<button class="btn btn-default" data-toggle="modal" ng-click="getReviewers();setToApp(book)" data-target="#myModal2" >Assign</button>					
							</td>
									
						<!-- Modal -->
						<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Application detail</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										<div class="form-group">

												<label>Reviewers</label>
												<select class="form-control" id="status" ng-model="rw.reviewer" >
													<option ng-repeat="reviewer in reviewers">{{reviewer.username}}</option>
												</select>											
											</div>
										</form>
																				
												
										
										<!-- <p style="color:red">{{addJobResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="createRw(rw)">Create Review</button>
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
					$scope.rw = {
							
							appID : "",
							reviewer : "",
						
					};

					
					$scope.data = {
							jobID : "<%=request.getParameter("jobID")%>"
					};


					$scope.queryData = function() {
						$http.post('./ApplicationController?action=getAppsByJobID',
								$scope.data).success(function(response) {
							$scope.books = response;
						}).error(function(response) {
							$scope.books = response;
						});
					};
					
					$scope.autoCheck = function(book) {
						console.log(book);
						$http.post('./ApplicationController?action=autocheck', book)
								.success(function(response) {
									$scope.responseMSG = response;
									$scope.queryData();
								}).error(function(response) {
									$scope.responseMSG = response;
								});
					}
					
					$scope.getReviewers = function() {
						$http.post('./ManagerController?action=getReviewers')
								.success(function(response) {
									$scope.reviewers = response;
								}).error(function(response) {
									$scope.responseMSG = response;
								});
					}
					
					$scope.setToApp = function(book) {
						console.log(book);
						$scope.rw.appID = book.appID;
					};
					
					$scope.createRw = function(rw) {
						console.log(rw);
						$http.post('./ReviewController?action=createRw', rw)
								.success(function(response) {
									$scope.responseMSG = response;
								}).error(function(response) {
									$scope.responseMSG = response;
								});
					}

					$scope.queryData();

				});
	</script>
	

</body>
</html>