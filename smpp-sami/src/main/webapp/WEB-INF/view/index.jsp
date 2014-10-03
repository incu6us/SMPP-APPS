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
<script type="text/javascript"
	src='<c:url value="/static/utils/smartmenus-0.9.7/libs/jquery-loader.js"></c:url>'></script>

<!-- SmartMenus jQuery plugin -->
<script type="text/javascript"
	src='<c:url value="/static/utils/smartmenus-0.9.7/jquery.smartmenus.js"></c:url>'></script>

<!-- SmartMenus jQuery init -->
<script type="text/javascript">
	$(function() {
		$('#main-menu').smartmenus({
			subMenusSubOffsetX : 1,
			subMenusSubOffsetY : -8
		});
	});
</script>




<!-- SmartMenus core CSS (required) -->
<link
	href='<c:url value="/static/utils/smartmenus-0.9.7/css/sm-core-css.css"></c:url>'
	rel="stylesheet" type="text/css" />

<!-- "sm-blue" menu theme (optional, you can use your own CSS, too) -->
<link
	href='<c:url value="/static/utils/smartmenus-0.9.7/css/sm-red/sm-red.css"></c:url>'
	rel="stylesheet" type="text/css" />

<!-- #main-menu config - instance specific stuff not covered in the theme -->
<style type="text/css">
#main-menu {
	position: relative;
	z-index: 9999;
	width: auto;
}

#main-menu ul {
	width: 12em;
	/* fixed width only please - you can use the "subMenusMinWidth"/"subMenusMaxWidth" script options to override this if you like */
}
</style>
</head>
<body>

	<ul id="main-menu" class="sm sm-red" style="z-index: 1;">
		<li><a href='<c:url value="/" />'>Home</a></li>
		<li><a href='<c:url value="/messaging" />'>Messaging</a></li>
		<li><a href='<c:url value="/bulk" />'>Bulk Messaging</a></li>
		<li><a href='<c:url value="/settings" />'>Settings</a></li>
	</ul>

	<!-- show user account -->
	<div style="position: absolute; padding-right: 40px; right: 0; top: 15px; z-index:2;">Logged as: user</div>	

	<div style="margin-top: 20px; padding: 0px 20px 20px 20px;background-color: #eee; border-style:solid; border-color: #ed1848; border-width: 1px;">

		<center><h2>Bulk Messaging:</h2></center>
		
		<center>
			<c:choose>
				<c:when test="${uploadState=='fileIsEmpty'}">
					<div style="border-style:solid; border-color: #ed1848; border-width: 1px; width: 200px;">Choose file first!</div>
				</c:when>
				<c:when test="${uploadState=='fail'}">
					<div style="border-style:solid; border-color: #ed1848; border-width: 1px; width: 200px;">Unknown Error!</div>
				</c:when>
				<c:when test="${uploadState=='success'}">
					<div style="border-style:solid; border-color: green; border-width: 1px; width: 200px;">Upload success!</div>
				</c:when>
			</c:choose>
		</center>
		
		<c:forEach var="smppSetting" items="${smppSettings}">
			<%-- ${smppSetting.getName()}<br>--%>
		</c:forEach>
		
		<form method="post" enctype="multipart/form-data" action='<c:url value="/upload" />'">
			<table border="0">
				<tr>
					<td>Name:</td><td><input name="name" type="text" /></td>
				</tr>
				<tr>
					<td>MSISDN File:</td><td> <input name="file" type="file" /> </td>
				</tr>
				<tr>
					<td>Text:</td><td><textarea name="text" rows="20" cols="100"></textarea></td>
				</tr>
				<tr>
					<td></td><td><button type="submit">Send</button></td>
				</tr>

			</table>
		</form>

	</div>
</body>
</html>