<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit"/><fmt:message key="role"/></title>
    <%@ include file="/resources/impForm.jsp"%>
    <script type="text/javascript">
	$(document).ready(function() {
		initMenu("viewRole_Menu");
		
        $.validator.addMethod("letterswith", function(value, element) {
            return this.optional(element) || /^[a-z\-.,()'"\s]+$/i.test(value);
        }, "Letters or punctuation only please");
        var rules = {
            "name":{required:true}
        };
        var messages = {
            "name":{required:"<fmt:message key="role.name"/> <fmt:message key="form.validate.required"/>"}
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
			<li><a href="${ctx}/system/role/view">角色管理</a></li>
			<li class="active">添加角色</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	
	<div class="page-content">
	    <div class="row">
			<div class="col-xs-10">
			      <form:form id="inputForm" modelAttribute="role" action="${ctx}/system/role/save" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/role/view">
					<fieldset>
						 <div class="panel panel-info">
						    <div class="panel-heading"><strong><i class="fa fa-user green"></i> 基本信息</strong></div>
						    <div class="panel-body">
								<div class="form-group">
									<label for="roleName" class="col-sm-2 control-label"><fmt:message key="role.roleName"/>:</label>
									<div class="col-xs-4">
					                    <input type="text" id="code" name="code"  class="form-control" value="${role.code}" readonly/>
									</div>
								</div>
								<div class="form-group">
									<label for="name" class="col-sm-2 control-label"><fmt:message key="role.name"/>:</label>
									<div class="col-xs-4">
										<input type="text" id="name" name="name" class="form-control" value="${role.name }"/>
									</div>
								</div>
								<div class="form-group">
									<label for="status" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
									<div class="col-xs-4">
										<c:forEach items="${statuss}" var="status">
										  <c:if test="${(status == role.status) }">
										     <label class="radio-inline"><input type="radio" name="status" value="${status}" checked="checked"/>${status.statusName}</label>
										  </c:if>
										  <c:if test="${!(status == role.status) }">
					                         <label class="radio-inline"><input type="radio" name="status" value="${status}"/>${status.statusName}</label>
					                      </c:if>
										</c:forEach>
									</div>
								</div>
						  </div>
						</div>
			            
			            <div class="form-group">
			                <div class="col-sm-offset-2 col-sm-10">
			                    <button type="submit" class="btn btn-primary"><i class="fa fa-check"></i> <fmt:message key="submit"/></button>
			                    <button type="reset" class="btn btn-danger"><fmt:message key="reset"/></button>
			                </div>
			            </div>
						<input type="hidden" name="id" value="${role.id }">
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
