<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Apply A Job</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.UserProfile"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	UserProfile up = (UserProfile) session.getAttribute("userProfileSession");
	// check user type
	if (userBean!=null&&!userBean.getUserType().equals("user")) {
		String site = new String("login.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		userBean = new User();
	}
	if (userBean==null) {
		String site = new String("login.jsp");
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		userBean = new User();
	}
%>


</head>
<body>
	<jsp:include page="header.jsp" />
	<%
	if(request.getParameter("jobID")==null){
		out.write("<center><br/><h3> Please select a job first! </h3></center>");
	}else if (request.getParameter("status")!=null&&!request.getParameter("status").equals("open")) {
		
		out.write("<center><br/><h3> The job's status is not open, please apply another one! </h3></center>");
		
	}else{
	
	
	%>
	
	
	<div class="container">
		<div ng-app="myApp" ng-controller="formCtrl">
			<div class="row">
				<div class="col-lg-10 col-md-10 col-lg-offset-1 col-md-offset-1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1>
								<small>Create Application</small>
							</h1>
						</div>
						<div class="panel-body">

							<form name="myForm" role="form" class="form-horizontal">
								

								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-3"
										for="coverLetter">Cover Letter:</label>
									<div class="col-sm-5">
										<input type="coverLetter" name="coverLetter" id="coverLetter"
											ng-model="user.coverLetter" class="form-control"
											placeholder="Create a coverLetter">
									</div>
			
								</div>

								<div class="form-group">
									<div class="col-sm-offset-5">
										<button class="btn" ng-click="reset()" type="reset">Reset</button>
										<button class="btn" ng-click="createApp()" type="submit">submit</button>

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
														<p>You application is sent!</p>
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
								$scope.user = {
									"coverLetter" : "",
									"profileID" : "<%=up.getProfileID()%>",
									"jobID": <%=request.getParameter("jobID")%>,
									"appID" : ""
								};

								$scope.createApp = function() {
									$http
											.post(
													'./ApplicationController?action=createApp',
													$scope.user)
											.success(
													function(response) {
														if (response == "true") {
															$('#myModal')
																	.modal(
																			'show')
														} 

														$scope.responseMSG = response;
													})
											.error(function(response) {
												$scope.responseMSG = response;
											});
								};

							});
		</script>


	</div>

<% }%>

</body>
</html>