<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<center>
	<h2>Bulk Messaging:</h2>
</center>

<center>
	<c:choose>
		<c:when test="${uploadState=='fileIsEmpty'}">
			<div
				style="border-style: solid; border-color: #ed1848; border-width: 1px; width: 200px;">Choose
				file first!</div>
		</c:when>
		<c:when test="${uploadState=='fail'}">
			<div
				style="border-style: solid; border-color: #ed1848; border-width: 1px; width: 200px;">Unknown
				Error!</div>
		</c:when>
		<c:when test="${uploadState=='success'}">
			<div
				style="border-style: solid; border-color: green; border-width: 1px; width: 200px;">Upload
				success!</div>
		</c:when>
	</c:choose>
</center>

<c:forEach var="smppSetting" items="${smppSettings}">
	<%-- ${smppSetting.getName()}<br>--%>
</c:forEach>

<form method="post" enctype="multipart/form-data"
	action='<c:url value="/upload" />'">
	<table border="0">
		<tr>
			<td>Campaign Name:</td>
			<td><input name="campName" type="text" /></td>
		</tr>
		<tr>
			<td>Source Address:</td>
			<td><input name="sourceAddress" type="text" /></td>
		</tr>
		<tr>
			<td>MSISDN File:</td>
			<td><input name="file" type="file" /></td>
		</tr>
		<tr>
			<td>Text:</td>
			<td><textarea name="message" rows="20" cols="100"></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td><button type="submit">Send</button></td>
		</tr>

	</table>
</form>