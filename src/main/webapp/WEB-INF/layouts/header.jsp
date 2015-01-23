<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="navbar" class="navbar navbar-default">
    <script type="text/javascript">
        try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>

    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>

        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <a href="${ctx}" class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    Haze Admin
                </small>
            </a>

            <!-- /section:basics/navbar.layout.brand -->

            <!-- #section:basics/navbar.toggle -->

            <!-- /section:basics/navbar.toggle -->
        </div>

        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="grey">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="ace-icon fa fa-tasks"></i>
                        <span class="badge badge-grey">4</span>
                    </a>

                    <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header">
                            <i class="ace-icon fa fa-check"></i>
                            4 Tasks to complete
                        </li>

                        <li class="dropdown-content">
                            <ul class="dropdown-menu dropdown-navbar">
                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <span class="pull-left">Software Update</span>
                                            <span class="pull-right">65%</span>
                                        </div>

                                        <div class="progress progress-mini">
                                            <div style="width:65%" class="progress-bar"></div>
                                        </div>
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <span class="pull-left">Hardware Upgrade</span>
                                            <span class="pull-right">35%</span>
                                        </div>

                                        <div class="progress progress-mini">
                                            <div style="width:35%" class="progress-bar progress-bar-danger"></div>
                                        </div>
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <span class="pull-left">Unit Testing</span>
                                            <span class="pull-right">15%</span>
                                        </div>

                                        <div class="progress progress-mini">
                                            <div style="width:15%" class="progress-bar progress-bar-warning"></div>
                                        </div>
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
                                            <span class="pull-left">Bug Fixes</span>
                                            <span class="pull-right">90%</span>
                                        </div>

                                        <div class="progress progress-mini progress-striped active">
                                            <div style="width:90%" class="progress-bar progress-bar-success"></div>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown-footer">
                            <a href="#">
                                See tasks with details
                                <i class="ace-icon fa fa-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="purple">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                        <span class="badge badge-important">8</span>
                    </a>

                    <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header">
                            <i class="ace-icon fa fa-exclamation-triangle"></i>
                            8 Notifications
                        </li>

                        <li class="dropdown-content">
                            <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                <li>
                                    <a href="#">
                                        <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
														New Comments
													</span>
                                            <span class="pull-right badge badge-info">+12</span>
                                        </div>
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <i class="btn btn-xs btn-primary fa fa-user"></i>
                                        Bob just signed up as an editor ...
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
														New Orders
													</span>
                                            <span class="pull-right badge badge-success">+8</span>
                                        </div>
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
														Followers
													</span>
                                            <span class="pull-right badge badge-info">+11</span>
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown-footer">
                            <a href="#">
                                See all notifications
                                <i class="ace-icon fa fa-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </li>

                <li class="green">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                        <span class="badge badge-success">5</span>
                    </a>

                    <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header">
                            <i class="ace-icon fa fa-envelope-o"></i>
                            5 Messages
                        </li>

                        <li class="dropdown-content">
                            <ul class="dropdown-menu dropdown-navbar">
                                <li>
                                    <a href="#" class="clearfix">
                                        <img src="../assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Alex:</span>
														Ciao sociis natoque penatibus et auctor ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>a moment ago</span>
													</span>
												</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="clearfix">
                                        <img src="../assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Susan:</span>
														Vestibulum id ligula porta felis euismod ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>20 minutes ago</span>
													</span>
												</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="clearfix">
                                        <img src="../assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Bob:</span>
														Nullam quis risus eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>3:15 pm</span>
													</span>
												</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="clearfix">
                                        <img src="../assets/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Kate:</span>
														Ciao sociis natoque eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>1:33 pm</span>
													</span>
												</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="clearfix">
                                        <img src="../assets/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />
												<span class="msg-body">
													<span class="msg-title">
														<span class="blue">Fred:</span>
														Vestibulum id penatibus et auctor  ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>10:09 am</span>
													</span>
												</span>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="dropdown-footer">
                            <a href="inbox.html">
                                See all messages
                                <i class="ace-icon fa fa-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </li>

                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue">
                    <shiro:guest><a href="${ctx}/login" class="btn btn-link"><fmt:message key="login"/></a></shiro:guest>
                    <shiro:user>
                        <a data-toggle="dropdown" href="#"
                           class="dropdown-toggle"> <img class="nav-user-photo"
                                                         src="${ctx}/resources/assets/avatars/user.jpg"/> <span class="user-info"><small>欢迎,</small>
								<shiro:principal property="name"/><input type="hidden" name="shiroUserId" id="shiroUserId" value="<shiro:principal property="userId"/>"/>
						</span> <i class="ace-icon fa fa-caret-down"></i>
                        </a>
                        <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                            <li>
                                <a href="javascript:void(0);" onclick="showUpdatePasswordModal();">
                                    <i class="ace-icon fa fa-cog" ></i>
                                    Settings
                                </a>
                            </li>

                            <li>
                                <a href="profile.html">
                                    <i class="ace-icon fa fa-user"></i>
                                    Profile
                                </a>
                            </li>

                            <li class="divider"></li>

                            <li>
                                <a href="${ctx}/logout">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    Logout
                                </a>
                            </li>
                        </ul>
                    </shiro:user>
                </li>
                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>

        <!-- /section:basics/navbar.dropdown -->
    </div><!-- /.navbar-container -->
</div>


<div class="modal fade" id="updatePasswordDiv">
    <div class="modal-dialog">
        <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">x</span></button>
        <h4 class="modal-title" id="myModalTitle">修改密码</h4>
    </div>
    <div class="modal-body">
      <div class="form-group">
      <label for="newPassword" class="col-sm-2 control-label">新密码</label>
    <div class="col-xs-4">
      <input type="email" class="form-control" id="newPassword" placeholder="请输入新密码">
    </div>
  </div>
    </div>
    <div class="modal-footer">
       <button type="button" class="btn btn-primary" onclick="updatePassword()"><i class="fa fa-check"></i>确定</button>
    </div>
</div>
        </div>
    </div>
<script type="text/javascript">
    function getDate() {
        var d,s;
        d = new Date();
        s = d.getFullYear() + "年";             //取年份
        s = s + (d.getMonth() + 1) + "月";//取月份
        s += d.getDate() + "日";         //取日期
        s += d.getHours() + ":";       //取小时
        var minutes = d.getMinutes();
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        s += minutes ;    //取分
        //s += d.getSeconds();         //取秒
        $("#currentDate").html(s);
    }
    $(document).ready(function() {
        getDate();
        window.setInterval(getDate, 60000);
    });
    var myModal;
    function changeLanguage(language) {
        $.ajax({
            type: "POST",
            url: ctx + "/changeLanguage",
            data: "language="+language,
            dataType:"text",
            async: true,
            error: function(data, error) {alert("change lang error!");},
            success: function(data){
                window.location.reload();
            }
        });
    }

    function changeTheme(theme) {
        $.ajax({
            type: "POST",
            url: ctx + "/changeTheme",
            data: "theme="+theme,
            dataType:"text",
            async: true,
            error: function(data, error) {alert("change lang error!");},
            success: function(data){
                window.location.reload();
            }
        });
    }
    function updatePassword() {
    	 var userId = $("#shiroUserId").val();
         var password = $("#newPassword").val();
    	if (password == null || password == "" || password == " " || password.length < 6 || password.length > 12) {
    		alert("密码不能为空，并且长度在6-12个字符");
    		return;
    	}
       if (window.confirm("确认修改密码？")) {
    	   $.ajax({
               type:'POST',
               url : ctx + "/system/user/updatePassword",
               dataType:'text',
               data:{"id":userId,"newPassword":password},
               success:function(data) {
                   alert("密码修改成功");
                   $("#updatePasswordDiv").modal('hide');
               }
           });
       }
        
    }
    
    function showUpdatePasswordModal() {
    	 $("#newPassword").val("");
    	$('#updatePasswordDiv').modal({show:true});
    }

    /**
     * 打开MyModal模态窗口,其中URL为远程地址,title为窗口title
     */
    function showMyModal(url, title, action) {
        $.ajaxSetup ({
            cache: false //close AJAX cache
        });
        $('#myModal').removeData("bs.modal");
        if (url != null && url != "") {
            if (url.indexOf("?") != -1) {
                url += "&";
            } else {
                url += "?";
            }
            url += "window=modal";
            $('#myModalTitle').text(title);
            $("#myModal .modal-body").html("");
            $("#myModal .modal-body").ajaxStart(function(){
                $(this).html("加载中...");
            });
           //var html = "<iframe class='embed-responsive-item' src='"+url+"'></iframe>";
            myModal = $('#myModal').modal({show:true,remote:url});
            myModal.callBack = action;
            //$("#modalIframe").attr("src",url);
           // $("#myModal .modal-body").addClass('embed-responsive embed-responsive-16by9').html(html);
           // $('#myModal').modal({show:true});
            return;
        }
    }

    /**
     * 关闭MyModal模态窗口
     */
    function hideMyModal() {
        if(myModal.callBack != null) {
            myModal.callBack.apply(this, arguments);
        }
        $('#myModal').modal('hide');
    }

    function notify(title,message,type) {
        /**
         if (type == null) {
    		  type = "bangTidy";
    	  }
         $('.notifications').notify({
    		    message: { text: message },
    		    type:type
    	}).show();*/
        if (type == null) {
            type = "success";
        }
        //s$.pnotify.defaults.delay -= 500;
        $.pnotify({
            delay : 2000,
            title : title,
            text : message,
            type : type
        });
    }

    function setWinHeight(obj) {
        return;
        var win = obj;
        if (document.getElementById) {
            if (win && !window.opera) {
                if (win.contentDocument
                        && win.contentDocument.body.offsetHeight)
                    win.height = win.contentDocument.body.offsetHeight + 20;
                else if (win.Document && win.Document.body.scrollHeight)
                    win.height = win.Document.body.scrollHeight + 20 ;
            }
        }
    }
/**
    var loader = $('<div id="loader">loading...</div>')
            .css({
                position: "relative",
                top: "10em",
                left: "10em",
                display: "inline"
            }).appendTo("body").hide();
    $(document).ajaxStart(function() {
        loader.show();
    }).ajaxStop(function() {
        loader.hide();
    }).ajaxError(function(a, b, e) {
        throw e;
    });
    **/

</script>