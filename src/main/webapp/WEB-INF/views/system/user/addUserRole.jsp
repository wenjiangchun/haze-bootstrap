<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户授权</title>
	<%@ include file="/resources/impForm.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewUser_Menu");
			var rules = {
			};
			var messages = {
			};
			initFormValidate("inputForm", rules, messages);
		});
	</script>
</head>

<body>
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="#">主页</a></li>
			<li><a href="#">系统管理</a></li>
			<li><a href="${ctx}/system/user/view">用户管理</a></li>
			<li class="active">用户授权</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
    <div class="row">
			<div class="col-xs-10">
		       <form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/saveRoles" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/user/view?groupId=${groupId}">
				<fieldset>
				  <div class="panel panel-info">
					  <div class="panel-heading"><strong><i class="fa fa-user green"></i> 角色信息</strong></div>
						  <div class="panel-body">
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
						</div>
					</div>
					<div class="form-group">
			            <div class="col-sm-offset-2 col-sm-10">
		                    <button type="reset" class="btn btn-danger"><fmt:message key="reset"/></button>
		                    <button type="submit" class="btn btn-primary"><span class="fa fa-check"></span> <fmt:message key="submit"/></button>
						</div>
					</div>
					<input type="hidden" name="id" value="${user.id}"/>
				</fieldset>
			</form:form>
	</div>
	</div>
	</div>
</body>
</html>
