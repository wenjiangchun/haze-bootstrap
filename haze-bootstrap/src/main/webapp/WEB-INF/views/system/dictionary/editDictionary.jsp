<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit"/><fmt:message key="dictionary"/></title>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-form/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-validation/dist/jquery.validate.js"></script>
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
	                 "code": {required:true,
           	          remote:{
           	        	  method:"post",
           	        	  url:"${ctx}/system/dictionary/isNotExistCode/",
           	        	  dataType:'json',
           	        	  data:{
           	                  "code":function(){return $("#code").val().trim()+","+$("#parentID").val().trim();}
           	                } 
           	          }
                        },
		        },
		        messages: {   
		            "name":{required:"字典名称不能为空"},
		            "code":{required:"字典代码不能为空",remote:"字典代码在该分类下已存在"},
		        }
		    });
		});
	</script>
</head>

<body>
    <ul class="breadcrumb">
        <li><a href="${ctx}"><i class="icon-home"></i><fmt:message key="home"/></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="system" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li><a href="${ctx}/system/dictionary/view/"><fmt:message key="dictionary" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li class="active"><fmt:message key="edit" /><fmt:message key="dictionary" /></li>
    </ul>
	<form:form id="inputForm" modelAttribute="dictionary" action="${ctx}/system/dictionary/update/" method="post" class="form-horizontal">
		<fieldset>
			<legend><fmt:message key="edit" /><fmt:message key="dictionary" /></legend>
			<div class="control-group">
				<label for="name" class="control-label"><fmt:message key="dictionary.name"/>:</label>
				<div class="controls">
					<input type="text" id="name" name="name" class="input-large" value="${dictionary.name}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="code" class="control-label"><fmt:message key="dictionary.code"/>:</label>
				<div class="controls">
					<input type="text" class="input-large" value="${dictionary.code}" disabled/>
					<input type="hidden" id="code" name="code" class="input-large" value="${dictionary.code}" />
				</div>
			</div>
			
			<div class="control-group">
				<label for="pname" class="control-label">字典分类:</label>
				<div class="controls">
					<c:if test="${dictionary.parent != null }">
						<input type="text" id="pname" name="pname" class="input-large" value="${dictionary.parent.name}" disabled/>
						<input type="hidden" id="parentCode" name="parent.code" class="input-large" value="${parent.code}"/>
						<input type="hidden" id="parentID" name="parent.id" value="${dictionary.parent.id}"/>
					</c:if>
					<c:if test="${dictionary.parent == null }">
						<input type="text" id="pname" name="pname" class="input-large" value="" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="" disabled/>
					</c:if>
				</div>
			</div>
			<div class="control-group">
				<label for="isEnabled" class="control-label"><fmt:message key="status" />:</label>
				<div class="controls">
				    <c:if test="${dictionary.isEnabled}">
				       <label class="radio inline"><input type="radio" name="isEnabled" value="true" checked="checked"/>启用</label>
					   <label class="radio inline"><input type="radio" name="isEnabled" value="false"/>禁用</label>
				    </c:if>
					 <c:if test="${!dictionary.isEnabled}">
				       <label class="radio inline"><input type="radio" name="isEnabled" value="true"/>启用</label>
					   <label class="radio inline"><input type="radio" name="isEnabled" value="false" checked="checked"/>禁用</label>
				    </c:if>
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label"><fmt:message key="description"/>:</label>
				<div class="controls">
				    <textarea rows="3" id="description" name="description">${dictionary.description}</textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit"/>"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel"/>" onclick="history.back()"/>
				<input type="hidden"  name="id" value="${dictionary.id}"/>
			</div>
		</fieldset>
	</form:form>
</body>
</html>
