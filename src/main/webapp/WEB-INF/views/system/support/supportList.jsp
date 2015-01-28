<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>系统支持</title>
   <script type="text/javascript">
	$(document).ready(function() {
        initMenu("viewSupport_Menu");
        $("#refreshBtn").click(function() {
            $.ajax({
                method:"post",
                url:"${ctx}/system/support/refreshMenus",
                success:function(data){
                    alert(data);
                }
            });
        })
	});
</script>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
    </script>

    <ul class="breadcrumb">
        <li><i class="icon-home home-icon"></i> <a href="#">主页</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">系统辅助</li>
    </ul>
    <!-- .breadcrumb -->
</div>
<div class="page-content">
    <div class="hr hr-18 hr-dotted"></div>
    <button type="button" class="btn btn-danger" id="refreshBtn">
        更新菜单
    </button>
    <div class="hr hr-18 hr-dotted"></div>
    <button type="button" class="btn btn-danger" id="refreshBtn">
        生成二维码
    </button>
	</div>
</body>
</html>
