<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>


<c:if test="${empty pageSubName}">
	<center>
		<h2>Statistic:</h2>
	</center>

	<table class="table table-striped table-bordered">
		<th><center><b>Campaign name:</b></center></th>
		<th><center><b>Source Address:</b></center></th>
		<th><center><b>Message:</b></center></th>
		<th><center><b>Total Messages:</b></center></th>
		<th><center><b>Sending:</b></center></th>
		<th><center><b>Success Sended:</b></center></th>
		<th><center><b>Unsuccess Messages:</b></center></th>

		<c:forEach items="${messageObjects}" var="messageObject">
			<tr>
				<td><a
					href='<c:url value="?campaing_id=${messageObject.campaignId}"></c:url>'>${messageObject.campaignName}</a></td>
				<td>${messageObject.campaignSourceAddres}</td>
				<td>${messageObject.campaignText}</td>
				<td width="200">${messageObject.totalMessages}</td>
				<td>${messageObject.inActionMessages}</td>
				<td>${messageObject.successMessages}</td>
				<td>${messageObject.unsuccessMessages}</td>
			</tr>
		</c:forEach>

	</table>
</c:if>


<c:if test="${pageSubName == 'statDetails'}">
	<center>
		<h2>Details:</h2>
	</center>

	<table class="table table-striped table-bordered">
		<tr>
			<td>Campaign ID:</td><td>${messageObject.campaignId}</td>
		</tr>
		<tr>
			<td>Campaign Name:</td><td>${messageObject.campaignName}</td>
		</tr>
		<tr>
			<td>Source Address:</td><td>${messageObject.campaignSourceAddres}</td>
		</tr>
		<tr>
			<td>Message Text:</td><td>${messageObject.campaignText}</td>
		</tr>
		<tr>
			<td>Total Messages:</td><td><label id="totalMessages">0</label></td>
		</tr>
		<tr>
			<td>Sending Messages:</td><td><label id="sendingMessages">0</label></td>
		</tr>
		<tr>
			<td>Success Delivered:</td><td><label id="successMessages">0</label></td>
		</tr>
		<tr>
			<td>Undelivered Messages:</td><td><label id="undeliveredMessages">0</label></td>
		</tr>
	</table>
</c:if>
