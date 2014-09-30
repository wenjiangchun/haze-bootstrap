<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户授权</title>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>

<body>
	<ul class="breadcrumb">
        <li><a href="${ctx}"><i class="icon-home"></i><fmt:message key="home"/></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="system" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li><a href="${ctx}/system/user/view"><fmt:message key="user" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li class="active"><fmt:message key="user" /><fmt:message key="role" /></li>
    </ul>
	<form:form id="inputForm" action="${ctx}/system/user/saveRoles" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}"/>
		<fieldset>
			<legend><fmt:message key="system" /><fmt:message key="role" /><fmt:message key="list" /></legend>
			<div class="control-group">
				<label for="groupList" class="control-label"><fmt:message key="select" /><fmt:message key="role" />:</label>
				<div class="controls">
				   <c:forEach items="${user.roles}" var="role" varStatus="idxStatus">
                        <label class="checkbox"><input type="checkbox" name="roleIds" value="${role.id }" checked="checked"/>${role.name }</label> 
                    </c:forEach>
					<c:forEach items="${roleList}" var="role" varStatus="idxStatus">
						<label class="checkbox"><input type="checkbox" name="roleIds" value="${role.id }"/>${role.name } </label>
					</c:forEach>
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit" />"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel" />" onclick="history.back()"/>
			</div>
		</fieldset>
	</form:form>
</body>
</html>
