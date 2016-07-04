
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<style>
/* Sticky footer styles
-------------------------------------------------- */
html {
  position: relative;
  min-height: 100%;
}
body {
  /* Margin bottom by footer height */
  margin-bottom: 150px;
}
.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  /* Set the fixed height of the footer here */
  height: 150px;
  /*background-color: #f5f5f5;*/
}


/* Custom page CSS
-------------------------------------------------- */
/* Not required for template or sticky footer method. */

body > .container {
  /*padding: 60px 15px 0;*/
}
.container .text-muted {
  margin: 20px 0;
}

.footer > .container {
  padding-right: 15px;
  padding-left: 15px;
}

code {
  font-size: 80%;
}

a:link {
    text-decoration: none;
}

a:visited {
    text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:active {
    text-decoration: none;
}

h1{
	font-size:56px;
}
h2{
	font-size:20px;
	padding:0px 0px 40px 0px;
	color:#aaa;
}
h2 span{
	color:#ffa800;
}
a{
	color:#777;
}
a:hover{
	color:#222;
}
p{
	padding:5px 0px;
}
.wrapper{
	width:960px;
	margin:20px auto;
	font-family:"Trebuchet MS", "Myriad Pro", Arial, sans-serif;
	font-size:14px;
	color:#000;
}
.box{
	width:49%;
}
.left{
	float:left;
}
.right{
	float:right;
}

a.back{
	color:#777;
	position:fixed;
	top:5px;
	right:10px;
	text-decoration:none;
}
/* Form Style */
.form_wrapper{
	background:#fff;
	border:1px solid #ddd;
	margin:0 auto;
	width:350px;
	font-size:16px;
	-moz-box-shadow:1px 1px 7px #ccc;
	-webkit-box-shadow:1px 1px 7px #ccc;
	box-shadow:1px 1px 7px #ccc;
}
.form_wrapper h3{
	padding:20px 30px 20px 30px;
	background-color:#444;
	color:#fff;
	font-size:25px;
	border-bottom:1px solid #ddd;
}
.form_wrapper form{
	display:none;
	background:#fff;
}
.form_wrapper .column{
	width:47%;
	float:left;
}
form.active{
	display:block;
}
form.login{
	width:350px;
}

.form_wrapper a{
	text-decoration:none;
	color:#777;
	font-size:12px;
}
.form_wrapper a:hover{
	color:#000;
}
.form_wrapper label{
	display:block;
	padding:10px 30px 0px 30px;
	margin:10px 0px 0px 0px;
}
.form_wrapper input[type="text"],
.form_wrapper input[type="password"]{
	border: solid 1px #E5E5E5;
	background: #FFFFFF;
	margin: 5px 30px 0px 30px;
	padding: 9px;
	display:block;
	font-size:16px;
	width:76%;
	background: 
		-webkit-gradient(
			linear,
			left top,
			left 25,
			from(#FFFFFF),
			color-stop(4%, #EEEEEE),
			to(#FFFFFF)
		);
	background: 
		-moz-linear-gradient(
			top,
			#FFFFFF,
			#EEEEEE 1px,
			#FFFFFF 25px
			);
	-moz-box-shadow: 0px 0px 8px #f0f0f0;
	-webkit-box-shadow: 0px 0px 8px #f0f0f0;
	box-shadow: 0px 0px 8px #f0f0f0;
}
.form_wrapper input[type="text"]:focus,
.form_wrapper input[type="password"]:focus{
	background:#feffef;
}
.form_wrapper .bottom{
	background-color:#444;
	border-top:1px solid #ddd;
	margin-top:20px;
	clear:both;
	color:#fff;	
}
.form_wrapper .bottom a{
	display:block;
	clear:both;
	padding:10px 30px;
	text-align:right;
	color:#ffa800;
	
}

.form_wrapper span.error{
	visibility:hidden;
	color:red;
	font-size:11px;
	font-style:italic;
	display:block;
	margin:4px 30px;
}
.form_wrapper input[type="submit"] {
	background: #e3e3e3;
	border: 1px solid #ccc;
	color: #000;
	font-family: "Trebuchet MS", "Myriad Pro", sans-serif;
	font-size: 14px;
	font-weight: bold;
	padding: 8px 0 9px;
	text-align: center;
	width: 150px;
	cursor:pointer;
	float:right;
	margin:15px 20px 10px 10px;
	text-shadow: 0px 1px 0px #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	-moz-box-shadow: 0px 0px 2px #fff inset;
	-webkit-box-shadow: 0px 0px 2px #fff inset;
	box-shadow: 0px 0px 2px #fff inset;
}
.form_wrapper input[type="submit"]:hover {
	background: #d9d9d9;
	-moz-box-shadow: 0px 0px 2px #eaeaea inset;
	-webkit-box-shadow: 0px 0px 2px #eaeaea inset;
	box-shadow: 0px 0px 2px #eaeaea inset;
	color: #000;
}

.block {
	text-align: center;
	height: 300px;
}

.block p {
	font-size: 26px;
	margin-top: 125xpx;
	color: white;
}
</style>