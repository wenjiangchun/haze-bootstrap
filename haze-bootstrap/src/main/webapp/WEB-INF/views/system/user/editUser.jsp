<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit" /><fmt:message key="user" /></title>
    <%@ include file="/resources/impForm.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {
            var rules = {
                "name":{required:true},
                "email":{email:true}
            };
            var messages = {
                "name":{required:"用户名不能为空"},
                "email":{email:"邮箱格式不正确"}
            };
            initFormValidate("inputForm", rules, messages);
        });
    </script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/update" method="post" role="form" class="form-horizontal">
		<fieldset>
			<div class="form-group">
				<label for="loginName" class="col-sm-2 col-sm-2 control-label"><fmt:message key="user.loginName" />:</label>
				<div class="col-xs-4">
                    <input type="text" id="loginName" name="loginName" class="form-control" value="${user.loginName}" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="user.name" />:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name"  class="form-control" value="${user.name}"/>
				</div>
			</div>
			<div class="form-group">
                <label for="email" class="col-sm-2 control-label"><fmt:message key="user.email" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="email" name="email" class="form-control" value="${user.email}"/>
                </div>
            </div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="user.sex" />:</label>
				<div class="col-xs-4">
					<c:forEach items="${sexs}" var="sex">
					    <c:if test="${(sex == user.sex) }">
					         <label class="radio-inline"><input type="radio" name="sex" value="${sex}" checked="checked"/>${sex.sexName}</label>
					    </c:if>
						<c:if test="${! (sex == user.sex) }">
                             <label class="radio-inline"><input type="radio" name="sex" value="${sex}"/>${sex.sexName}</label>
                        </c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
				<div class="col-xs-4">
					<c:forEach items="${statuss}" var="status">
					  <c:if test="${(status == user.status) }">
					     <label class="radio-inline"><input type="radio" name="status" value="${status}" checked="checked"/>${status.statusName}</label>
					  </c:if>
					  <c:if test="${!(status == user.status) }">
                         <label class="radio-inline"><input type="radio" name="status" value="${status}"/>${status.statusName}</label>
                      </c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> 提交</button>
                <button type="reset" class="btn btn-danger">重置</button>
			   </div>
                </div>
			<input type="hidden" name="id" value="${user.id }">
		</fieldset>
	</form:form>
</body>
</html>
