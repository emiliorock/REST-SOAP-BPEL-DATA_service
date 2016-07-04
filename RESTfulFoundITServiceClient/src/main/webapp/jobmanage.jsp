<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Stock Management</title>
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
	<div class="container" style="margin-top:3%">
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1" ng-app="jobmanage" ng-controller="jobmanageCtrl">
				<div class="panel panel-default">
					<div class="panel-heading">Jobs</div>
					<div class="panel-body">

						<!-- add book form -->
						<!-- Button trigger modal -->
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">+Job</button>

						<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Job detail</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										<div class="form-group">
												<label>Company Name</label><h4><%=cp.getName()%></h4>
											</div>
											<div class="form-group">
												<label>Job Description</label><input class="form-control" type="text" ng-model="book.description">
											</div>
											
											
											<div class="form-group">
												<label>Salary Rate</label><input class="form-control" type="text" ng-model="book.salaryRate">
											</div>
											<div class="form-group">
												<label>Position Type</label><input class="form-control" type="text" ng-model="book.positionType">
											</div>
											<div class="form-group">
												<label>Location</label><input class="form-control" type="text" ng-model="book.location">
											</div>
											
											<div class="form-group">
												<label>Status</label>
												 <select class="form-control" id="status" ng-model="book.status">
													<option selected>open</option>
													<option>processed</option>
													<option>in-review</option>
													<option>sent-invitations</option>
													<option>close</option>
												</select>
											</div>
											
										</form>
										<!-- <p style="color:red">{{addJobResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="addJob()">Add Job</button>
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
									<td><button class="btn btn-default" ng-click="deleteJob(book)">-</button></td>
									<td><h5>JobID:</h5>{{book.jobID}}</td>
									<td><h5>Job Description:</h5>{{book.description}}</td>
									<td><h5>Company Name:</h5>{{book.companyName}}</td>
									<td><h5>Salary Rate</h5>{{book.salaryRate}}</td>
									<td><h5>Position Type</h5>{{book.positionType}}</td>
									<td><h5>Location:</h5>{{book.location}}</td>
									<td><h5>Status:</h5>{{book.status}}</td>
									
									
									
									<td><button class="btn btn-default" data-toggle="modal" ng-click="setToUpd(book)" data-target="#myModal2" >Update</button>
									<a class="btn btn-default" href="applications.jsp?jobID={{book.jobID}}&description={{book.description}}">View Applications</a>
									
									</td>
									
						<!-- Modal -->
						<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">Job detail</h4>
									</div>
									<div class="modal-body">
										<form novalidate>
										<div class="form-group">
										
												<label>Company Name</label><h4>{{job.companyName}}</h4>
											</div>
											<div class="form-group">
												<label>Job Description</label><input class="form-control" type="text" ng-model="job.description" value>
											</div>
											
											
											<div class="form-group">
												<label>Salary Rate</label><input class="form-control" type="text" ng-model="job.salaryRate">
											</div>
											<div class="form-group">
												<label>Position Type</label><input class="form-control" type="text" ng-model="job.positionType">
											</div>
											<div class="form-group">
												<label>Location</label><input class="form-control" type="text" ng-model="job.location">
											</div>
											
											<div class="form-group">
												<label>Status</label>
												 <select class="form-control" id="status" ng-model="job.status">
												
													<option>processed</option>
													<option>in-review</option>
													<option>sent-invitations</option>
													<option>close</option>
												</select>
											</div>
										</form>
																				
												
										
										<!-- <p style="color:red">{{addJobResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="updateJob(job)">Update Job</button>
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
		var app = angular.module('jobmanage', []);
		app.controller('jobmanageCtrl', function($scope, $http) {
			$scope.books = [];
			$scope.job =[];
			$scope.responseMSG = "";
			$scope.data={
					companyID : "<%=cp.getCompanyID()%>"
					};
			$scope.getJobs = function() {
				$http.post('./ManagerController?action=getJobs',
						$scope.data).success(function(response) {
					$scope.books = response;
				}).error(function(response) {
					$scope.books = response;
				});
			};
			$scope.setToUpd = function(book) {
				console.log(book);
				$scope.job = angular.copy(book);
			};
			$scope.book = {
				companyName : "<%=cp.getName()%>",
				description : "",
				salaryRate : "",
				positionType : "",
				location : "",
				status : "",
				companyID: "<%=cp.getCompanyID()%>",
				jobID: ""
			};
			
			$scope.addJob = function() {
				$http.post('./ManagerController?action=addJob', $scope.book)
						.success(function(response) {
							$scope.addJobResponse = response;
							$scope.getJobs();
						}).error(function(response) {
							$scope.addJobResponse = response;
						});
			};


			$scope.deleteJob = function(book) {
				console.log(book);
				$http.post('./ManagerController?action=deleteJob', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getJobs();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}
			
			$scope.updateJob = function(book) {
				console.log(book);
				$http.post('./ManagerController?action=updateJob', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getJobs();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}

			$scope.getJobs();
		});
	</script>
</body>
</html>