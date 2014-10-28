<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<center>
		<h2><span class="label label-success">User Management:</span></h2>
</center>
	
	<br>
	
<div class="form-group">
<form action='<c:url value="/users"></c:url>' method="get">
	<fieldset>
		<legend></legend>
		<table style="border-spacing: 20px; border-collapse: separate;">
			<tr>
				<td><label>Username: </label></td>
				<td><input type="text" name="username" value=""
					autocomplete="off" /></td>
			</tr>
			<tr>
				<td><label>Password: </label></td>
				<td><input type="password" name="password" value=""
					autocomplete="off" /></td>
			</tr>
			<tr>
				<td><label>Role: </label></td>
				<td><select id="role" name="role" class="form-control">
						<option value="0">SU</option>
						<option value="1">Admin</option>
						<option value="2" selected="selected">User</option>
				</select></td>
			</tr>
			<tr>
				<td><button class="btn btn-default" type="submit">Add</button></td>
				<td></td>
			</tr>
		</table>
		<legend></legend>
	</fieldset>
</form>
<c:if test="${!empty userList}">
	<table class="table table-striped table-bordered table-hover" style="background-color: #dff0d8" id="tableQuery">
		<thead>
		<th>#</th>
		<th>Username</th>
		<th>Roles</th>
		<th></th>
		</thead>
		<tbody>
		<c:forEach items="${userList}" var="user" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${user.username}</td>
				<td>${user.role}</td>
				<td><a class='btn btn-danger btn-xs'
					href='<c:url value="/users/delete/${user.id}"></c:url>'>Delete</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</c:if>
</div>
