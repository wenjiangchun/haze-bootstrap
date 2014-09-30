<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%response.setStatus(200);%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>404 - 页面不存在</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link type="image/x-icon" href="${ctx}/resources/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${ctx}/resources/styles/default.css" type="text/css" rel="stylesheet" />
</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="http-error">
				<h1>出错了!</h1>
				<p class="info">当前页不存在或已删除</p>
				<p>
					<i class="icon-home"></i>
				</p>
				<p>
					<a href="<c:url value="/"/>">返回首页</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>