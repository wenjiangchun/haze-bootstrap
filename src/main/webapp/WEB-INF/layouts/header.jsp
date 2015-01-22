<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Haze</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统菜单<b class="caret"></b></a>
                  <ul class="dropdown-menu">
                      <li><a href="javascript:void(0)">系统管理</a></li>
                      <li><a href="javascript:void(0)">调度管理</a></li>
                      <li><a href="javascript:void(0)">文件管理</a></li>
                      <li><a href="javascript:void(0)">流程管理</a></li>
                  </ul>
              </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">切换样式 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                  <li><a href="javascript:void(0)" class="theme-switch-default" onclick="changeTheme('default')">Default</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('amelia')">Amelia</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('cerulean')">Cerulean</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('cosmo')">Cosmo</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('slate')">Slate</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('united')">United</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('spruce')">spruce</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('simplex')">simplex</a></li>
                  <li><a href="javascript:void(0)" class="theme-switch-amelia" onclick="changeTheme('cyborg')">cyborg</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="language"/> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="javascript:void(0)" onclick="changeLanguage('zh_CN')">中文</a></li>
                <li><a href="javascript:void(0)" onclick="changeLanguage('en_US')">英文</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">


            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li>
                <shiro:guest><a href="${ctx}/login" class="btn btn-link"><fmt:message key="login"/></a></shiro:guest>
                <shiro:user>
                    <a href="#" data-toggle="dropdown" class="btn dropdown-toggle">
                        <i class="icon-user"></i> <shiro:principal property="name"/><input type="hidden" name="shiroUserId" id="shiroUserId" value="<shiro:principal property="userId"/>"/>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#updatePasswordDiv" data-toggle="modal" role="button"><i class="icon-lock"></i> <fmt:message key="change.password"/></a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/logout"><i class="icon-off"></i><fmt:message key="logout"/></a></li>
                    </ul>
                </shiro:user>

            </li>
          </ul>
        </div>
      </div>
    </div>
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">X</span></button>
        <h4 class="modal-title" id="myModalTitle"></h4>
    </div>
    <div class="modal-body">
    </div>
    <%--<div class="modal-footer">
    </div>--%>
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
        var password = $("#password").val();
        $.ajax({
            type:'POST',
            url : ctx + "/system/user/updatePassword",
            dataType:'text',
            data:{"id":userId,"password":password},
            success:function(data) {
                notify("密码修改成功");
                $("#updatePasswordDiv").modal('hide');
            }
        });
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