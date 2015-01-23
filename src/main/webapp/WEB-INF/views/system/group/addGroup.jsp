<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="group"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewGroup_Menu");
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
			<li><a href="${ctx}/system/group/view">机构管理</a></li>
			<li class="active">添加机构</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
    <div class="row">
			<div class="col-xs-10">
	<form:form id="inputForm" modelAttribute="group" action="${ctx}/system/group/save" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/group/view?parentId=${parentId}">
		<fieldset>
		<div class="panel panel-info">
			  <div class="panel-heading"><strong><i class="fa fa-group green"></i> 基本信息</strong></div>
			  <div class="panel-body">
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
				<label for="code" class="col-sm-2 control-label"><fmt:message key="group.groupCode"/>:</label>
				<div class="col-xs-4">
				   <input type="text" id="code" name="code" class="form-control"/>
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
                  <label for="remark" class="col-sm-2 control-label">备注:</label>
                  <div class="col-xs-4">
                      <input type="text" id="remark" name="remark" class="form-control"/>
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
		</fieldset>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>
