<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="role"/></title>
	<%@ include file="/resources/impForm.jsp"%> 
	<script type="text/javascript">
	$(document).ready(function() {
		$.validator.addMethod("letterswith", function(value, element) {
			return this.optional(element) || /^[a-z\-.,()'"\s]+$/i.test(value);
		}, "Letters or punctuation only please");
		var rules = {
	            "roleName": { 
      	          required:true,
      	          letterswith:true,
      	          remote:{
      	        	  method:"post",
      	        	  url:"${ctx}/system/role/notExistRoleName/",
      	        	  dataType:'json',
      	        	  data:{
      	                  loginName:function(){return $("#roleName").val();}
      	                } 
      	          }
                   },
                "name":{required:true}
          };
	   var messages = {
		   "roleName": {required:"英文名不能为空",remote: "英文名已存在，请重新输入！",letterswith:"请输入英文字母"},
           "name":{required:"角色名不能为空"}   
	   };
	   initFormValidate("inputForm", rules, messages);
	});
	</script>
</head>

<body>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/system/role/save" method="post" role="form" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label for="roleName" class="col-sm-2 control-label"><fmt:message key="role.roleName"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="roleName" name="roleName"  class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="role.name"/>:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="status"/>:</label>
				<div class="col-xs-4">
					<c:forEach items="${statuss}" var="status" varStatus="index">
					       <c:if test="${index.index == 0 }">
					         <label class="radio-inline">
					           <input type="radio" name="status" value="${status}" checked="checked"/>${status.statusName}
					         </label>
					       </c:if>
					       <c:if test="${index.index != 0 }">
				             <label class="radio-inline">
				               <input type="radio" name="status" value="${status}"/>${status.statusName}
				             </label>
					       </c:if>
					</c:forEach>
				</div>
			</div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> <fmt:message key="submit"/></button>
                    <button type="reset" class="btn btn-danger"><fmt:message key="reset"/></button>
                </div>
            </div>
		</fieldset>
	</form:form>
</body>
</html>
