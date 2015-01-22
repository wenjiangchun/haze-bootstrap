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
                showMyModal("${ctx}/system/role/add","添加角色", callBackAction);
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
                alert(data.content);
                refreshTable();
            }
        }

        function editRole(id) {
            if (id != null) {
                showMyModal("${ctx}/system/role/edit/" + id, "<fmt:message key="edit"/><fmt:message key="role"/>", callBackAction);
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

        function addResources(id) {
            if (id != null) {
                showMyModal("${ctx}/system/role/addResources/" + id, "<fmt:message key="add"/><fmt:message key="resource"/>", callBackAction);
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
    <ol class="breadcrumb">
        <li><a href="#">主页</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">角色管理</li>
    </ol>
    <div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><i class="fa fa-tag"></i> 角色列表</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label class="" for="name">角色名：</label> 
					<input type="text" id="name" name="name_like" class="databatle_query form-control">
				</div>
				<div class="form-group">
					<label class="" for="status">状态：</label> 
					<select id="status" name="status" class="databatle_query form-control">
						<option value=""></option>
						<c:forEach items="${statuss}" var="status">
							<option value="${status}">${status.statusName}</option>
						</c:forEach>
					</select>
				</div>
				<button type="button" class="btn btn-primary" onclick="refreshTable();"><i class="fa fa-search"></i> 查询</button>
				<button type="button" class="btn btn-default" onclick="">清空</button>
			</form>
			<br>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
                    <thead>
                        <tr>
                            <th sName="id"><fmt:message key="num"/></th>
                            <th sName="name"><fmt:message key="role.name"/></th>
                            <th sName="code" bSortable="true"><fmt:message key="role.roleName"/></th>
                            <th sName="status" columnRender="formatStatus"><fmt:message key="status"/></th>
                            <th sName="operate" columnRender="formatOperator"><fmt:message key="operate"/></th>
                        </tr>
                    </thead>
                </table>
		</div>
	</div>
	<button type="button" class="btn btn-danger" id="add_btn"><i class="fa fa-plus-circle"></i> <fmt:message key="add"/><fmt:message key="role"/></button>
	<button type="button" class="btn btn-danger hide" id="delete_btn"><span class="glyphicon glyphicon-minus-sign"></span> <fmt:message key="delete"/><fmt:message key="role"/></button>
</body>
</html>
