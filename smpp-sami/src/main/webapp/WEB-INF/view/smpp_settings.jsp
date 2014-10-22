<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<center>
	<h2>
		<span class="label label-success">SMPP Settings:</span>
	</h2>
</center>

<br>

<center>
	<table>
		<tr>
			<td valign="top"><c:if test="${empty pageSubName}">
					<form class="form-horizontal" method="post"
						action='<c:url value="/smpp_settings/add_sysid"></c:url>'
						style="background-color: #fff; padding: 10px 10px 10px 10px">
						<fieldset>

							<!-- Form Name -->
							<legend style="width: 400px;">Add SMPP Account</legend>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="systemId">System
									ID</label>
								<div class="col-md-4">
									<input id="systemId" name="systemId" type="text"
										placeholder="system_id" class="form-control input-md"
										required="">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="passwd">Password</label>
								<div class="col-md-4">
									<input id="passwd" name="passwd" type="text"
										placeholder="password" class="form-control input-md"
										required="">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="host">Host
									Address</label>
								<div class="col-md-4">
									<input id="host" name="host" type="text" placeholder="0.0.0.0"
										class="form-control input-md" required="">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="port">Port</label>
								<div class="col-md-4">
									<input id="port" name="port" type="text" placeholder="16000"
										class="form-control input-md" required="">

								</div>
							</div>

							<!-- Select Basic -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="active">Status</label>
								<div class="col-md-4">
									<select id="active" name="active" class="form-control">
										<option value="1">Active</option>
										<option value="0">In Active</option>
									</select>
								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="speed">Speed</label>
								<div class="col-md-4">
									<input id="speed" name="speed" type="text" placeholder="50"
										class="form-control input-md" required="">

								</div>
							</div>

							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="submit"></label>
								<div class="col-md-4">
									<button id="submit" name="submit" type="submit"
										class="btn btn-default">Create</button>
								</div>
							</div>

						</fieldset>
					</form>
				</c:if> <c:if test="${pageSubName == 'changeSystemId'}">
					<form class="form-horizontal" method="post"
						action='<c:url value="/smpp_settings/change_sysid"></c:url>'
						style="background-color: #fff; padding: 10px 10px 10px 10px">
						<fieldset>

							<!-- Form Name -->
							<legend style="width: 400px;">Change SMPP Account</legend>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="systemId">System
									ID</label>
								<div class="col-md-4">
									<input id="systemId" name="systemId" type="text"
										placeholder="system_id" class="form-control input-md"
										required="" value="${smppSetting.systemId}">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="passwd">Password</label>
								<div class="col-md-4">
									<input id="passwd" name="passwd" type="text"
										placeholder="password" class="form-control input-md"
										required="" value="${smppSetting.password}">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="host">Host
									Address</label>
								<div class="col-md-4">
									<input id="host" name="host" type="text" placeholder="0.0.0.0"
										class="form-control input-md" required="" value="${smppSetting.host}">

								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="port">Port</label>
								<div class="col-md-4">
									<input id="port" name="port" type="text" placeholder="16000"
										class="form-control input-md" required="" value="${smppSetting.port}">

								</div>
							</div>

							<!-- Select Basic -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="active">Status</label>
								<div class="col-md-4">
									<select id="active" name="active" class="form-control">
										<c:if test="${smppSetting.active == 1}">
											<option value="1" selected="selected">Active</option>
											<option value="0">In Active</option>
										</c:if>
										<c:if test="${smppSetting.active != 1}">
											<option value="1">Active</option>
											<option value="0" selected="selected">In Active</option>
										</c:if>
									</select>
								</div>
							</div>

							<!-- Text input-->
							<div class="form-group">
								<label class="col-md-4 control-label" for="speed">Speed</label>
								<div class="col-md-4">
									<input id="speed" name="speed" type="text" placeholder="50"
										class="form-control input-md" required="" value="${smppSetting.maxMessagesLimitForSysId}">

								</div>
							</div>

							<!-- Button -->
							<div class="form-group">
								<label class="col-md-4 control-label" for="submit"></label>
								<div class="col-md-4">
									<button id="submit" name="submit" type="submit"
										class="btn btn-default">Change</button>
								</div>
							</div>

						</fieldset>
						<input type="hidden" id="id" name="id" value="${smppSetting.id}">
					</form>
				</c:if></td>
			<td valign="top">
				<table class="table"
					style="width: 400px; background-color: #fff; margin-left: 60px;">
					<th><center>#</center></th>
					<th><center>System Id</center></th>
					<th><center>Host</center></th>
					<th><center>Status</center></th>
					<th><center>Speed</center></th>
					<th></th>
					<th></th>
					<c:forEach items="${smppSettings}" var="smppSetting" varStatus="rowCounter">
						<tr>
							<td>${rowCounter.index+1}</td>
							<td>${smppSetting.systemId}</td>
							<td>${smppSetting.host}:${smppSetting.port}</td>
							<c:if test="${smppSetting.active == '1'}">
								<td><span class="label label-success label-xs">active</span></td>
							</c:if>
							<c:if test="${smppSetting.active != '1'}">
								<td><span class="label label-danger label-xs">inactive</span></td>
							</c:if>
							<td>${smppSetting.maxMessagesLimitForSysId}</td>
							<td><center>
									<a
										href="<c:url value="/smpp_settings/change_sysid?id=${smppSetting.id}"></c:url>"
										class="btn btn-primary btn-xs">change</a>
								</center></td>
							<td><center>
									<a
										href="<c:url value="/smpp_settings/delete_sysid?id=${smppSetting.id}"></c:url>"
										class="btn btn-danger btn-xs">delete</a>
								</center></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</center>
