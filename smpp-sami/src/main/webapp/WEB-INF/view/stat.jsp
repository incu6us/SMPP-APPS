<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>


<c:if test="${empty pageSubName}">
	<center>
		<h2><span class="label label-success">Statistic:</span></h2>
	</center>
	
	<br>
	
	<table class="table table-striped table-bordered">
		<th><center>
				<b>Campaign name:</b>
			</center></th>
		<th><center>
				<b>Source Address:</b>
			</center></th>
		<th><center>
				<b>Message:</b>
			</center></th>
		<th><center>
				<b>Total Messages:</b>
			</center></th>
		<th><center>
				<b>Sending:</b>
			</center></th>
		<th><center>
				<b>Success Sended:</b>
			</center></th>
		<th><center>
				<b>Unsuccess Messages:</b>
			</center></th>
		<th></th>
		<c:forEach items="${messageObjects}" var="messageObject">
			<tr>
				<td><center><a
					href='<c:url value="/stat/list?campaign_id=${messageObject.campaignId}"></c:url>' class="btn btn-default btn-xs">${messageObject.campaignName}</a></center></td>
				<td>${messageObject.campaignSourceAddres}</td>
				<td>${messageObject.campaignText}</td>
				<td width="200">${messageObject.totalMessages}</td>
				<td>${messageObject.inActionMessages}</td>
				<td>${messageObject.successMessages}</td>
				<td>${messageObject.unsuccessMessages}</td>
				<td><center><a href='<c:url value="/stat/delete?campaign_id=${messageObject.campaignId}"></c:url>' type="button" class="btn btn-danger btn-xs">Delete</a></center></td>
			</tr>
		</c:forEach>

	</table>
</c:if>


<c:if test="${pageSubName == 'statDetails'}">
	<center>
		<h2><span class="label label-success">Details:</span></h2>
	</center>
	
	<br>
	
	<table class="table" style="width: 400px; background-color: #fff;">
		<th><center>Description</center></th>
		<th><center>Data</center></th>
		<tr class="info">
			<td>Campaign ID:</td>
			<td>${messageObject.campaignId}</td>
		</tr>
		<tr class="info">
			<td>Campaign Name:</td>
			<td>${messageObject.campaignName}</td>
		</tr>
		<tr class="info">
			<td>Source Address:</td>
			<td>${messageObject.campaignSourceAddres}</td>
		</tr>
		<tr class="info">
			<td>Message Text:</td>
			<td>${messageObject.campaignText}</td>
		</tr>
		<tr>
			<td>Total Messages:</td>
			<td><span id="totalMessages">0</span></td>
		</tr>
		<tr>
			<td>Sending Messages:</td>
			<td><span id="sendingMessages">0</span></td>
		</tr>
		<tr>
			<td>Success Delivered:</td>
			<td><span id="successMessages">0</span></td>
		</tr>
		<tr>
			<td>Undelivered Messages:</td>
			<td><span id="undeliveredMessages">0</span></td>
		</tr>
	</table>
	<br>
	<button onclick="history.back(); return false;" class="btn btn-default btn-sm">Back</button>
</c:if>
