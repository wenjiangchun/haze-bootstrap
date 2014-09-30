<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="dictionary"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
		$(document).ready(function() {
		        var rules = {
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
		        };
		        var messages= {   
		            "name":{required:"字典名称不能为空"},
		            "code":{required:"字典代码不能为空",remote:"字典代码在该分类下已存在"},
		        };
			initFormValidate("inputForm", rules, messages);
		});
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="dictionary" action="${ctx}/system/dictionary/save/" method="post" role="form" class="form-horizontal">
		<fieldset>
			<legend><fmt:message key="add" /><fmt:message key="dictionary" /></legend>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="dictionary.name"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="code" class="col-sm-2 control-label"><fmt:message key="dictionary.code"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="code" name="code" class="form-control"/>
				</div>
			</div>
			
			<div class="form-group">
				<label for="pname" class="col-sm-2 control-label">字典分类:</label>
				<div class="col-xs-4">
					<c:if test="${parent != null }">
						<input type="text" id="pname" name="pname" class="form-control" value="${parent.name}" disabled/>
						<input type="hidden" id="parentCode" name="parent.code" class="input-large" value="${parent.code}"/>
						<input type="hidden" id="parentID" name="parent.id" value="${parent.id}"/>
					</c:if>
					<c:if test="${parent == null }">
						<input type="text" id="pname" name="pname" class="form-control" value="" disabled/>
						<input type="hidden" id="parentID" name="parent.id" value="" disabled/>
					</c:if>
					</div>
			</div>
			
			<div class="form-group">
				<label for="isEnabled" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
					<label class="radio-inline"><input type="radio" name="isEnabled" value="true" checked="checked"/>启用</label>
					<label class="radio-inline"><input type="radio" name="isEnabled" value="false"/>禁用</label>
			</div>
			<div class="form-group">
				<label for="description" class="col-sm-2 control-label"><fmt:message key="description"/>:</label>
				<div class="col-xs-4">
				    <textarea rows="3" id="description" name="description"></textarea>
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
</body>
</html>
