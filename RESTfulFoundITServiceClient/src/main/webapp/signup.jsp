<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="head.jsp" />
<title>Sign Up</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<br />
	<div class="container">
		<div ng-app="myApp" ng-controller="formCtrl">
			<div class="row">
				<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1>
								<small>Sign Up</small>
							</h1>
						</div>
						<div class="panel-body">

							<form name="myForm" role="form" class="form-horizontal">
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="email">E-mail:</label>
									<div class="col-sm-5">
										<input type="email" name="email" id="email"
											ng-model="user.email" class="form-control"
											placeholder="Enter E-mail Address" ng-change="checkeml()"
											ng-model-options='{ debounce: 1000 }' required>
									</div>
									<div class="col-sm-3" id="checkeml" style="color: red">
										<span class="error" ng-show="myForm.email.$error.email">
											Invalid Email Address!</span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="password">Password:</label>
									<div class="col-sm-5">
										<input type="password" name="password" id="password"
											ng-model="user.password" class="form-control"
											placeholder="Create a password"
											ng-model-options='{ debounce: 1000 }'
											ng-pattern="/^.{4,12}$/" required ng-trim="false">
									</div>
									<div class="col-sm-3" style="color: red">
										<span class="error" ng-show="myForm.password.$error.pattern">
											Should be 4-12 characters!</span>
									</div>
								</div>



								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="usertype">User Type:</label>
									<div class="col-sm-5">
										<select class="form-control" name="userType" id="userType"
											ng-model="user.userType">
											<option value="user">Employee</option>
											<option value="manger">Employer</option>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-sm-offset-5">
										<button class="btn" ng-click="reset()" type="reset">Reset</button>
										<button class="btn" ng-click="checknew()" type="submit">submit</button>

										<!-- Modal -->
										<div id="myModal" class="modal fade" role="dialog">
											<div class="modal-dialog">

												<!-- Modal content-->
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>

														<h4 class="modal-title">Congratulations!</h4>
													</div>
													<div class="modal-body">
														<p>Congratulations! You have successfully signed up!</p>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">Close</button>
													</div>
												</div>

											</div>
										</div>

										<div id="myModal2" class="modal fade" role="dialog">
											<div class="modal-dialog">

												<!-- Modal content-->
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>

														<h4 class="modal-title">Failed to Create Account!</h4>
													</div>
													<div class="modal-body">
														<p>Please re-fill the sign up form follow the instructions!</p>
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
							<!--  
							<p>form = {{user}}</p>
							<p>{{responseMSG}}</p>-->
						</div>
					</div>
				</div>
			</div>
		</div>


		<script>
			var app = angular.module('myApp', []);
			app
					.controller(
							'formCtrl',
							function($scope, $http) {
								$scope.master = {
									"email" : "",
									"password" : "",
									"userType" : "user",
								};

								$scope.reset = function() {
									$scope.user = angular.copy($scope.master);
								};

								$scope.checkeml = function() {
									$http
											.post(
													'./RegisterController?action=checkeml',
													$scope.user)
											.success(
													function(response) {
														document
																.getElementById("checkeml").innerHTML = response;
														if (response == "true") {
															document
																	.getElementById("checkeml").innerHTML = "Email Exists!";
														} else {
															document
																	.getElementById("checkeml").innerHTML = "";
														}
													}).error(
													function(response) {
													});
								};

								$scope.checknew = function() {
									$http
											.post(
													'./RegisterController?action=checknew',
													$scope.user)
											.success(
													function(response) {
														if (response == "true") {
															$('#myModal')
																	.modal(
																			'show')
														} else
															$('#myModal2')
																	.modal(
																			'show')

														$scope.responseMSG = response;
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