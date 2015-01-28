<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>二维码演示</title>
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
        <li><a href="#">Demo演示</a></li>
        <li class="active">二维码</li>
    </ul>
    <!-- .breadcrumb -->
</div>
<div class="page-content">
    <div class="hr hr-18 hr-dotted"></div>
    输入二维码内容<input id="text" class="form-control"/>
    <div class="hr hr-18 hr-dotted"></div>
    <button class="btn btn-success btn-large" id="create_btn">点击生成</button>
    <div class="hr hr-18 hr-dotted"></div>
    <div class="row">
        <div class="col-xs-6 col-md-3">
            <a href="#" class="thumbnail">
                <img data-holder-rendered="true" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDIwMCAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjczLjUiIHk9IjEwMCIgc3R5bGU9ImZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxMHB0O2RvbWluYW50LWJhc2VsaW5lOmNlbnRyYWwiPjIwMHgyMDA8L3RleHQ+PC9nPjwvc3ZnPg==" style="width: 200px; height: 200px;" data-src="holder.js/200x200" class="img-rounded" alt="200x200" id="preview_img"/>
            </a>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        initMenu("viewBarcode_Menu");
        $("#create_btn").click(function() {
            var text = $("#text").val();
            $("#preview_img").attr({src:""});
            $("#preview_img").attr({src:"${ctx}/demo/showMatrix?text="+text});
        });
    });
</script>
</body>
</html>
