<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit"/><fmt:message key="dictionary"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewDictionary_Menu");
			var rules = {
	                 "name":{required:true},
	                 "code": {required:true,
          	          remote:{
          	        	  method:"post",
          	        	  url:"${ctx}/system/dictionary/isNotExistCode/",
          	        	  dataType:'json',
          	        	  data:{
          	                  "code":function(){return $.trim($("#code").val()) + "," + $.trim($("#parentID").val().trim());}
          	                } 
          	          }
                       },
				"sn":{number:true}
		        };
		        var messages= {   
		            "name":{required:"字典名称不能为空"},
		            "code":{required:"字典代码不能为空",remote:"字典代码在该分类下已存在"},
					"sn":{number:"请输入整数"}
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
			<li><a href="${ctx}/system/dictionary/view">字典管理</a></li>
			<li class="active">修改字典</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
    <div class="row">
			<div class="col-xs-10">
				<form:form id="inputForm" modelAttribute="dictionary" action="${ctx}/system/dictionary/update/" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/dictionary/view?parentId=${dictionary.parent.id}">
					<fieldset>
					<div class="panel panel-info">
						  <div class="panel-heading"><strong><i class="fa fa-book green"></i> 基本信息</strong></div>
						  <div class="panel-body">
								<div class="form-group">
									<label for="name" class="col-sm-2 control-label"><fmt:message key="dictionary.name"/>:</label>
									<div class="col-xs-4">
										<input type="text" id="name" name="name" class="form-control" value="${dictionary.name}"/>
									</div>
								</div>
								<div class="form-group">
									<label for="code" class="col-sm-2 control-label"><fmt:message key="dictionary.code"/>:</label>
									<div class="col-xs-4">
										<input type="text" class="form-control" value="${dictionary.code}" disabled/>
										<input type="hidden" id="code" name="code" class="form-control" value="${dictionary.code}" />
									</div>
								</div>
								
								<div class="form-group">
									<label for="pname" class="col-sm-2 control-label">字典分类:</label>
									<div class="col-xs-4">
										<c:if test="${dictionary.parent != null }">
											<input type="text" id="pname" name="pname" class="form-control" value="${dictionary.parent.name}" disabled/>
											<input type="hidden" id="parentCode" name="parent.code" class="form-control" value="${parent.code}"/>
											<input type="hidden" id="parentID" name="parent.id" value="${dictionary.parent.id}"/>
										</c:if>
										<c:if test="${dictionary.parent == null }">
											<input type="text" id="pname" name="pname" class="form-control" value="" disabled/>
											<input type="hidden" id="parentID" name="parent.id" value="" disabled/>
										</c:if>
									</div>
								</div>
								<div class="form-group">
									<label for="isEnabled" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
									<div class="col-xs-4">
									    <c:if test="${dictionary.isEnabled}">
									       <label class="radio-inline"><input type="radio" name="isEnabled" value="true" checked="checked"/>启用</label>
										   <label class="radio-inline"><input type="radio" name="isEnabled" value="false"/>禁用</label>
									    </c:if>
										 <c:if test="${!dictionary.isEnabled}">
									       <label class="radio-inline"><input type="radio" name="isEnabled" value="true"/>启用</label>
										   <label class="radio-inline"><input type="radio" name="isEnabled" value="false" checked="checked"/>禁用</label>
									    </c:if>
									</div>
								</div>
								<div class="form-group">
									<label for="description" class="col-sm-2 control-label"><fmt:message key="description"/>:</label>
									<div class="col-xs-4">
									    <textarea rows="3" id="description" name="description" class="form-control">${dictionary.description}</textarea>
									</div>
								 </div>
							  <div class="form-group">
								  <label for="sn" class="col-sm-2 control-label">显示顺序:</label>
								  <div class="col-xs-4">
									  <input type="text" id="sn" name="sn" placeholder="请输入显示顺序" class="form-control" value="${dictionary.sn}"/>
								  </div>
							  </div>
								</div>
								</div>	
								<div class="form-group">
						            <div class="col-sm-offset-2 col-sm-10">
					                    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> <fmt:message key="submit"/></button>
					                    <button type="reset" class="btn btn-danger"><fmt:message key="reset"/></button>
					                    <input type="hidden" id="id" name="id" value="${dictionary.id}"/>
									</div>
								</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
