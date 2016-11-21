<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page import="org.apache.shiro.authc.LockedAccountException " %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <head>
    <title>系统登陆</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="${ctx}/resources/styles/images/favicon.png" />
    <link href="${ctx}/<spring:theme code='bootstrap-css'/>" type="text/css" rel="stylesheet"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
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
    <body class="login-layout">
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
    <div class="main-container">
        <div class="main-content">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="login-container">
                        <div class="center">
                            <h1>
                                <i class="ace-icon fa fa-leaf green"></i>
                                <span class="red">Haze</span>
                                <span class="white" id="id-text2">Application</span>
                            </h1>
                            <h4 class="blue" id="id-company-text">&copy; Company Name</h4>
                        </div>

                        <div class="space-6"></div>

                        <div class="position-relative">
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-coffee green"></i>
                                            请登录
                                        </h4>

                                        <div class="space-6"></div>

                                        <form action="${ctx}/login" method="post" onsubmit="return validator()">
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" id="username" name="username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" id="password" name="password"/>
															<i class="ace-icon fa fa-lock"></i>
                                                            <img src="${ctx}/validateCode" id="validatecode"/>
                                                            <button onclick="refreshCode()" type="button">刷新</button>
														</span>
                                                </label>

                                                <div class="space"></div>

                                                <div class="clearfix">
                                                    <label class="inline">
                                                        <input type="checkbox" class="ace" />
                                                        <span class="lbl"> 记住我</span>
                                                    </label>

                                                    <button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
                                                        <i class="ace-icon fa fa-key"></i>
                                                        <span class="bigger-110">登录</span>
                                                    </button>
                                                </div>

                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form>

                                        <div class="social-or-login center">
                                            <span class="bigger-110">Or Login Using</span>
                                        </div>

                                        <div class="space-6"></div>

                                        <div class="social-login center">
                                            <a class="btn btn-primary">
                                                <i class="ace-icon fa fa-qq"></i>
                                            </a>

                                            <a class="btn btn-info">
                                                <i class="ace-icon fa fa-twitter"></i>
                                            </a>

                                            <a class="btn btn-danger">
                                                <i class="ace-icon fa fa-google-plus"></i>
                                            </a>
                                        </div>
                                    </div><!-- /.widget-main -->

                                    <div class="toolbar clearfix">
                                        <div>
                                            <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                <i class="ace-icon fa fa-arrow-left"></i>
                                                I forgot my password
                                            </a>
                                        </div>

                                        <div>
                                            <a href="#" data-target="#signup-box" class="user-signup-link">
                                                I want to register
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.login-box -->

                            <div id="forgot-box" class="forgot-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header red lighter bigger">
                                            <i class="ace-icon fa fa-key"></i>
                                            Retrieve Password
                                        </h4>

                                        <div class="space-6"></div>
                                        <p>
                                            Enter your email and to receive instructions
                                        </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
                                                </label>

                                                <div class="clearfix">
                                                    <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                        <i class="ace-icon fa fa-lightbulb-o"></i>
                                                        <span class="bigger-110">Send Me!</span>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div><!-- /.widget-main -->

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            Back to login
                                            <i class="ace-icon fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.forgot-box -->

                            <div id="signup-box" class="signup-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header green lighter bigger">
                                            <i class="ace-icon fa fa-users blue"></i>
                                            New User Registration
                                        </h4>

                                        <div class="space-6"></div>
                                        <p> Enter your details to begin: </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
                                                </label>

                                                <label class="block">
                                                    <input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
                                                </label>

                                                <div class="space-24"></div>

                                                <div class="clearfix">
                                                    <button type="reset" class="width-30 pull-left btn btn-sm">
                                                        <i class="ace-icon fa fa-refresh"></i>
                                                        <span class="bigger-110">Reset</span>
                                                    </button>

                                                    <button type="button" class="width-65 pull-right btn btn-sm btn-success">
                                                        <span class="bigger-110">Register</span>

                                                        <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            <i class="ace-icon fa fa-arrow-left"></i>
                                            Back to login
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.signup-box -->
                        </div><!-- /.position-relative -->

                        <div class="navbar-fixed-top align-right">
                            <br />
                            &nbsp;
                            <a id="btn-login-dark" href="#">Dark</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-blur" href="#">Blur</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-light" href="#">Light</a>
                            &nbsp; &nbsp; &nbsp;
                        </div>
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container -->


    <!-- /.main-container -->

    <script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${ctx}/resources/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script> 

    <!-- inline scripts related to this page -->

    <script type="text/javascript">
        if('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
    </script>

    <!-- inline scripts related to this page -->
    <script type="text/javascript">
        jQuery(function($) {
            $(document).on('click', '.toolbar a[data-target]', function(e) {
                e.preventDefault();
                var target = $(this).data('target');
                $('.widget-box.visible').removeClass('visible');//hide others
                $(target).addClass('visible');//show target
            });
        });



        //you don't need this, just used for changing background
        jQuery(function($) {
            $('#btn-login-dark').on('click', function(e) {
                $('body').attr('class', 'login-layout');
                $('#id-text2').attr('class', 'white');
                $('#id-company-text').attr('class', 'blue');

                e.preventDefault();
            });
            $('#btn-login-light').on('click', function(e) {
                $('body').attr('class', 'login-layout light-login');
                $('#id-text2').attr('class', 'grey');
                $('#id-company-text').attr('class', 'blue');

                e.preventDefault();
            });
            $('#btn-login-blur').on('click', function(e) {
                $('body').attr('class', 'login-layout blur-login');
                $('#id-text2').attr('class', 'white');
                $('#id-company-text').attr('class', 'light-blue');

                e.preventDefault();
            });
            $('#btn-login-light').click();
        });

        function refreshCode() {
            $("#validatecode").attr('src',"${ctx}/validateCode?t="+Math.random());
        }
    </script>
</body>
</html>
