<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<center>
	<h2>
		<span class="label label-success">Create Campaign:</span>
	</h2>
</center>

<br>

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

<form class="form-horizontal" method="post"
	enctype="multipart/form-data" action='<c:url value="/upload" />'">
	<fieldset>

		<!-- Form Name -->
		<center><legend style="width: 600px;"></legend></center>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="campName">Campaign
				Name</label>
			<div class="col-md-4">
				<input id="campName" name="campName"
					placeholder="campaign identifier" class="form-control input-md"
					required="" type="text">

			</div>
		</div>

		<!-- Text input-->
		<div class="form-group">
			<label class="col-md-4 control-label" for="sourceAddress">Source
				Address</label>
			<div class="col-md-4">
				<input id="sourceAddress" name="sourceAddress" placeholder=""
					class="form-control input-md" required="" type="text"> <span
					class="help-block">example: alpha-name or msisdn</span>
			</div>
		</div>

		<!-- Select Validity Period -->
		<div class="form-group">
		  <label class="col-md-4 control-label" for="validityPeriod">Validity period</label>
		  <div class="col-md-4">
		    <select id="validityPeriod" name="validityPeriod" class="form-control">
		      <option value="000000001500000R">15 min</option>
		      <option value="000000003000000R">30 min</option>
		      <option value="000000010000000R">1 hour</option>
		      <option value="000000020000000R" selected="selected">2 hours</option>
		      <option value="000000030000000R">3 hours</option>
		      <option value="000000060000000R">6 hours</option>
		      <option value="000000115959000R">12 hours</option>
		      <option value="000001000000000R">1 day</option>
		      <option value="000003000000000R">3 days</option>
		      <option value="000005000000000R">5 days</option>
		      <option value="000007000000000R">7 days</option>
		    </select>
		  </div>
		</div>

		<!-- Multiple Radios -->
		<div class="form-group">
		  <label class="col-md-4 control-label" for="radios">SMPP System ID</label>
		  <div class="col-md-4">
		  <div class="radio" style="background-color: #fff;">
		    <label for="radios-0">
		      <input name="radios" id="radios-0" value="" checked="checked" type="radio">
		      <label class="label label-default">- ALL ACTIVE SYSTEM IDs -</label>
		    </label>
			</div>
		  <c:forEach items="${smppSettings}" var="smpp">
		  <div class="radio" style="background-color: #fff;">
		    <label for="radios-${smpp.systemId}">
		      <input name="radios" id="radios-${smpp.systemId}" value="${smpp.id}" type="radio">
		      <label class="label label-default">${smpp.systemId}</label> <label id="status-${smpp.systemId}"><span class="label label-success label-xs">free</span></label>
		    </label>
			</div>
		  </c:forEach>
		  </div>
		</div>
		
		<!-- File Button -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="file">Uploaded
				File</label>
			<div class="col-md-4">
				<input id="file" name="file" class="input-file" type="file">
			</div>
		</div>

		<!-- Textarea -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="message">Message
				Text</label>
			<div class="col-md-4">
				<textarea class="form-control" rows="20" cols="100" id="message"
					name="message" onKeyUp="len_display(this,0,'long_len')"></textarea>
			</div>
		</div>

		<div class="form-group">
			<p class="col-sm-4 control-label">
			<div class="col-sm-4">
				<input type="text" id="long_len" value="0" class="len" disabled="disabled" size="5"> <label style="color: white;">Char(s)</label>
			</div>
		</div>

		<!-- Button -->
		<div class="form-group">
			<label class="col-md-4 control-label" for="submit"></label>
			<div class="col-md-4">
				<button id="submit" name="submit" class="btn btn-default">Submit</button>
			</div>
		</div>

	</fieldset>
</form>