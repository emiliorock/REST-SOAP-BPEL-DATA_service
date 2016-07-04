<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="head.jsp" />
<title>Manage Company Profile</title>
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.CompanyProfile"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	CompanyProfile currentuser = (CompanyProfile) session.getAttribute("companyProfileSession");
	// check user type
	if (userBean == null) {
		String site = new String("login.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		userBean = new User();
	}
%>
</head>
<body>
	<jsp:include page="header.jsp" />

	<div class="container" style="margin-top: 3%">
		<div ng-app="myApp" ng-controller="formCtrl">
			<div class="row">
				<div class="col-lg-9 col-md-9 col-lg-offset-2 col-md-offset-2">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1>
								<small>Update Company Profile</small>
									<button class="btn btn-default" ng-click="deletepro()"
									type="submit" data-toggle="modal" data-target="#myModal2">
									Delete Profile</button>
							</h1>
							
							<div id="myModal2" class="modal fade" role="dialog">
								<div class="modal-dialog">

									<!-- Modal content-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>

											<h4 class="modal-title">Success</h4>
										</div>
										<div class="modal-body">
											<p>Your Profile is Deleted</p>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Close</button>
										</div>
									</div>
								</div>
							</div>
							
						</div>
						<div class="panel-body">

							<form name="myForm" role="form" class="form-horizontal">
								<div class="form-group">
									<div class="control-label col-sm-offset-1 col-sm-3">
										<img src="{{user.img}}" height="100px" width="100px">
									</div>
									<div class="col-sm-5">
										<label for="img" style="margin-top: 6%">Profile
											Photo(url):</label> <input type="text" name="img" id="img"
											ng-model="user.img" class="form-control"
											placeholder="Your img url">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="name">Company Name:</label>
									<div class="col-sm-5">
										<input type="text" name="name" id="name"
											ng-model="user.name" class="form-control"
											placeholder="Enter Company Name" >
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="intro">About Us:</label>
									<div class="col-sm-5">
										<input type="text" name="intro" id="intro"
											ng-model="user.intro" class="form-control"
											placeholder="About Company" >
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="url">Company Link:</label>
									<div class="col-sm-5">
										<input type="text" name="url" id="url"
											ng-model="user.url" class="form-control"
											placeholder="A link to your company website">
									</div>
								</div>

							
								<div class="form-group">
									<div class="col-sm-offset-5">
										<button class="btn" ng-click="reset()">Reset</button>
										<button class="btn" ng-click="updatepro()" type="submit">submit</button>

										<!-- Modal -->
										<div id="myModal" class="modal fade" role="dialog">
											<div class="modal-dialog">

												<!-- Modal content-->
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>

														<h4 class="modal-title">Success</h4>
													</div>
													<div class="modal-body">
														<p>Company profile has been successfully created!</p>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">Close</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>

							</form>
							<!--  user info
							<p>form = {{user}}</p>
							{{responseMSG}}</p>-->
						</div>
					</div>
				</div>
			</div>
		</div>


		<script>
			var app = angular.module('myApp', []);
			app.controller('formCtrl', function($scope, $http) {
				$scope.master = {		
					"companyID" : "<%=currentuser.getCompanyID()%>",
					"managerID" : "<%=userBean.getUserID()%>",
					"name" : "<%=currentuser.getName()%>",
					"img" : "<%=currentuser.getImg()%>",
					"url": "<%=currentuser.getUrl()%>",
					"intro" : "<%=currentuser.getIntro()%>"
					
								};

								$scope.reset = function() {
									$scope.user = angular.copy($scope.master);
								};
								$scope.reset();
								
								$scope.deletepro = function() {
									$http
											.post(
													'./CompanyController?action=deletepro',
													$scope.user)
											.success(
													function(response) {
														$scope.responseMSG = response;
													})
											.error(function(response) {
												$scope.responseMSG = response;
											});
								};

								$scope.updatepro = function() {
									$http
											.post(
													'./CompanyController?action=updatepro',
													$scope.user)
											.success(
													function(response) {
														$scope.responseMSG = response;
														if (response == "true") {
															$('#myModal')
																	.modal(
																			'show')
														} 
													})
											.error(function(response) {
												$scope.responseMSG = response;
											});
								};
							});
		</script>
	</div>

</body>
</html>