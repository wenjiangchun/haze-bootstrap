<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page import="org.apache.shiro.authc.LockedAccountException " %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>系统登陆</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- Bootstrap -->
    <link href="${ctx}/<spring:theme code='bootstrap-css'/>" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/resources/jquery/jquery.min.js"></script>
    <script src="${ctx}/resources/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-form/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-validation/jquery.validate.min.js"></script>
    <style>
        html {
            background: url(${ctx}/resources/styles/images/loginbg.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

        body {
            padding-top: 50px;
            font-size: 16px;
            font-family: "Open Sans",serif;
            background: transparent;
        }

        h1 {
            font-family: "Abel", Arial, sans-serif;
            font-weight: 400;
            font-size: 40px;
        }

        /* Override B3 .panel adding a subtly transparent background */
        .panel {
            background-color: rgba(255, 255, 255, 0.9);
        }

        .margin-base-vertical {
            margin: 40px 0;
        }

    </style>
    <script>
        function validator() {
            var username = $("#username").val();
            var password = $("#password").val();
            if (username == '' || password == '') {
                alert("用户名或密码不能为空");
                return false;
            }
            return true;
        }
        function reloadValidateCode() {
            $("#validateCodeImg").attr( "src", "validateCode?date = " + new Date()+ Math.floor(Math.random() * 24));
        }
    </script>
</head>
<body>
<%
    String error = (String) request
            .getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
    if (error != null) {
        String errorMessage = "";
        if (error.contains("DisabledAccountException")) {
            errorMessage = "用户已被屏蔽,请登录其他用户";
        } else if (error.contains("UserNotExistException")) {
            errorMessage = "用户名不存在，请重试";
        } else if (error.contains("ExpiredCredentialsException")) {
            errorMessage = "用户已失效，请登录其他用户";
        } else if (error.contains("IncorrectCredentialsException")) {
            errorMessage = "密码错误，请重试";
        } else {
            errorMessage = error;
        }
        out.println("<script>alert('" + errorMessage + "')</script>");
    }
%>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4 panel panel-default">
            <h3 class="margin-base-vertical">系统登陆</h3>
            <form class="margin-base-vertical" action="${ctx}/login"
                  method="post" onsubmit="return validator()">
                <p class="input-group">
                <span class="input-group-addon">用户名</span>
                    <input type="text" id="username" name="username" value="" class="form-control input-lg"
                           placeholder="请输入用户名"/>
            </p>
                <p class="input-group">
                    <span class="input-group-addon">密&nbsp;&nbsp;码</span>
                    <input type="password" id="password" name="password" value="" class="form-control input-lg"
                           placeholder="请输入密码"/>
                </p>
                <p class="input-group hide">
                    <input type="text"
                           name="validateCode" id="validateCode" class="form-control"
                           style="width: 100px; padding-left: 0px; float: left;"/><span
                        class="help-inline" style="margin-left: 10px; float: left;"><img
                        id="validateCodeImg" src="validateCode" width="80"
                        align="absmiddle"/><a href="javascript:reloadValidateCode();"
                                              class="logininfo btn btn-link">看不请?</a></span>
                </p>
                <p class="help-block text-center"><small>We won't send you spam. Unsubscribe at any time.</small></p>
                <p class="text-center">
                    <button type="submit" class="btn btn-success btn-lg btn-block">登陆</button>
                </p>
                </span>
            </form>

            <div class="margin-base-vertical">
                <small class="text-muted"><a href="http://www.flickr.com/photos/erwlas/6133364748/">Background picture by erwlas @flickr</a>. Used under <a href="http://creativecommons.org/licenses/by/2.0/deed.en">Creative Commons - Attribution</a>.</small>
            </div>

        </div><!-- //main content -->
    </div><!-- //row -->
</div> <!-- //container -->

</body>
</html>
