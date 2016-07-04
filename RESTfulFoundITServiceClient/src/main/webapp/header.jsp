<%@page import="au.edu.unsw.soacourse.user.User"%>
<%@page import="au.edu.unsw.soacourse.beans.UserProfile"%>
<%@page import="au.edu.unsw.soacourse.beans.CompanyProfile"%>
<%@page import="au.edu.unsw.soacourse.beans.Reviewer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-inverse" style="background-color: #383838">
	<div class="container-fluid">

		<div>
			<ul class="nav navbar-nav" style="margin-left: 6%">
				<li><a href="./"
					style="font-size: 25px; color: #FEFCFF; margin-top: 2%">FoundIT</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right" style="margin-right: 4%">
				<%
					User user = (User) session.getAttribute("userSession");
					UserProfile up = (UserProfile) session.getAttribute("userProfileSession");
					Reviewer rw = (Reviewer) session.getAttribute("reviewerSession");
					CompanyProfile cp = (CompanyProfile) session.getAttribute("companyProfileSession");			
					
					// check user type
					if (user != null) {
						if (user.getUserType().equals("user")) { // if normal user
							out.write(
									"<li style='margin-top:5px'><a href='./jobalerts.jsp'><span class='glyphicon glyphicon-envelope'></span> Get Job Alerts</a></li>");
							if (up == null) {
								out.write(
										"<li style='margin-top:5px'><a href='./createprofile.jsp'><span class='glyphicon glyphicon-edit'></span> Create Profile</a></li>");
							} else {
								out.write(
										"<li style='margin-top:5px'><a href='./appsmanage.jsp'><span class='glyphicon glyphicon-book'></span> Manage Applications</a></li>");
								out.write(
										"<li style='margin-top:5px'><a href='./updateprofile.jsp'><span class='glyphicon glyphicon-edit'></span> Update Profile</a></li>");
							}
							out.write(
									"<li style='margin-top:5px'><a href='./LoginController?action=logout'><span class='glyphicon glyphicon-log-out'></span> Log out</a></li>");
						} else if (user.getUserType().equals("manager")) { // if seller
							if(cp!=null){
								out.write(
										"<li style='margin-top:5px'><a href='./reviewermanage.jsp'><span class='glyphicon glyphicon-paperclip'></span> Manage Hiring Team</a></li>");
								out.write(
									"<li style='margin-top:5px'><a href='./jobmanage.jsp'><span class='glyphicon glyphicon-book'></span> Manage Jobs</a></li>");}

						if (cp == null) {
								out.write(
										"<li style='margin-top:5px'><a href='./createcp.jsp'><span class='glyphicon glyphicon-edit'></span> Create Compnay Profile</a></li>");
							} else {
								out.write(
										"<li style='margin-top:5px'><a href='./updatecp.jsp'><span class='glyphicon glyphicon-edit'></span> Update Company Profile</a></li>");
							}
							out.write(
									"<li style='margin-top:5px'><a href='./LoginController?action=logout'><span class='glyphicon glyphicon-log-out'></span> Log out</a></li>");
						}
					} else if(rw != null){
						out.write(
								"<li style='margin-top:5px'><a href='./review.jsp'><span class='glyphicon glyphicon glyphicon-user'></span> Manage Reviews</a></li>");
						out.write(
								"<li style='margin-top:5px'><a href='./ReviewerController?action=logout'><span class='glyphicon glyphicon-log-out'></span> Log out</a></li>");
					}
					else{
						
						out.write(
								"<li style='margin-top:5px'><a href='./reviewer.jsp'><span class='glyphicon glyphicon-log-in'></span> Reviewer Log in</a></li>");
						out.write(
								"<li style='margin-top:5px'><a href='./login.jsp'><span class='glyphicon glyphicon-log-in'></span> Log in</a></li>");
						out.write(
								"<li style='margin-top:5px'><a href='./signup.jsp'><span class='glyphicon glyphicon-user'></span> Sign up</a></li>");
					}
				%>
			</ul>
		</div>
	</div>

</nav>