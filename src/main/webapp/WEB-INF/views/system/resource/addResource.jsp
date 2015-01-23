<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="resource"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
	$(document).ready(function() {
		initMenu("viewResource_Menu");
		
		$.validator.addMethod("letterswith", function(value, element) {
			return this.optional(element) || /^[a-z\-:[]()'"\s]+$/i.test(value);
		}, "Letters or punctuation only please");
		var rules =  {
                "name":{required:true}
        };
		var messages = {   
	            "name":{required:"资源名称不能为空"}
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
			<li><a href="${ctx}/system/resource/view">资源管理</a></li>
			<li class="active">添加资源</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	
	<div class="page-content">
	    <div class="row">
			<div class="col-xs-10">
			      <form:form id="inputForm" modelAttribute="role" action="${ctx}/system/resource/save" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/resource/view">
					<fieldset>
						 <div class="panel panel-info">
						    <div class="panel-heading"><strong><i class="fa fa-user green"></i> 基本信息</strong></div>
						   <div class="panel-body">
						     <div class="alert alert-info">
					              <div>
					                   <strong>1.资源类型说明：</strong>系统资源分为菜单资源和操作资源，菜单资源通常是模块资源，如用户管理，而操作资源通常对应菜单资源下面操作按钮，如添加，修改，删除用户操作等
					              </div>
					             <div>
					                   <strong>2.资源权限说明：</strong>资源权限对应系统访问权限，如果为空说明该资源访问公开，对应写法为Shiro权限写法如：模块:菜单：资源（如用户管理为system:user:view 添加用户为system:user:add）
					              </div>
				              </div>
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label"><fmt:message key="resource.name"/>:</label>
								<div class="col-xs-4">
									<input type="text" id="name" name="name" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="permission" class="col-sm-2 control-label"><fmt:message key="resource.permission"/>:</label>
								<div class="col-xs-4">
								   <input type="text" id="permission" name="permission"  class="form-control" value="anon"/>
								</div>
							</div>
							<div class="form-group">
								<label for="url" class="col-sm-2 control-label"><fmt:message key="resource.url"/>:</label>
								<div class="col-xs-4">
									<input type="text" id="url" name="url" class="form-control" placeholder=""/>
								</div>
							</div>
							<div class="form-group">
				                <label for="parent.id" class="col-sm-2 control-label">上级资源:</label>
				                <div class="col-xs-2">
				                    <select name="parent.id" class="form-control">
				                    	<option></option>
				                        <c:forEach items="${resources}" var="resource">
				                            <option value="${resource.id}">${resource.name}</option>
				                        </c:forEach>
				                    </select>
				                </div>
				            </div>
							<div class="form-group">
								<label for="resourceType" class="col-sm-2 control-label"><fmt:message key="resource.resourceType"/>:</label>
								<c:forEach items="${resourceTypes}" var="resourceType" varStatus="index">
				                          <c:if test="${index.index == 0 }">
				                            <label class="radio-inline">
				                              <input type="radio" name="resourceType" value="${resourceType}" checked="checked"/>${resourceType.typeName}
				                            </label>
				                          </c:if>
				                          <c:if test="${index.index != 0 }">
				                            <label class="radio-inline">
				                              <input type="radio" name="resourceType" value="${resourceType}"/>${resourceType.typeName}
				                            </label>
				                          </c:if>
				                </c:forEach>
							</div>
			              </div>
			            </div>
						<div class="form-group">
				            <div class="col-sm-offset-2 col-sm-10">
								<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>"/>&nbsp;	
							    <input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel"/>" onclick="history.back()"/>
							</div>
						</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
