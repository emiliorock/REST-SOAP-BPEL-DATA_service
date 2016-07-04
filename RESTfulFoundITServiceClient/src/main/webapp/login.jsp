<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Login</title>

<jsp:include page="head.jsp" />


</head>
<body>
	<jsp:include page="header.jsp" />


	<div class="container" ng-app="login" ng-controller="customersCtrl">
		<div class="wrapper">
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<form class="login active">
						<h3 style="margin-top: 0">Login</h3>
						<div>
							<label>Email:</label> <input type="text" class="form-control"
								ng-model="login.email" required> <span class="error">This
								is an error</span>
						</div>
						<div>
							<label>Password: </label> <input type="password"
								class="form-control" ng-model="login.password" required> <span
								class="error">This is an error</span>
						</div>
						<!-- <p>{{login}}<p>-->
						<div class="bottom" style="margin-bottom: 0">
							<input type="submit" value="Login" class="btn btn-default"
								ng-click="submit()"></input> <a href="signup.jsp"
								class="linkform">You don't have an account yet? Register
								here</a>
							<div id="myModal" class="modal fade" role="dialog">
								<div class="modal-dialog">

									<!-- Modal content-->
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>

											<h4 class="modal-title" style="color:#111">Invalid email or password</h4>
										</div>
										<div class="modal-body" style="color:#111">
											<p>{{responseMSG}}<p>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">Close</button>
										</div>
									</div>

								</div>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>

	</div>



	<script>
		var app = angular.module('login', []);
		app.controller('customersCtrl', function($scope, $window, $http) {
			$scope.login = {
				email : "",
				password : ""
			};
			$scope.responseMSG = "";
			$scope.submit = function() {
				$http.post('./LoginController?action=login', $scope.login)
						.success(function(response) {
							$scope.responseMSG = response;
							console.log($scope.responseMSG);
							if (response === 'login succeed') {
								$window.location.href = './index.jsp'
							}else
								$('#myModal').modal('show')
							;
						}).error(function(response) {
							$scope.responseMSG = response;
						});
			};
		});
	</script>

</body>
</html>