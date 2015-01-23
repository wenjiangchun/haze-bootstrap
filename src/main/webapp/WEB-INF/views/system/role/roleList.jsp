<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="role"/><fmt:message key="manager"/></title>
	<%@ include file="/resources/impDatatable.jsp"%>
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewRole_Menu");
			initDataTable();
			//清空按钮
			$("#clearBtn").click(function(){
				$(".databatle_query").val("");
			});
			$("#delete_btn").click(function() {
				var ids = new Array();
				$("input[name=role_id_checkbox]").each(function() {
					if (this.checked) {
						ids.push($(this).val());
					}
				});
				window.location.href="${ctx}/system/role/delete/" + ids ;
			});
			$("#add_btn").click(function() {
                //showMyModal("${ctx}/system/role/add","添加角色", callBackAction);
                window.location.href = "${ctx}/system/role/add";
			});
		});
		
		function operatorRole(id,operator) {
            if (id != null) {
                window.location.href="${ctx}/system/role/"+operator+"/"+id;
            }
        }
		
		function initDataTable() {
            var options = {
                    divId:"contentTable",
                    url : "${ctx}/system/role/search"
            };
            createTable(options);
        }
        function callBackAction(data) {
            if (data != undefined) {
            	if(data.alertType == "SUCCESS"){
                	alert("操作成功!");
                }else{
                	alert(data.content);
                }
                refreshTable();
            }
        }

        function editRole(id) {
            if (id != null) {
               // showMyModal("${ctx}/system/role/edit/" + id, "<fmt:message key="edit"/><fmt:message key="role"/>", callBackAction);
                window.location.href = "${ctx}/system/role/edit/" + id;
            }else{
            	alert("待编辑的数据id不能为空");
            }
        }

        function deleteRole(roleId) {
            if (roleId == null || roleId == "") {
                alert("ID不能为空");
            } else {
                if (window.confirm("确认删除数据?")) {
                    var userIds = [roleId];
                    $.ajax({
                        method:'post',
                        url:'${ctx}/system/role/delete/'+userIds,
                        success:function(data) {
                            callBackAction(data);
                        }
                    });
                }
            }
        }

        function addResource(id) {
            if (id != null) {
                //showMyModal("${ctx}/system/role/addResources/" + id, "<fmt:message key="add"/><fmt:message key="resource"/>", callBackAction);
            	 window.location.href = "${ctx}/system/role/addResources/" + id;
            }else{
            	alert("角色id不能为空");
            }
        }

        function formatStatus(data) {
            if (data.status == "D") {
                return "<span class='label label-danger'>禁用</span>";
            }
            return "<span class='label label-success'>启用</span>";
        }

        function formatOperator(data) {
            var html = "";
            html += "<a href='javascript:void(0)' onclick='editRole(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit'></i> </a> | ";
            html += "<a href='javascript:void(0)' onclick='deleteRole(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o'></i> </a> | ";
            html += "<a href='javascript:void(0)' onclick='addResource(\"" + data.id + "\")' title='<fmt:message key="add"/><fmt:message key="resource"/>'> <i class='fa fa-database'></i> </a>";
            return html;
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
			<li class="active">角色管理</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content">
		<div class="hr hr-18 hr-dotted"></div>
		<div class="row">
			<div class="col-xs-12">
				<form class="form-inline" role="form">
						<label class="" for="name">角色名：</label> 
						<input type="text" id="name" name="name_like" class="databatle_query input-middle">
						
						<label class="" for="status" style="margin-left:5px;">状态：</label> 
						<select id="status" name="status" class="databatle_query input-large">
							<option value=""></option>
							<c:forEach items="${statuss}" var="status">
								<option value="${status}">${status.statusName}</option>
							</c:forEach>
						</select>
						
					<button type="button" class="btn btn-sm btn-danger" onclick="">清空</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();" style="margin-left:5px;">
						<i class="fa fa-search"></i> 查询
					</button>
				</form>
				<div class="hr hr-18 hr-dotted"></div>
				<div class="table-header">
					<i class="fa fa-user"></i> 角色列表
				</div>
				<div class="table-responsive">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<tr>
		                            <th sName="id"><fmt:message key="num"/></th>
		                            <th sName="name"><fmt:message key="role.name"/></th>
		                            <th sName="code" bSortable="true"><fmt:message key="role.roleName"/></th>
		                            <th sName="status" columnRender="formatStatus"><fmt:message key="status"/></th>
		                            <th sName="operate" columnRender="formatOperator"><fmt:message key="operate"/></th>
		                        </tr>
							</tr>
						</thead>
					</table>
					<div class="hr hr-18 hr-dotted"></div>
					<button type="button" class="btn btn-danger" id="add_btn">
						<i class="fa fa-plus-circle"></i> <fmt:message key="add"/><fmt:message key="role"/>
					</button>
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>
