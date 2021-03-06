<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="edit" /><fmt:message key="user" /></title>
    <%@ include file="/resources/impForm.jsp"%>
    <%@ include file="/resources/impUpload.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {
        	initMenu("viewUser_Menu");
			initUploader();
            var rules = {
                "name":{required:true},
                "email":{email:true},
				"sn":{number:true}
            };
            var messages = {
                "name":{required:"用户名不能为空"},
                "email":{email:"邮箱格式不正确"},
				"sn":{number:"请输入整数"}
            };
            initFormValidate("inputForm", rules, messages);
        });
        
        function submitForm(){
			var directUrl = $("#inputForm").attr("directUrl");
			var selectGroup = $("#groupId").val();
			
			if(selectGroup != ""){
				 $("#inputForm").attr("directUrl",directUrl + "?groupId=" + selectGroup)
			}
			
			return true;
		}

		function initUploader() {
			var uploader = WebUploader.create({
				auto: true,
				swf: '${ctx}/resources/webuploader/Uploader.swf',
				server: '${ctx}/oa/file/upload',
				// 选择文件的按钮。可选。
				// 内部根据当前运行是创建，可能是input元素，也可能是flash.
				pick: '#filePicker',
				// 只允许选择图片文件。
				accept: {
					title: 'Images',
					extensions: 'gif,jpg,jpeg,bmp,png',
					mimeTypes: 'image/*'
				}
				//fileNumLimit:1
			});
			uploader.on( 'fileQueued', function( file ) {
				var $li = $(
								'<div id="' + file.id + '" class="file-item thumbnail">' +
								'<img>' +
								'<div class="info">' + file.name + '</div>' +
								'</div>'
						),
						$img = $li.find('img');
				$("#fileList").empty();
				$("#fileList").append( $li );
				// 创建缩略图
				// 如果为非图片文件，可以不用调用此方法。
				// thumbnailWidth x thumbnailHeight 为 100 x 100
				uploader.makeThumb( file, function( error, src ) {
					if ( error ) {
						$img.replaceWith('<span>不能预览</span>');
						return;
					}
					$img.attr( 'src', src );
				}, 100, 100 );
			});

			uploader.on( 'uploadSuccess', function( file,response ) {
				$( '#'+file.id ).addClass('upload-state-done');
				var responseText = (response._raw || response);
				$("#imgPath").val(responseText);
			});
		}
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
			<li><a href="${ctx}/system/user/view">用户管理</a></li>
			<li class="active">编辑用户</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
    <div class="row">
			<div class="col-xs-10">
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/system/user/update" method="post" role="form" class="form-horizontal" directUrl="${ctx}/system/user/view">
		<fieldset>
			<div class="panel panel-info">
			  <div class="panel-heading"><strong><i class="fa fa-user green"></i> 基本信息</strong></div>
			  <div class="panel-body">
			<div class="form-group">
				<label for="loginName" class="col-sm-2 col-sm-2 control-label"><fmt:message key="user.loginName" />:</label>
				<div class="col-xs-4">
                    <input type="text" id="loginName" name="loginName" class="form-control" value="${user.loginName}" disabled/>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"><fmt:message key="user.name" />:</label>
				<div class="col-xs-4">
					<input type="text" id="name" name="name"  class="form-control" value="${user.name}"/>
				</div>
			</div>
			<div class="form-group">
                <label for="email" class="col-sm-2 control-label"><fmt:message key="user.email" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="email" name="email" class="form-control" value="${user.email}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label"><fmt:message key="user.mobile" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="mobile" name="mobile" placeholder="请输入手机号码" class="form-control" value="${user.mobile}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="tel" class="col-sm-2 control-label"><fmt:message key="user.tel" />:</label>
                <div class="col-xs-4">
                    <input type="text" id="tel" name="tel" placeholder="请输入固定电话" class="form-control" value="${user.tel}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label">住宅电话:</label>
                <div class="col-xs-4">
                    <input type="text" id="hourseTel" name="hourseTel" placeholder="请输入住宅电话" class="form-control" value="${user.hourseTel}"/>
                </div>
            </div>
             <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label">小号:</label>
                <div class="col-xs-4">
                    <input type="text" id="backupTel" name="backupTel" placeholder="请输入小号" class="form-control" value="${user.backupTel}"/>
                </div>
            </div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="user.sex" />:</label>
				<div class="col-xs-4">
					<c:forEach items="${sexs}" var="sex">
					    <c:if test="${(sex == user.sex) }">
					         <label class="radio-inline"><input type="radio" name="sex" value="${sex}" checked="checked"/>${sex.sexName}</label>
					    </c:if>
						<c:if test="${! (sex == user.sex) }">
                             <label class="radio-inline"><input type="radio" name="sex" value="${sex}"/>${sex.sexName}</label>
                        </c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="status" />:</label>
				<div class="col-xs-4">
					<c:forEach items="${statuss}" var="status">
					  <c:if test="${(status == user.status) }">
					     <label class="radio-inline"><input type="radio" name="status" value="${status}" checked="checked"/>${status.statusName}</label>
					  </c:if>
					  <c:if test="${!(status == user.status) }">
                         <label class="radio-inline"><input type="radio" name="status" value="${status}"/>${status.statusName}</label>
                      </c:if>
					</c:forEach>
				</div>
			</div>
			
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label">用户类型:</label>
				<div class="col-xs-4">
					<c:forEach items="${userTypes}" var="userType">
					  <c:if test="${(userType == user.userType) }"> 
					     <label class="radio-inline"><input type="radio" name="userType" value="${userType}" checked="checked"/>${userType.typeName}</label>
					  </c:if>
					  <c:if test="${!(userType == user.userType) }">
                         <label class="radio-inline"><input type="radio" name="userType" value="${userType}"/>${userType.typeName}</label>
                      </c:if>
					</c:forEach>
				</div>
			</div>
				  <div class="form-group">
					  <label for="status" class="col-sm-2 control-label">显示顺序:</label>
					  <div class="col-xs-4">
						  <input type="text" id="sn" name="sn" placeholder="请输入显示顺序" class="form-control" value="${user.sn}"/>
					  </div>
				  </div>
			<div class="form-group">
				<label for="status" class="col-sm-2 control-label"><fmt:message key="group" />:</label>
				<div class="checkbox-inline">
					<select name="group.id" class="form-control" id="groupId">
					   <option></option>
					   <c:forEach items="${groupList}" var="group">
					      <option value="${group.id}" <c:if test="${user.group.id == group.id }">selected</c:if>>${group.fullName}</option>
					   </c:forEach>
					</select>
				</div>
			</div>
				  <div class="form-group">
					  <label for="status" class="col-sm-2 control-label">兼职机构:</label>
					  <div class="checkbox-inline">
						  <select name="bakGroupId" class="form-control">
							  <option></option>
							  <c:forEach items="${groupList}" var="group">
								  <option value="${group.id}" <c:if test="${user.bakGroupId == group.id }">selected</c:if>>${group.fullName}</option>
							  </c:forEach>
						  </select>
					  </div>
				  </div>

				  <div class="form-group">
					  <label class="col-sm-2 control-label">签名图片:</label>
					  <div class="col-sm-8">
						  <div id="uploader-demo">
							  <input type="hidden" name="signaturePath" id="imgPath" required="required" value="${user.signaturePath}"/>
							  <div id="filePicker"><i class="fa fa-photo"></i> 选择图片</div>
							  <span id="fileList" class="">
								  <div class="file-item thumbnail">
									  <img src="${ctx}/oa/file/previewPath?path=${user.signaturePath}"/>
								  </div>
							  </span>
						  </div>
					  </div>
				  </div>

			</div>
			</div>
			<div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary" onclick="return submitForm();"><i class="fa fa-check"></i> 提交</button>
                <button type="reset" class="btn btn-danger">重置</button>
			   </div>
                </div>
			<input type="hidden" name="id" value="${user.id }">
		</fieldset>
	</form:form>
	</div>
	</div>
	</div>
</body>
</html>
