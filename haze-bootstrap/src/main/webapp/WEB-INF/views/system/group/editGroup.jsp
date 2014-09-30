<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit"/><fmt:message key="config"/></title>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
		        success: function(label) {
		        },
		        errorPlacement: function(error, element) { 
		        	error.appendTo( element.parent() ); 
		        },
		        errorClass: "help-inline",
		        focusInvalid: false,   
		        onkeyup: true,
		        highlight: function(element, errorClass) {
		            $(element).parent().parent().removeClass("success").addClass("error");
		         },
		        unhighlight: function(element, errorClass) {
	                $(element).parent().parent().removeClass("error").addClass("success");
	            },
		        rules: {
	                 "name":{required:true},
		        },
		        messages: {   
		            "name":{required:"机构名称不能为空"},
		        }
		    });
		});
	</script>
</head>

<body>
    <ul class="breadcrumb">
        <li><a href="${ctx}"><i class="icon-home"></i><fmt:message key="home"/></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="system" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li><a href="${ctx}/system/group/view"><fmt:message key="group" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li class="active"><fmt:message key="edit" /><fmt:message key="group" /></li>
    </ul>
	<form:form id="inputForm" modelAttribute="group" action="${ctx}/system/group/update" method="post" class="form-horizontal">
		<fieldset>
			<legend><fmt:message key="edit" /><fmt:message key="group" /></legend>
			<div class="control-group">
				<label for="fullName" class="control-label"><fmt:message key="group.fullName"/>:</label>
				<div class="controls">
					<input type="text" id="fullName" name="fullName" class="input-large" value="${group.fullName}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label"><fmt:message key="group.name"/>:</label>
				<div class="controls">
					<input type="text" id="name" name="name" class="input-large" value="${group.name}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="groupType.id" class="control-label"><fmt:message key="group.groupType"/>:</label>
				<div class="controls">
				    <select name="groupType.id">
				        <option></option>
				        <c:forEach items="${groupTypeList}" var="groupType">
				             <c:if test="${groupType.id == group.groupType.id }">
				                <option value="${groupType.id}" selected="selected">${groupType.name}</option>
				             </c:if>
				             <c:if test="${groupType.id != group.groupType.id }">
				                <option value="${groupType.id}">${groupType.name}</option>
				             </c:if>
				        </c:forEach>
				    </select>
				</div>
			</div>
			<div class="control-group">
				<label for="groupCode" class="control-label"><fmt:message key="group.groupCode"/>:</label>
				<div class="controls">
				   <input type="text" id="groupCode" name="groupCode" class="input-large" value="${group.groupCode}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="pname" class="control-label">所在机构:</label>
				<div class="controls">
					<c:if test="${group.parent != null }">
						<input type="text" id="pname" name="pname" class="input-large" value="${group.parent.fullName}" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="${group.parent.id}"/>
					</c:if>
					<c:if test="${group.parent == null }">
						<input type="text" id="pname" name="pname" class="input-large" value="" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="" disabled/>
					</c:if>
				</div>
			</div>
			<div class="control-group">
				<label for="tel" class="control-label"><fmt:message key="group.tel"/>:</label>
				<div class="controls">
				   <input type="text" id="tel" name="tel" class="input-large" value="${group.tel}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="address" class="control-label"><fmt:message key="group.address"/>:</label>
				<div class="controls">
				   <input type="text" id="address" name="address" class="input-large" value="${group.address}"/>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel"/>" onclick="history.back()"/>
			</div>
			<input type="hidden" name="id" value="${group.id}"/>
		</fieldset>
	</form:form>
</body>
</html>
