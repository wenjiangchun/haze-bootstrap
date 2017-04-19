<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
	<title><sitemesh:write property='title' /></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<link rel="shortcut icon" type="image/x-icon" href="${ctx}/resources/styles/images/favicon.png" />
	<link href="${ctx}/resources/ace/assets/css/bootstrap.css" rel="stylesheet" />
	<link rel="stylesheet" href="${ctx}/resources/ace/assets/css/font-awesome.css" />
	<link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace-fonts.css" />
	<link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	<!--[if lte IE 9]>
	<link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace-part2.css" class="ace-main-stylesheet" />
	<![endif]-->
	<!--[if lte IE 9]>
	<link rel="stylesheet" href="${ctx}/resources/ace/assets/css/ace-ie.css" />
	<![endif]-->
	<!-- ace settings handler -->
	<script src="${ctx}/resources/ace/assets/js/ace-extra.js"></script>
	<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
	<!--[if lte IE 8]>
	<script src="${ctx}/resources/ace/assets/js/html5shiv.js"></script>
	<script src="${ctx}/resources/ace/assets/js/respond.js"></script>
	<![endif]-->
	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${ctx}/resources/ace/assets/js/jquery.js'>"+"<"+"/script>");
	</script>
	<!-- <![endif]-->

	<!--[if IE]>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${ctx}/resources/ace/assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/resources/ace/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
	</script>
	<script src="${ctx}/resources/ace/assets/js/bootstrap.js"></script>
	<!-- ace scripts -->
	<script src="${ctx}/resources/ace/assets/js/ace/ace.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.sidebar.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.submenu-hover.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.widget-box.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.settings.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.settings-rtl.js"></script>
	<script src="${ctx}/resources/ace/assets/js/ace/ace.settings-skin.js"></script>
	<sitemesh:write property='head' />
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<%@ include file="/WEB-INF/layouts/header.jsp"%>
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>

	<!-- #section:basics/sidebar -->
	<%@ include file="/WEB-INF/layouts/left.jsp"%>

	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<!-- #section:basics/content.breadcrumbs -->
			<sitemesh:write property='body' />
			<!-- /section:basics/content.breadcrumbs -->
		</div>
	</div><!-- /.main-content -->
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
		<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
</div><!-- /.main-container -->
</body>
</html>
