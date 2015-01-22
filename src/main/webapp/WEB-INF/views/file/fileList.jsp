<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/resources/impTagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title>文件管理</title>
    <script type="text/javascript">
    var parentPath = null;
        var fileName = null;
        var currentPath = null;
        $(document).ready(function() {
        });

        function openChildrenFilePath(path) {
            //parent.showProgressMessage();
            $.ajax({
                method:"POST",
                url:"${ctx}/file/getSubFile?path="+path,
                dataType:"json",
                success:function(data) {
                   // parent.closeProcessMessage();
                    getResult(data,path);
                }
            });
        }
        function getResult(data, path) {
            if (data != null) {
                $("#file_div").html("");
                var width = $("#file_div").width();
                var n = Math.round(width / 100);
                $("#tt2").html("当前位置：<a href='#' id='parentPath_A'>" + data[0].parentPath + "</a>");
                var html = "";
                $(data).each(function(index,fileEntity) {
                    var left = ((Math.floor(index % n / n)+ index % n) * 100 + 20)+ "px";
                    var top = (Math.floor(index / n) * 100 + 120) + "px";
                    fileEntity.realName = fileEntity.name;
                    var fileIcon = fileEntity.contentType;
                    if (fileEntity.fileType == "DIRECTORY") {
                        fileEntity.name = "<div style='position:absolute;width: 100px;height: 100px;left:"+left+";top:"+top+"'><a href='javascript:void(0)' style='width:100px' onclick='openChildrenFilePath(\"" + fileEntity.absolutePath + "\")' parentPath='" + fileEntity.parentPath + "' fileName='" + fileEntity.name + "' path='"+fileEntity.absolutePath+"'><img src='${ctx}/resources/styles/images/32X32/"+fileIcon+".png'/></br>" + fileEntity.name +"</a></div>";
                    } else {
                        fileEntity.name = "<div style='position:absolute;width: 100px;height: 100px;left:"+left+";top:"+top+"'><a href='javascript:void(0)' style='width:100px' parentPath='" + fileEntity.parentPath + "' fileName='" + fileEntity.name + "' path='"+fileEntity.absolutePath+"'><img src='${ctx}/resources/styles/images/32X32/" + fileIcon + ".png'/></br>" + fileEntity.name+"</a></div>";
                    }
                    if (fileEntity.hidden) {
                        fileEntity.hidden = "是";
                    } else {
                        fileEntity.hidden = "否";
                    }
                    html += fileEntity.name;

                });
                $("#file_div").html(html);
                $("#file_div").find("div").find("a").contextmenu({
                    target: "#context-menu",
                    before: function (e, element, target) {
                        e.preventDefault();
                        parentPath = $(element).attr("parentPath");
                        fileName = $(element).attr("fileName");
                        currentPath = $(element).attr("path");
                        if (e.target.tagName == 'DIV') {
                            e.preventDefault();
                            this.closemenu();
                            return false;
                        }
                        return true;
                    }
                });
                /*$("#file_div").css({"overflow-y":"auto","scroll":"true"});
                $("#file_div").find("div").bind('contextmenu',function(e){
                        e.preventDefault();
                        $('#mm').menu('show', {
                            left: e.pageX,
                            top: e.pageY
                        });
                        parentPath = $(this).find("a").attr("parentPath");
                        fileName = $(this).find("a").attr("fileName");
                        currentPath = $(this).find("a").attr("path");
                });*/
            }
        }

        function openParentFilePath() {
           // parent.showProgressMessage();
            var parentPath = $("#parentPath_A").text();
            $.ajax({
                method:"POST",
                url:"${ctx}/file/getParentFile?path="+parentPath,
                dataType:"json",
                success:function(data) {
                    //parent.closeProcessMessage();
                    getResult(data, parentPath);
                },
                error:function(data) {
                    //parent.closeProcessMessage();
                }
            });
        }

        function refreshDirectory() {
            var parentPath_A = $("#parentPath_A").text();
            openChildrenFilePath(parentPath_A);
        }
        function openCreateDirectory() {
            var parentPath_A = $("#parentPath_A").text();
            var url = "${ctx}/file/addCreateDirectory?parentPath="+parentPath_A;
            parent.openWindow(url, "新建文件夹",350,150,refreshCallBack);
        }
        function refreshCallBack(data) {
            if ( data != null) {
                parent.showMessageTop(data.content);
            }
        }

        function downLoadPath() {
            window.location.href="${ctx}/file/downLoadPath?parentPath="+parentPath+"&fileName="+fileName;
        }

        function renamePath() {
            $.messager.prompt('文件重命名', '请输入新的文件名称', function(r){
                if (r){
                    parent.showProgressMessage();
                    var url = "${ctx}/file/renamePath?path=" + currentPath + "&newFileName=" + r ;
                    $.ajax({
                        method:"post",
                        url:url,
                        dataType:"json",
                        success:function(data) {
                            parent.closeProcessMessage();
                            refreshCallBack(data);
                            refreshDirectory();
                        }
                    });
                }
            });
        }

        function deletePath() {
            $.messager.confirm('提示', '确认删除?', function(r){
                if (r) {
                    parent.showProgressMessage();
                    var url = "${ctx}/file/deletePath?path=" + currentPath;
                    $.ajax({
                        method:"post",
                        url:url,
                        dataType:"json",
                        success:function(data) {
                            parent.closeProcessMessage();
                            refreshCallBack(data);
                            refreshDirectory();
                        }
                    });
                }
            });
        }

        function showInfoPath() {
        }

        function previewPath() {
            $('#preview_win').html("");
            var width = $('#preview_win').width() *0.95;
            var height = $('#preview_win').height() -10;
            var url = "${ctx}/file/previewPath?path=" + currentPath;
            var $previewIframe = $("<iframe src='"+url+"' width='"+width+"' height='"+height+"' frameborder='0'</iframe>");
            $('#preview_win').html($previewIframe).window({title:currentPath}).window('open');
        }

        function previewResize(w,h) {
            $('#preview_win').find("iframe").css({width:w*0.95,height:h-10});
        }

       function uploadFile() {
           var parentPath_A = $("#parentPath_A").text();
           var url = "${ctx}/file/upload?parentPath="+parentPath_A;
           parent.openWindow(url, "上传文件",800,400,refreshCallBack);
       }

    </script>
</head>
<body >

<ol class="breadcrumb">
    <li><a href="#">主页</a></li>
    <li><a href="#">文件管理</a></li>
    <li class="active">我的电脑</li>
</ol>

    <div class="col-md-3">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-windows"></i> 设备信息</h3>
            </div>
            <div class="panel-body">
                <c:forEach items="${pathList}" var="file">
                    <a href="javascript:void(0)" onclick="openChildrenFilePath('${file}')"><image src="${ctx}/resources/styles/images/64X64/HD-64.png"></image></br>${file}</a>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="col-md-9">
        <div id="tt2" class="alert alert-info" role="alert" style="font-weight: bold"></div>
        <div class="btn-group" id="tt1">
            <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    操作
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="#">重命名</a></li>
                    <li><a href="#"><i class="fa fa-scissors"></i> 剪切</a></li>
                </ul>
            </div>
            <button type="button" class="btn btn-default" onclick="openParentFilePath()"><i class="fa fa-arrow-circle-up"></i> 上一级</button>
            <button type="button" class="btn btn-default" onclick="refreshDirectory()"><i class="fa fa-refresh"></i> 刷新</button>
            <button type="button" class="btn btn-default"><i class="fa fa-info-circle"></i> 属性</button>
        </div>
                <div id="file_div"></div>
                </div>
    </div>
    <div id="context-menu">
        <ul class="dropdown-menu" role="menu">
            <li><a tabindex="-1" href="#" onclick="renamePath()"><i class="fa fa-files-o"></i> 重命名</a></li>
            <li><a tabindex="-1" href="#" onclick="downLoadPath()"><i class="fa fa-download"></i> 下载</a></li>
            <li><a tabindex="-1" href="#" onclick="deletePath()"><i class="fa fa-times"></i> 删除</a></li>

            <li><a tabindex="-1" href="#" onclick="previewPath()"><i class="fa fa-eye"></i> 预览</a></li>
            <li role="presentation" class="divider"></li>
            <li><a tabindex="-1" href="#" onclick="showInfoPath()"><i class="fa fa-info-circle"></i> 属性</a></li>
        </ul>
    </div>
</body>
</html>
