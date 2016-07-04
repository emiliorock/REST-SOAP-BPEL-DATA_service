<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Applications</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.UserProfile"%>
<% 
User userBean = (User) session.getAttribute("userSession");
UserProfile up = (UserProfile) session.getAttribute("userProfileSession");
if (userBean!=null&&!userBean.getUserType().equals("user")) {
	String site = new String("login.jsp");
	response.setStatus(response.SC_MOVED_TEMPORARILY);
	response.setHeader("Location", site);
	userBean = new User();
}
%>


</head>
<body>


	<jsp:include page="header.jsp" />
	<div class="container" style="margin-top:3%">
		<div class="row">
			<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1" ng-app="Appmanage" ng-controller="AppmanageCtrl">
				<div class="panel panel-default">
					<div class="panel-heading">Applications</div>
					<div class="panel-body">

						<!-- add book form -->
						<!-- Button trigger modal -->
						<!-- stock table -->
						<div class="table-responsive" style="margin-top:10px">
							<table class="table table-striped">
								<col width="50">
								<col width="15%">
								<col width="20%">
								<tr ng-repeat="book in books">
			
									<td><button class="btn btn-default" ng-click="deleteApp(book)">-</button></td>
									<td><h5>AppID:</h5>{{book.appID}}</td>
									<td><h5>JobID:</h5>{{book.jobID}}</td>
									<td><h5>Cover Letter:</h5>{{book.coverLetter}}</td>
									<td><h5>Status:</h5>{{book.status}}</td>
									
									
									
									<td><button class="btn btn-default" data-toggle="modal" ng-click="setToUpd(book)" data-target="#myModal2" >Update</button>					
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
												<label>Cover Letter</label><input class="form-control" type="text" ng-model="app.coverLetter">					
											</div>
									<div class="form-group">
												<label>Current Status</label>
												<select class="form-control" id="status" ng-model="app.status" >
													<option >accept</option>
													<option >reject</option>
												</select>											
											</div>
										</form>
																				
												
										
										<!-- <p style="color:red">{{addJobResponse}}</p>  -->
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
										<button type="button" class="btn btn-primary" ng-click="updateApp(app)">Update Application</button>
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
		var app = angular.module('Appmanage', []);
		app.controller('AppmanageCtrl', function($scope, $http) {
			$scope.books = [];
			$scope.app =[];
			$scope.responseMSG = "";
			$scope.data={
					profileID : "<%=up.getProfileID()%>"
					};
			$scope.getApps = function() {
				$http.post('./ApplicationController?action=getApps',
						$scope.data).success(function(response) {
					$scope.books = response;
				}).error(function(response) {
					$scope.books = response;
				});
			};
			$scope.setToUpd = function(book) {
				console.log(book);
				$scope.app = angular.copy(book);
			};
			
			$scope.book = {
					jobID : "",
					appID : "",
					profileID : "<%=up.getProfileID()%>",
					coverLetter : "",
					status : ""
				
			};

			$scope.deleteApp = function(book) {
				console.log(book);
				$http.post('./ApplicationController?action=deleteApp', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getApps();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}
			
			$scope.updateApp = function(book) {
				console.log(book);
				$http.post('./ApplicationController?action=updateApp', book)
						.success(function(response) {
							$scope.responseMSG = response;
							$scope.getApps();
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			}

			$scope.getApps();
		});
	</script>


</body>
</html>