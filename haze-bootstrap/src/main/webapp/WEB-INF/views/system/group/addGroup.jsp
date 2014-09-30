<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="config"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
		$(document).ready(function() {
			var rules = {
					"fullName":{required:true}
			};
			var messages = {
					"fullName":{required:"机构名称不能为空"}
			}
			initFormValidate("inputForm", rules, messages);
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="group" action="${ctx}/system/group/save" method="post" role="form" class="form-horizontal">
		<fieldset>
			<legend><fmt:message key="add" /><fmt:message key="group" /></legend>
			<div class="form-group">
				<label for="fullName" class="col-sm-2 control-label"><fmt:message key="group.fullName"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="fullName" name="fullName" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="group.name"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="groupType.id" class="col-sm-2 control-label"><fmt:message key="group.groupType"/>:</label>
				<div class="col-xs-4">
				    <select name="groupType.id">
				        <c:forEach items="${groupTypeList}" var="groupType">
				             <option value="${groupType.id}">${groupType.name}</option>
				        </c:forEach>
				    </select>
				</div>
			</div>
			<div class="form-group">
				<label for="groupCode" class="col-sm-2 control-label"><fmt:message key="group.groupCode"/>:</label>
				<div class="col-xs-4">
				   <input type="text" id="groupCode" name="groupCode" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="pname" class="col-sm-2 control-label">所在机构:</label>
				<div class="col-xs-4">
					<c:if test="${parent != null }">
						<input type="text" id="pname" name="pname" class="form-control" value="${parent.fullName}" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="${parent.id}"/>
					</c:if>
					<c:if test="${parent == null }">
						<input type="text" id="pname" name="pname" class="form-control" value="" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="" disabled/>
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<label for="tel" class="col-sm-2 control-label"><fmt:message key="group.tel"/>:</label>
				<div class="col-xs-4">
				   <input type="text" id="tel" name="tel" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="address" class="col-sm-2 control-label"><fmt:message key="group.address"/>:</label>
				<div class="col-xs-4">
				   <input type="text" id="address" name="address" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel"/>" onclick="history.back()"/>
				</div>
			</div>
			<c:if test="${parentId != null }">
				<input type="hidden" name="parent.id" value="${parentId}"/>
			</c:if>
		</fieldset>
	</form:form>
</body>
</html>
