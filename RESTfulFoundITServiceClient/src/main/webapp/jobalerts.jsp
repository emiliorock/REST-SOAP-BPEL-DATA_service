<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Job Feed</title>
<jsp:include page="head.jsp" />
<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.CompanyProfile"%>
<%
	User userBean = (User) session.getAttribute("userSession");
	CompanyProfile cp = (CompanyProfile) session.getAttribute("companyProfileSession");
	String email = userBean.getEmail();
%>

</head>
<body>

	<jsp:include page="header.jsp" />
	<div align="center" style="margin-top: 5%">
		<img alt="logo" src="img/2.png" style="height: 120px">
	</div>

	<div class="container" ng-app="searchApp" ng-controller="searchCtrl">
		<div class="row">
			<div class="col-lg-6 col-md-6 col-lg-offset-1 col-md-offset-1">
				<!-- basic search -->
				<div class="row" style="margin-top: 20px;">
					<!-- left form -->
					<div
						class="form-group col-xs-6 col-lg-6 col-md-6 col-xs-offset-6 col-lg-offset-6 col-md-offset-6"
						style="margin-top: 5px;">
						<form method="POST" action="MailServlet">
						<label>Keyword</label> <input name="keyword" type="text"
							class="form-control" placeholder="Keyword"> 
							<div style="margin-top: 5px;">
							<label>Sort By</label> <select name="sort_by" class="form-control">
								<option>title</option>
								<option>description</option>
							</select>
							</div>
							<p></p>
							<input type="hidden" name="email" value="<%=email%>">
						<button class="btn btn-primary" type="submit">
						<span class="glyphicon glyphicon-search"></span> Get Feed
					</button>
					</form>
					</div>
	
				</div>
			</div>
		</div>
		</div>

</body>
</html>