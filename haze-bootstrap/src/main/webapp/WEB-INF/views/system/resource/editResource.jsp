<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit"/><fmt:message key="resource"/></title>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-validation/jquery.validate.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$.validator.addMethod("letterswith", function(value, element) {
			return this.optional(element) || /^[a-z\-:[]()'"\s]+$/i.test(value);
		}, "Letters or punctuation only please");
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
                 "name":{required:true}
	        },
	        messages: {   
	            "name":{required:"资源名称不能为空"}
	        }
	    });
		
	});
	</script>
</head>
<body>
	<ul class="breadcrumb">
        <li><a href="${ctx}"><i class="icon-home"></i> <fmt:message key="home"/></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="system" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="resource" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li class="active"><fmt:message key="edit" /><fmt:message key="resource" /></li>
    </ul>
	<form:form id="inputForm" modelAttribute="resource" action="${ctx}/system/resource/update" method="post" class="form-horizontal">
		<fieldset>
			<legend><fmt:message key="edit"/><fmt:message key="resource"/></legend>
			<div class="alert alert-info">
              <div>
                   <strong>1.资源类型说明：</strong>系统资源分为菜单资源和操作资源，菜单资源通常是模块资源，如用户管理，而操作资源通常对应菜单资源下面操作按钮，如添加，修改，删除用户操作等
              </div>
             <div>
                   <strong>2.资源权限说明：</strong>资源权限对应系统访问权限，如果为空说明该资源访问公开，对应写法为Shiro权限写法如：模块:菜单：资源（如用户管理为system:user:view 添加用户为system:user:add）
              </div>
            </div>
			<div class="control-group">
				<label for="name" class="control-label"><fmt:message key="resource.name"/>:</label>
				<div class="controls">
					<input type="text" id="name" name="name" class="input-large required" value="${resource.name}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="permission" class="control-label"><fmt:message key="resource.permission"/>:</label>
				<div class="controls">
				   <input type="text" id="permission" name="permission"  class="input-large" value="${resource.permission}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="url" class="control-label"><fmt:message key="resource.url"/>:</label>
				<div class="controls">
					<input type="text" id="url" name="url" class="input-large" placeholder="" value="${resource.url}"/>
				</div>
			</div>
			<div class="control-group">
                <label for="parent.id" class="control-label">上级资源:</label>
                <div class="controls">
                    <select name="parent.id">
                        <option></option>
                        <c:forEach items="${menuResources}" var="menu">
                            <c:if test="${resource.parent.id == menu.id }">
                                <option value="${menu.id}" selected="selected">${menu.name}</option>
                            </c:if>
                            <c:if test="${resource.parent.id != menu.id }">
                                <option value="${menu.id}">${menu.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
			<div class="control-group">
				<label for="resourceType" class="control-label"><fmt:message key="resource.resourceType"/>:</label>
				<div class="controls">
					<c:forEach items="${resourceTypes}" var="resourceType" varStatus="index">
                           <c:if test="${resourceType == resource.resourceType }">
                             <label class="radio inline">
                               <input type="radio" name="resourceType" value="${resourceType}" checked="checked"/>${resourceType.typeName}
                             </label>
                           </c:if>
                           <c:if test="${resourceType != resource.resourceType }">
                             <label class="radio inline">
                               <input type="radio" name="resourceType" value="${resourceType}"/>${resourceType.typeName}
                             </label>
                           </c:if>
                    </c:forEach>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel"/>" onclick="history.back()"/>
			</div>
			<input type="hidden" name="id" value="${resource.id}"/>
		</fieldset>
	</form:form>
</body>
</html>
