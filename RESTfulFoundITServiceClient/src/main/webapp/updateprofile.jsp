<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="head.jsp" />
<title>Manage User Profile</title>
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.UserProfile"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	UserProfile currentuser = (UserProfile) session.getAttribute("userProfileSession");
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
								<small>Update Profile </small>
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
									<label class="control-label col-sm-offset-1 col-sm-3">Email:</label>
									<label class="control-label col-sm-2"> <%out.print(userBean.getEmail()); %>
									</label>

								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="firstname">First Name:</label>
									<div class="col-sm-5">
										<input type="text" name="firstname" id="firstname"
											ng-model="user.firstname" class="form-control"
											placeholder="Enter First Name">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="lastname">Last Name:</label>
									<div class="col-sm-5">
										<input type="text" name="lastname" id="lastname"
											ng-model="user.lastname" class="form-control"
											placeholder="Enter Last Name">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="phone">Mobile:</label>
									<div class="col-sm-5">
										<input type="text" name="phone" id="phone"
											ng-model="user.phone" class="form-control"
											placeholder="Enter Your Full Mobile No.">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="driverLicense">Driver License:</label>
									<div class="col-sm-5">
										<input type="text" name="driverLicense" id="driverLicense"
											ng-model="user.driverLicense" class="form-control"
											placeholder="DriverLicense No.">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="address">Address:</label>
									<div class="col-sm-5">
										<input type="text" name="address" id="address"
											ng-model="user.address" class="form-control"
											placeholder="Enter Your Full Address">
									</div>
								</div>


								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="professionalSkills">Professional Skills:</label>
									<div class="col-sm-5">
										<input type="text" name="professionalSkills"
											id="professionalSkills" ng-model="user.professionalSkills"
											class="form-control"
											placeholder="Your Full Professional Skills?">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="experience">Experience:</label>
									<div class="col-sm-5">
										<input type="text" name="experience" id="experience"
											ng-model="user.experience" class="form-control"
											placeholder="Your experience">
									</div>
								</div>



								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3">User
										Type:</label> <label class="control-label col-sm-2"> <%
 								String type = userBean.getUserType();
 								if (type.equals("user"))
 									out.print("Employee");
 								else if (type.equals("manager"))
 									out.print("Employer"); %>
									</label>
								</div>

								<div class="form-group">
									<div class="col-sm-offset-5">
										<button class="btn" ng-click="reset()">Reset</button>
										<button class="btn" ng-click="checkupd()" type="submit">submit</button>

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
														<p>Your information has been successfully updated!</p>
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
							<p>{{responseMSG}}</p>-->
						</div>
					</div>
				</div>
			</div>
		</div>


		<script>
			var app = angular.module('myApp', []);
			app.controller('formCtrl', function($scope, $http) {
				$scope.master = {		
					"profileID" : "<%=currentuser.getProfileID()%>",
					"userID" : "<%=currentuser.getUserID()%>",
					"firstname" : "<%=currentuser.getFirstname()%>",
					"lastname" : "<%=currentuser.getLastname()%>",
					"email" : "<%=currentuser.getEmail()%>",
					"phone" : "<%=currentuser.getPhone()%>",
					"driverLicense" : "<%=currentuser.getDriverLicense()%>",
					"address" : "<%=currentuser.getAddress()%>",
					"professionalSkills" : "<%=currentuser.getProfessionalSkills()%>",
					"experience": "<%=currentuser.getExperience()%>",
					"img" : "<%=currentuser.getImg()%>"
								};

								$scope.reset = function() {
									$scope.user = angular.copy($scope.master);
								};
								$scope.reset();
								
								$scope.deletepro = function() {
									$http
											.post(
													'./RegisterController?action=deletepro',
													$scope.user)
											.success(
													function(response) {
														$scope.responseMSG = response;
																						})
											.error(function(response) {
												$scope.responseMSG = response;
											});
								};
								
								
								$scope.checkupd = function() {
									$http
											.post(
													'./RegisterController?action=checkupd',
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