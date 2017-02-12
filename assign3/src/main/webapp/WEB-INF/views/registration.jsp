<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>

<script
	src="<%=request.getContextPath()%>/resources/js/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-ui.js"></script>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery-ui.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery-ui.structure.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery-ui.theme.css">


<style>
.error {
	color: #ff0000;
}
</style>


<script>
	$(function() {
		$("input[name=birthDate]").datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat: "yy-mm-dd"
		});
	});

	$(document)
			.ready(
					function() {
						$("input[name=username],input[name=email]")
								.blur(
										function() {

											var token = $("meta[name='_csrf']")
													.attr("content");
											var header = $(
													"meta[name='_csrf_header']")
													.attr("content");

											var userdata = {}
											userdata["username"] = $(
													"#username").val();
											userdata["email"] = $("#email")
													.val();

											var action = "${pageContext.request.contextPath}/search/api/getSearchResult"

											$
													.ajax({
														type : "POST",
														url : action,
														data : JSON
																.stringify(userdata),
														beforeSend : function(
																xhr) {
															xhr
																	.setRequestHeader(
																			header,
																			token);
															xhr
																	.setRequestHeader(
																			"Accept",
																			"application/json");
															xhr
																	.setRequestHeader(
																			"Content-Type",
																			"application/json");
														},
														success : function(data) {
															console
																	.log(
																			"SUCCESS: ",
																			data);

															if (data.status == "FAIL") {
																errorInfo = "";

																if (data.usermsg != null)
																	errorInfo += "<br>"
																			+ ". "
																			+ data.usermsg;
																if (data.emailmsg != null)
																	errorInfo += "<br>"
																			+ ". "
																			+ data.emailmsg;
																if (data.validemail != null)
																	errorInfo += "<br>"
																			+ ". "
																			+ data.validemail;
																$('#error')
																		.html(
																				"Please correct following errors: "
																						+ errorInfo);
															}
														},
														error : function(e) {
															console.log(
																	"ERROR: ",
																	e);
														}
													});
										});
					});
</script>

</head>

<body>

	<h2>Registration Form</h2>

	<form:form method="POST" id="newUserForm" modelAttribute="userForm">
		<table>

			<tr>
				<td colspan="2"><div id="error" class="error"></div></td>
			</tr>

			<tr>
				<td><label for="username">Username: </label></td>
				<td><form:input type="text" path="username" id="username" /></td>
				<td><form:errors path="username" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="email">Email: </label></td>
				<td><form:input type="text" path="email" id="email" /></td>
				<td><form:errors path="email" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="birthDate">Birthday: </label></td>
				<td><form:input path="birthDate" id="birthDate" /></td>
				<td><form:errors path="birthDate" cssClass="error" /></td>
			</tr>

			<tr>
				<td>Sex :</td>
				<td><form:radiobutton path="sex" value="1" />Male <form:radiobutton
						path="sex" value="0" />Female</td>
				<td><form:errors path="sex" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="password">Password: </label></td>
				<td><form:input type="password" path="password" id="password" /></td>
				<td><form:errors path="password" cssClass="error" /></td>
			</tr>

			<tr>
				<td><label for="passwordConfirm">Password Confirmation:
				</label></td>
				<td><form:input type="password" path="passwordConfirm"
						id="passwordConfirm" /></td>
				<td><form:errors path="passwordConfirm" cssClass="error" /></td>
			</tr>

			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" /></td>
			</tr>
		</table>

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />

	</form:form>
</body>
</html>