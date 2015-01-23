<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="group"/><fmt:message key="manager"/></title>
	<%@ include file="/resources/impDatatable.jsp"%> 
	<%@ include file="/resources/impZtree.jsp"%> 
	<script type="text/javascript">
	    var dataTable ;
		$(document).ready(function() {
			initMenu("viewGroup_Menu");
			initGroupTree(); //初始化机构树
			initDataTable(); //初始化用户分页列表
			$("#clearBtn").click(function(){ //清空按钮
				$(".databatle_query").val("");
			});
			$("#delete_btn").click(function() { //批量删除用户按钮事件
				var ids = new Array();
				$("input[name=user_id_checkbox]").each(function() {
					if (this.checked) {
						ids.push($(this).val());
					}
				});
				//window.location.href="${ctx}/system/user/delete/" + ids ;
				$.get(this.groupService.batchDelete(ids),function(data){
					 if(data.alertType == "SUCCESS"){
		            	alert("操作成功!");
		             }else{
		            	alert(data.content);
		             }
					 window.location.reload();
				});
			});
			$("#add_btn").click(function() { //添加组织机构按钮事件
				var parentId = $("#parent_ID").val();
			    var url = "${ctx}/system/group/add";
			    if (parentId != null && parentId != "") {
			    	url += "?parentId="+parentId;
			    }
			    //showMyModal(url,"添加机构",callBackAction);
			    window.location.href=url;
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
				  var parentId = $("#parent_ID").val();
				  if (parentId != null && parentId != "") {
					  var node = tree.getNodeByParam("id",parentId);
					  tree.expandNode(node, true, false, true);
				  } else {
					  var node = tree.getNodeByParam("fullName","组织机构树");
					  tree.expandNode(node, true, false, true);
				  }
			  }
		  });	
		
		}
		
		function onClick(event, treeId, treeNode, clickFlag) {
			$("#parent_ID").val(treeNode.id);
			refreshTable();
		}	
		
		/**
		 * 组织机构列表操作按钮对应事件
		 * id 为组织机构ID
		 * operator 操作类型 如"edit","delete"等
		 */
		function operatorGroup(id,operator) {
			if (id != null) {
				window.location.href="${ctx}/system/group/"+operator+"/"+id;
			}
		}
		
		/**
		 * 初始化用户分页列表
		 */
		function initDataTable(groupId) {
			var options = {
					divId : "contentTable",
					url : "${ctx}/system/group/search"
				};
			dataTable = createTable(options);
		}
		function callBackAction(data) {
	        if (data != undefined) {
	            alert(data.content);
	            refreshTable();
	        }
	    }
		
	 function editGroup(id){
		 window.location.href="${ctx}/system/group/edit/"+id;
	 }
		
	 function deleteGroup(id){
		 $.get("${ctx}/system/group/delete/"+id, function(data){
			if(data.alertType == "SUCCESS"){
            	alert("操作成功!");
            }else{
            	alert(data.content);
            }
            refreshTable();
            removeTreeNodeByNodeId(id);
		 });
	 }
	 
	function removeTreeNodeByNodeId(id){
		var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	    if (id != null && id != "") {
		  var node = treeObj.getNodeByParam("id", id);
		  if(node != null){
			  treeObj.removeNode(node);
		  }
	    }
	}
		
	  function formatOperator(data) {
	        var html = "";
	        html += "<a href='javascript:void(0)' onclick='editGroup(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit fa-lg'></i> </a>|";
	        html += "<a href='javascript:void(0)' onclick='deleteGroup(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o fa-lg'></i> </a>";
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
			<li class="active">机构管理</li>
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
					<h3 class="panel-title"><i class="fa fa-group"></i> 机构列表</h3>
				</div>
				<div class="panel-body">
                    <form class="form-inline" role="form">
                                <label class="" for="fullName">机构名称：</label>
                                <input type="text" id="fullName" name="fullName_like" class="databatle_query input-middle"/>
                                <label class="" for="groupType">机构类型：</label>
                                <select id="groupType" name="groupType.id" class="databatle_query input-large">
                                    <option value=""></option>
                                    <c:forEach items="${groupTypeList}" var="groupType">
                                        <option value="${groupType.id}">${groupType.name}</option>
                                    </c:forEach>
                                </select>
	                            
	                            <button type="button" class="btn btn-sm btn-default" onclick="">清空</button>
	                            <button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();">
	                            	<i class="fa fa-search"></i> 查询
	                            </button>
                    </form>
                    </br>
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					    <thead>
							<tr>
							<th sName="id" type="checkbox"><fmt:message key="num"/></th>
							<th sName="fullName"><fmt:message key="group.fullName" /></th>
							<th sName="name"><fmt:message key="group.name" /></th>
							<th sName="groupType.name"><fmt:message key="group.groupType" /></th>
							<th sName="code"><fmt:message key="group.groupCode" /></th>
							<th sName="tel"><fmt:message key="group.tel" /></th>
							<th sName="operator" columnRender="formatOperator"><fmt:message key="operate" /></th>
							</tr>
							</thead>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-danger" id="add_btn"><i class="fa fa-plus"></i> 添加机构</button>
		</div>
		</div>
		</div>
		</div>
	<input type="hidden" class="databatle_query" name="parent"
		id="parent_ID" value="${parentId}" />
</body>
</html>
