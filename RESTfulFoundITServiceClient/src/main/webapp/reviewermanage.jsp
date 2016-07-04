<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hiring Team Management</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.CompanyProfile"%>
<%@page import="au.edu.unsw.soacourse.beans.Reviewer"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	CompanyProfile cp = (CompanyProfile) session.getAttribute("companyProfileSession");
	// check user type
	if (!userBean.getUserType().equals("manager")) {
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
	<div class="container" style="margin-top:3%">
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1" ng-app="reviewermanage" ng-controller="reviewermanageCtrl">
				<div class="panel panel-default">
					<div class="panel-heading">Hiring Team</div>
					<div class="panel-body">

						<!-- add book form -->
						<!-- Button trigger modal -->
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">+Reviewer</button>

						<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Reviewer detail</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										<div class="form-group">
												<label>Company Name</label><h4><%=cp.getName()%></h4>
											</div>
							
											<div class="form-group">
												<label>Username</label><input class="form-control" type="text" ng-model="reviewer.username">
											</div>
											<div class="form-group">
												<label>Password</label><input class="form-control" type="text" ng-model="reviewer.password">
											</div>
											<div class="form-group">
												<label>Professional Skills</label><input class="form-control" type="text" ng-model="reviewer.professionalSkills">
											</div>
											
											<div class="form-group">
												<label>Status</label>
												 <select class="form-control" id="status" ng-model="reviewer.status">
													<option selected>available</option>
													<option>not-available</option>
												</select>
											</div>
											
										</form>
										<!-- <p style="color:red">{{addReviewerResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="addReviewer(book)">Add Reviewer</button>
									</div>
								</div>
							</div>
						</div>
						<!-- stock table -->
						<div class="table-responsive" style="margin-top:10px">
							<table class="table table-striped">
								<col width="50">
								<col width="15%">
								<col width="20%">
								<tr ng-repeat="book in books">
									<td><button class="btn btn-default" ng-click="deleteReviewer(book)">-</button></td>
				
									<td><h5>Username:</h5>{{book.username}}<br>
									<td><h5>Password:</h5>{{book.password}}<br>
									<td><h5>Professional Skills:</h5>{{book.professionalSkills}}</td>
									<td><h5>Status:</h5>{{book.status}}</td>
									
									<td><button class="btn btn-default" data-toggle="modal" ng-click="setToUpd(book)" data-target="#myModal2" >Update</button></td>
										
									
						<!-- Modal -->
						<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Reviewer detail</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										
											
											
											<div class="form-group">
												<label>Username</label><input class="form-control" type="text" ng-model="reviewer.username">
											</div>
											<div class="form-group">
												<label>Password</label><input class="form-control" type="text" ng-model="reviewer.password">
											</div>
											<div class="form-group">
												<label>Professional Skills</label><input class="form-control" type="text" ng-model="reviewer.professionalSkills">
											</div>
											
											<div class="form-group">
												<label>Status</label>
												 <select class="form-control" id="status" ng-model="reviewer.status">
													<option selected>availabe</option>
													<option>not-available</option>
													
												</select>
											</div>
										</form>
																				
												
										
										<!-- <p style="color:red">{{addResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="updateReviewer(reviewer)" >Update Reviewer</button>
									</div>
								</div>
							</div>
						</div>
									
								</tr>

							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		var app = angular.module('reviewermanage', []);
		app.controller('reviewermanageCtrl', function($scope, $http) {
			$scope.books = [];
			$scope.reviewer = {
					companyName : "<%=cp.getName()%>",
					username : "",
					password : "",
					professionalSkills : "",
					status : "",
					reviewerID : "",
					companyID : "<%=cp.getCompanyID()%>"
			};
			$scope.responseMSG = "";
			$scope.data={
					companyID : "<%=cp.getCompanyID()%>"
					};
			$scope.getReviewers = function() {
				$http.post('./ManagerController?action=getReviewers',
						$scope.data).success(function(response) {
					$scope.books = response;
				}).error(function(response) {
					$scope.books = response;
				});
			};
			$scope.setToUpd = function(book) {
				console.log(book);
				$scope.reviewer = angular.copy(book);
			};
		

			$scope.addReviewer = function() {
				$http.post('./ManagerController?action=addReviewer', $scope.reviewer)
						.success(function(response) {
							$scope.addReviewerResponse = response;
							$scope.getReviewers();
						}).error(function(response) {
							$scope.addReviewerResponse = response;
						});
			};


			$scope.deleteReviewer = function(book) {
				console.log(book);
				$http.post('./ManagerController?action=deleteReviewer', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getReviewers();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}
			
			$scope.updateReviewer = function(book) {
				console.log(book);
				$http.post('./ManagerController?action=updateReviewer', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getReviewers();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}

			$scope.getReviewers();
		});
	</script>
</body>
</html>