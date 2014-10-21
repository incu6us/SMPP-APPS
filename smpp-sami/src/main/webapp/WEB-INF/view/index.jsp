<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--  <link rel="stylesheet" href='<c:url value="/static/css/login/style.css"></c:url>'>-->

<!-- jQuery -->
<script src='<c:url value="/static/js/control/jquery.min.js"></c:url>'></script>

<!-- Bootstrap -->
<link rel="stylesheet"
	href='<c:url value="/static/css/control/bootstrap.min.css"></c:url>' />
<script
	src='<c:url value="/static/js/control/bootstrap.min.js"></c:url>'></script>

<c:if test="${pageSubName == 'statDetails'}">
	<script type="text/javascript">
	$(document).ready(function () {
	    var interval = 2000;   //number of mili seconds between each call
	    var refresh = function() {
	        $.ajax({
	            url: "rest/getMessageCountsByStatuses/7",
	            type: 'GET',
	            cache: false,
	            dataType: 'json',
	            success: function(html) {
	                $('#totalMessages').html(JSON.stringify(html['total']));
	                $('#sendingMessages').html(JSON.stringify(html['sending']));
	                $('#successMessages').html(JSON.stringify(html['success']));
	                $('#undeliveredMessages').html(JSON.stringify(html['unsuccess']));
	                setTimeout(function() {
	                    refresh();
	                    console.debug(JSON.stringify(html));
	                }, interval);
	            }
	        });
	    };
	    refresh();
	});
	</script>
</c:if>
</head>
<body>
	<div>
		<nav id="myNavbar" class="navbar navbar-inverse navbar-fixed-top"
			role="navigation"> <!-- Brand and toggle get grouped for better mobile display -->
		<div class="container">
			<div class="navbar-header">
				<!-- <img src="img/logo.png" alt="logo" width="60px" />-->
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">Menu <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href='<c:url value="/"></c:url>'>Create Campaign</a></li>
							<li><a href='<c:url value="/stat"></c:url>'>Statistics</a></li>

						</ul></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" data-toggle="dropdown"
						class="dropdown-toggle">VIACHESLAV.PRYIMAK <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href='<c:url value="/users"></c:url>'>User
									Management</a></li>
							<li><a href='<c:url value="/settings"></c:url>'>SMPP
									Settings</a></li>
							<li class="divider"></li>
							<li><a href="?logout">Logout</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
		</nav>
	</div>

	<div style="padding: 20px 20px 20px 20px">
		<div
			style="margin-top: 60px; padding: 0px 20px 20px 20px; background-color: #eee; border-style: solid; border-color: #ed1848; border-width: 1px;">

			<c:choose>
				<c:when test="${pageName=='index'}">
					<jsp:include page="create_campaign.jsp"></jsp:include>
				</c:when>
				<c:when test="${pageName=='stat'}">
					<jsp:include page="stat.jsp"></jsp:include>
				</c:when>
			</c:choose>

		</div>
	</div>
</body>
</html>