<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>用户管理</title>
<%@ include file="/resources/impDatatable.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		initDataTable(); //初始化用户分页列表
		$("#clearBtn").click(function() { //清空按钮事件
			$(".databatle_query").val("");
		});
		$("#add_btn").click(function() { //添加用户按钮事件
            showMyModal("${ctx}/system/user/add","添加用户",callBackAction);
		});
	});

	/**
	 * 用户列表操作按钮对应事件
	 * id 为用户ID
	 * operator 操作类型 如"edit","delete"等
	 */
	function operatorUser(id, operator) {
		if (id != null) {
			window.location.href = "${ctx}/system/user/" + operator + "/" + id;
		}
	}

	/**
	 * 初始化用户分页列表
	 */
	function initDataTable() {
		var options = {
			divId : "contentTable",
			url : "${ctx}/system/user/search"
			//columns : columns
		};
		createTable(options);
	}

    function editUser(userId) {
        if (userId == null || userId == "") {
            alert("用户ID不能为空");
        } else {
            showMyModal("${ctx}/system/user/edit/"+userId, "编辑用户", callBackAction);
        }
    }

    function deleteUser(userId) {
        if (userId == null || userId == "") {
            alert("用户ID不能为空");
        } else {
            if (window.confirm("确认删除数据?")) {
                var userIds = [userId];
                $.ajax({
                    method:'post',
                    url:'${ctx}/system/user/delete/'+userIds,
                    success:function(data) {
                        callBackAction(data);
                    }
                });
            }
        }
    }

    function callBackAction(data) {
        if (data != undefined) {
            alert(data.content);
            refreshTable();
        }
    }
    function formatId(data) {
        return data[0];
    }
    function formatSex(data) {
        if (data.sex == "F") {
            return "男";
        } else if (data.sex == "M") {
            return "女";
        }
        return "保密";
    }

    function formatStatus(data) {
        if (data.status == "D") {
            return "<span class='label label-danger'>禁用</span>";
        }
        return "<span class='label label-success'>启用</span>";
    }

    function formatOperator(data) {
        var html = "";
        html += "<a href='javascript:void(0)' onclick='editUser(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit fa-lg'></i> </a> | ";
        html += "<a href='javascript:void(0)' onclick='deleteUser(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o fa-lg'></i> </a> | ";
        html += "<a href='javascript:void(0)' onclick='addRole(\"" + data.id + "\")' title='<fmt:message key="add"/><fmt:message key="role"/>'> <i class='fa fa-tag fa-lg'></i> </a>";
        return html;
    }
</script>
</head>
<body>
    <ol class="breadcrumb">
        <li><a href="#">主页</a></li>
        <li><a href="#">系统管理</a></li>
        <li class="active">用户管理</li>
    </ol>
    <div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"><i class="fa fa-user"></i> 用户列表</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label class="" for="loginname">登录名：</label> 
					<input type="text" id="loginname" name="loginName_like" class="databatle_query form-control">
				</div>
				<div class="form-group">
					<label class="" for="name">姓名：</label> 
					<input type="text" id="name" name="name_like" class="databatle_query form-control" />
				</div>
				<div class="form-group">
					<label class="" for="name">状态：</label> 
					<select id="status" name="status" class="databatle_query form-control">
						<option value=""></option>
						<c:forEach items="${statuss}" var="status">
							<option value="${status}">${status.statusName}</option>
						</c:forEach>
					</select>
				</div>
				<button type="button" class="btn btn-primary" onclick="refreshTable();"><i class="fa fa-search"></i> 查询</button>
				<button type="button" class="btn btn-danger" onclick="">清空</button>
			</form>
			<br>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th sName="id" type="checkbox">编号</th>
						<th sName="loginName" bSortable="true">登录名</th>
						<th sName="name" bSortable="true">用户名</th>
						<th sName="email">邮箱</th>
						<th sName="group.name">机构</th>
						<th sName="sex" columnRender="formatSex">性别</th>
						<th sName="status" columnRender="formatStatus">状态</th>
						<th sName="operator" columnRender="formatOperator">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<button type="button" class="btn btn-danger" id="add_btn">
        <i class="fa fa-plus"></i> <fmt:message key="add"/><fmt:message key="user"/>
	</button>
</body>
</html>
