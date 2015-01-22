<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title><fmt:message key="add" /><fmt:message key="user" /></title>
	<%@ include file="/resources/impForm.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			var rules = {
					"loginName": {required:true,
          	          remote:{
          	        	  method:"post",
          	        	  url:"${ctx}/system/user/isNotExistLoginName/",
          	        	  dataType:'json',
          	        	  data:{
          	                  loginName:function(){return $("#loginName").val();}
          	                } 
          	          }
                       },
           "name":{required:true},
           "email":{email:true}
			};
			var messages = {
				            "loginName": {required:"登录名不能为空",remote: "登录名已存在，请重新输入！"},
				            "name":{required:"用户名不能为空"},
		                    "email":{email:"邮箱格式不正确"}
			};
			initFormValidate("inputForm", rules, messages);
		});
	</script>
</head>
<body>
       <form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/save" method="post" role="form" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label for="loginName" class="col-sm-2 control-label"><fmt:message key="user.loginName" />:</label>
				<div class="col-xs-4">
					<input type="text" id="loginName" name="loginName" class="form-control" placeholder="请输入登录名"/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="user.name" />:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name"  class="form-control" placeholder="请输入用户名"/>
				</div>
			</div>
			<div class="form-group">
                <label for="email" class="col-sm-2 control-label"><fmt:message key="user.email" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="email" name="email" placeholder="请输入邮箱" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label"><fmt:message key="user.mobile" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="mobile" name="mobile" placeholder="请输入手机号码" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label for="tel" class="col-sm-2 control-label"><fmt:message key="user.tel" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="tel" name="tel" placeholder="请输入固定电话" class="form-control"/>
                </div>
            </div>
			<div class="form-group">
				<label for="sex" class="col-sm-2 control-label"><fmt:message key="user.sex" />:</label>
					<c:forEach items="${sexs}" var="sex" varStatus="index">
					   <c:if test="${index.index == 0 }">
						  <label class="radio-inline"><input type="radio" name="sex" value="${sex}" checked="checked"/>${sex.sexName}</label>
					   </c:if>
					   <c:if test="${index.index != 0 }">
                          <label class="radio-inline"><input type="radio" name="sex" value="${sex}"/>${sex.sexName}</label>
                       </c:if>
					</c:forEach>
			</div>
            <c:if test="${fn:length(roleList) > 0}">
                <div class="form-group">
                    <label for="roleIds" class="col-sm-2 control-label"><fmt:message key="role" />:</label>
                    <c:forEach items="${roleList}" var="role">
                        <label class="checkbox"><input type="checkbox" name="roleIds" value="${role.id}"/> ${role.name}</label>
                    </c:forEach>
                </div>
            </c:if>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
					<c:forEach items="${statuss}" var="status" varStatus="index">
					 <c:if test="${index.index == 0 }">
						<label class="radio-inline"><input type="radio" name="status" value="${status}" checked="checked"/>${status.statusName}</label>
					 </c:if>
					 <c:if test="${index.index != 0 }">
                        <label class="radio-inline"><input type="radio" name="status" value="${status}" />${status.statusName}</label>
                     </c:if>
					</c:forEach>
			</div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="group" />:</label>
				<div class="checkbox-inline">
					<select name="group.id">
					   <option></option>
					   <c:forEach items="${groupList}" var="group">
					      <option value="${group.id}">${group.name}</option>
					   </c:forEach>
					</select>
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
