<%@page import="au.edu.unsw.soacourse.beans.Reviewer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-inverse" style="background-color: #383838">
	<div class="container-fluid">

		<div>
			<ul class="nav navbar-nav" style="margin-left: 6%">
				<li><a href="./review.jsp"
					style="font-size: 25px; color: #FEFCFF; margin-top: 2%">FoundIT</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right" style="margin-right: 4%">
				<%
					Reviewer user = (Reviewer) session.getAttribute("reviewerSession");

					// check user type
					if (user != null) {
										
						out.write(
								"<li style='margin-top:5px'><a href='./review.jsp'><span class='glyphicon glyphicon glyphicon-user'></span> Manage Reviews</a></li>");
						out.write(
								"<li style='margin-top:5px'><a href='./ReviewerController?action=logout'><span class='glyphicon glyphicon-log-out'></span> Log out</a></li>");
					}else{
						out.write(
								"<li style='margin-top:5px'><a href='./reviewer.jsp'><span class='glyphicon glyphicon-log-in'></span> Log in</a></li>");
					}
				%>
			</ul>
		</div>
	</div>

</nav>