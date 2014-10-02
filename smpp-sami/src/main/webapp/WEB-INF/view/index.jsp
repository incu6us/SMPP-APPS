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
<script type="text/javascript" src='<c:url value="/static/utils/smartmenus-0.9.7/libs/jquery-loader.js"></c:url>'></script>

<!-- SmartMenus jQuery plugin -->
<script type="text/javascript" src='<c:url value="/static/utils/smartmenus-0.9.7/jquery.smartmenus.js"></c:url>'></script>

<!-- SmartMenus jQuery init -->
<script type="text/javascript">
	$(function() {
		$('#main-menu').smartmenus({
			subMenusSubOffsetX: 1,
			subMenusSubOffsetY: -8
		});
	});
</script>




<!-- SmartMenus core CSS (required) -->
<link href='<c:url value="/static/utils/smartmenus-0.9.7/css/sm-core-css.css"></c:url>' rel="stylesheet" type="text/css" />

<!-- "sm-blue" menu theme (optional, you can use your own CSS, too) -->
<link href='<c:url value="/static/utils/smartmenus-0.9.7/css/sm-mint/sm-mint.css"></c:url>' rel="stylesheet" type="text/css" />

<!-- #main-menu config - instance specific stuff not covered in the theme -->
<style type="text/css">
	#main-menu {
		position:relative;
		z-index:9999;
		width:auto;
	}
	#main-menu ul {
		width:12em; /* fixed width only please - you can use the "subMenusMinWidth"/"subMenusMaxWidth" script options to override this if you like */
	}
</style>
</head>
<body>

<ul id="main-menu" class="sm sm-mint">
	<li><a href='<c:url value="/" />'>Home</a></li>
	<li><a href='<c:url value="/bulk" />'>Bulk</a></li>
	<li><a href='<c:url value="/settings" />'>Settings</a></li>
</ul>

${testItem}

<h1>Settings: </h1>

<c:forEach var="smppSetting" items="${smppSettings}">
${smppSetting.getName()}<br>
</c:forEach>

</body>
</html>