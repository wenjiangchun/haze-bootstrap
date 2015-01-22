<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="image/x-icon" href="${ctx}/resources/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/<spring:theme code='bootstrap-css'/>" rel="stylesheet" media="screen">
<link href="${ctx}/resources/bootstrap/font-awesome/css/font-awesome.css" rel="stylesheet" media="screen">
<script src="${ctx}/resources/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="${ctx}/resources/bootstrap/js/bootstrap-contextmenu.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/hazeUtils.js" type="text/javascript"></script>
<link href="${ctx}/resources/styles/aa.css" rel="stylesheet" media="screen">
<script type="text/javascript">
   var ctx = "${ctx}";
</script>
<sitemesh:head />
</head>
<body>
<%@ include file="/WEB-INF/layouts/header.jsp"%>
    <div class="container-fluid">
      <div class="row">
        <%@ include file="/WEB-INF/layouts/left.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <sitemesh:body />
          </div>
        </div>
      </div>
</body>
</html>