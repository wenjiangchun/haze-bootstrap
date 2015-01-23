org.springframework.validation.BindException<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link type="image/x-icon" href="${ctx}/resources/images/favicon.ico" rel="shortcut icon">
	<title>400 - 用户输入错误</title>
	<link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>

<body>
	<h2>400 - 用户输入错误.${exception}</h2>
</body>
<script type="text/javascript">
</script>
</html>
