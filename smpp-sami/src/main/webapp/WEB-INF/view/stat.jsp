<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>

<center>
	<h2>Statistic:</h2>
</center>

<table class="table table-striped table-bordered">
	<th><center><b>Campaign name:</b></center></th>
	<th><center><b>Source Address:</b></center></th>
	<th><center><b>Message:</b></center></th>
	
	<c:forEach items="${messageObjects}" var="messageObject">
		<tr>
			<td>${messageObject.campaignName}</td>
			<td>${messageObject.campaignSourceAddres}</td>
			<td>${messageObject.campaignText}</td>
		</tr>
	</c:forEach>
</table>

