<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>用户管理</title>
<%@ include file="/resources/impDatatable.jsp"%>
<%@ include file="/resources/impZtree.jsp"%> 
<script type="text/javascript">
	$(document).ready(function() {
		initMenu("viewUser_Menu");
		//initGroupTree();
		initDataTable(); //初始化用户分页列表
		$("#clearBtn").click(function() { //清空按钮事件
			$(".databatle_query").val("");
		});
		$("#add_btn").click(function() { //添加用户按钮事件
           // showMyModal("${ctx}/system/user/add","添加用户",callBackAction);
		   window.location.href="${ctx}/system/user/add";
		});
	});

	function initGroupTree() {
		  $.ajax({
			  method : "post",
			  url : "${ctx}/system/group/getTopGroups",
			  dataType : "json",
			  success : function(data) {
				  var setting = {data:{
					  simpleData:{
						  enable:true,
						  idKey:"id",
						  pIdKey:"pid",
						  rootPId:null
					  },
					  key:{
						  name:"fullName"
					  }
				  }, callback: {
					  onClick:onClick
				  }};
				  $.fn.zTree.init($("#groupTree"), setting, data);
				  
				  var tree = $.fn.zTree.getZTreeObj("groupTree");
				  var parentId = $("#groupId").val();
				  if (parentId != null && parentId != "") {
					  var node = tree.getNodeByParam("id",parentId);
					  if(!node.isParent){
						  node = node.getParentNode();
					  }
					  tree.expandNode(node, true, false, true);
				  } else {
					  var node = tree.getNodeByParam("fullName","组织机构树");
					  tree.expandNode(node, true, false, true);
				  }
			  }
		  });	
		
		}
	
	
	function onClick(event, treeId, treeNode, clickFlag) {
		$("#groupId").val(treeNode.id);
		refreshTable();
	}
	
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
            //showMyModal("${ctx}/system/user/edit/"+userId, "编辑用户", callBackAction);
            window.location.href="${ctx}/system/user/edit/"+userId;
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

    function addRole(userId) {
        if (userId == null || userId == "") {
            alert("用户ID不能为空");
        } else {
            //showMyModal("${ctx}/system/user/addRoles/"+userId, "用户授权", callBackAction);
        	 window.location.href="${ctx}/system/user/addRoles/"+userId;
        }
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
    function formatId(data) {
        return data[0];
    }
    function formatSex(data) {
        if (data.sex == "F") {
            return "<i class='fa fa-female fa-lg green'></i>";
        } else if (data.sex == "M") {
            return "<i class='fa fa-male fa-lg red'></i>";
        }
        return "保密";
    }

    function formatStatus(data) {
        if (data.status == "D") {
            return "<span class='label label-danger'>禁用</span>";
        }
        return "<span class='label label-success'>启用</span>";
    }

    function formatUserType(data) {
        if (data.userType == "Z") {
            return "<span class='label label-success'>正式</span>";
        }
        if (data.userType == "S") {
            return "<span class='label label-danger'>试用</span>";
        }
        return "<span class='label label-danger'>无效</span>";
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
			<li class="active">用户管理</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
		<div class="hr hr-18 hr-dotted"></div>
		<div class="row">
			<div class="col-xs-12">
				<div class="col-md-3">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title"><i class="fa fa-tree"></i> 机构树</h3>
						</div>
						<div class="panel-body">
							<ul id="groupTree" class="ztree"></ul>
						</div>
					</div>
			</div>
			<div class="col-md-9">
				<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="fa fa-group"></i> 人员列表</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form">
						<input type="hidden" name="group.id" id="groupId" class="databatle_query" value="${groupId}"/>
						<div style="width:100%"> 
							<label class="" for="loginname">登录名：</label> <input type="text"
								id="loginname" name="loginName_like"
								class="databatle_query input-middle">
								
							<label class="" for="name" style="margin-left:5px;">状　　态：</label> 
							<select id="status" name="status" class="databatle_query input-large">
								<option value=""></option>
								<c:forEach items="${statuss}" var="status">
									<option value="${status}">${status.statusName}</option>
								</c:forEach>
							</select>
							
							<button type="button" class="btn btn-sm btn-danger" onclick="" style="margin-left:10px;">清空</button>
							<button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();" style="margin-left:5px;">
								<i class="fa fa-search"></i> 查询
							</button>
						</div>
					    <div>
							<label class="" for="name">姓　名：</label> <input type="text"
								id="name" name="name_like" class="databatle_query  input-middle" />
						
							<label class="" for="name" style="margin-left:5px;">用户类型：</label> 
							<select id="userType" name="userType" class="databatle_query input-large">
								<option value=""></option>
								<c:forEach items="${userTypes}" var="userType">
									<option value="${userType}">${userType.typeName}</option>
								</c:forEach>
							</select>
						</div>
					</form>
					<div class="hr hr-18 hr-dotted"></div>
					<div class="table-responsive">
						<table id="contentTable"
							class="table table-striped table-bordered table-condensed table-hover">
							<thead>
								<tr>
									<th sName="loginName" bSortable="true">登录名</th>
									<th sName="name" bSortable="true">用户名</th>
									<th sName="mobile" >手机号</th>
									<!-- <th sName="tel">固定电话</th> -->
									<!-- <th sName="email">邮箱</th> -->
									<th sName="group.name">机构</th>
									<!-- <th sName="sex" columnRender="formatSex">性别</th> -->
									<th sName="userType" columnRender="formatUserType">用户类型</th>
									<th sName="status" columnRender="formatStatus">状态</th>
									<th sName="operator" columnRender="formatOperator">操作</th>
								</tr>
							</thead>
						</table>
						<div class="hr hr-18 hr-dotted"></div>
						<button type="button" class="btn btn-danger" id="add_btn">
							<i class="fa fa-plus"></i>
							<fmt:message key="add" />
							<fmt:message key="user" />
						</button>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>
