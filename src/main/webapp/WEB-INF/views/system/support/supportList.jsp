<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>系统支持</title>
   <script type="text/javascript">
	$(document).ready(function() {
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

    <button type="button" class="btn btn-danger" id="refreshBtn">
        更新菜单
    </button>
	
</body>
</html>
