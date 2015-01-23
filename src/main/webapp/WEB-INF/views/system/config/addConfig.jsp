<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/resources/impTagLib.jsp"%>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="config"/></title>
    <%@ include file="/resources/impForm.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewConfig_Menu");
			var rules = {
				           "code": {required:true,
		          	          remote:{
		          	        	  method:"post",
		          	        	  url:"${ctx}/system/config/notExistCode",
		          	        	  dataType:'json'
		          	          }
		                       },
				            "name":{required:true},
				            "value":{required:true}
				          };
			var messages = {
					"code": {required:"配置代码不能为空",remote: "配置名称已存在，请重新输入！"},
		            "name":{required:"配置名称不能为空"},
                    "value":{required:"配置值不能为空"}
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
			<li><a href="${ctx}/system/config/view">配置管理</a></li>
			<li class="active">添加配置</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
    <div class="row">
			<div class="col-xs-10">
	<form:form id="inputForm" modelAttribute="config" action="${ctx}/system/config/save" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/config/view">
		<fieldset>
			<div class="panel panel-info">
			  <div class="panel-heading"><strong><i class="fa fa-cog green"></i> 基本信息</strong></div>
			  <div class="panel-body">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="config.name" />:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="code" class="col-sm-2 control-label"><fmt:message key="config.code" />:</label>
				<div class="col-xs-4">
					<input type="text" id="code" name="code"  class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="value" class="col-sm-2 control-label"><fmt:message key="config.value" />:</label>
				<div class="col-xs-4">
					<input type="text" id="value" name="value" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label"><fmt:message key="description" />:</label>
				<div class="col-xs-6">
				    <textarea rows="3" id="description" name="description" class="form-control"></textarea>
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
