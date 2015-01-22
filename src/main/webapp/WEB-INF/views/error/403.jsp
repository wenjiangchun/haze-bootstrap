<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>403 - 用户权限不足</title>
</head>
<body>
	<div class="http-error">
        <h1>Error!</h1>
        <p class="info">You don't have permission to view this page.</p>
        <p><i class="icon-home"></i></p>
        <p><a href="${ctx}">Back to the home page</a></p>
    </div>
</body>
</html>
