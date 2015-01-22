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
				window.location.href="${ctx}/system/user/delete/" + ids ;
			});
			$("#add_btn").click(function() { //添加组织机构按钮事件
				var parentId = $("#parent_ID").val();
			    var url = "${ctx}/system/group/add";
			    if (parentId != null && parentId != "") {
			    	url += "?parentId="+parentId;
			    }
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
                    divId:"contentTable",
                    url : "${ctx}/system/group/search",
                    columns:[
                    	 {
                    		 "sName": "id",
                             "bSortable": false,
                             "bSearchable":false,
                             "sWidth":40
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "operator",
                             "bSearchable":false,
                             "bSortable": false,
                             "sWidth":120
                         }
                   ]
            };
			dataTable = createTable(options);
		}
		 function tbreresh() {
				dataTable.fnClearTable();
			}
	</script>
</head>

<body>
<ol class="breadcrumb">
    <li><a href="#">主页</a></li>
    <li><a href="#">系统管理</a></li>
    <li class="active">机构管理</li>
</ol>
 <div class="row">
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
                            <div class="form-group">
                                <label class="" for="fullName">机构名称：</label>
                                <input type="text" id="fullName" name="fullName_like" class="databatle_query form-control" style="width: 100px"/>
                            </div>
                            <div class="form-group">
                                <label class="" for="groupCode">机构代码：</label>
                                <input type="text" id="groupCode" name="groupCode_like" class="databatle_query form-control" style="width: 100px">
                            </div>
                            <div class="form-group">
                                <label class="" for="groupType">机构类型：</label>
                                <select id="groupType" name="groupType.id" class="databatle_query form-control">
                                    <option value=""></option>
                                    <c:forEach items="${groupTypeList}" var="groupType">
                                        <option value="${groupType.id}">${groupType.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="button" class="btn btn-primary" onclick="refreshTable();"><i class="fa fa-search"></i> 查询
                            </button>
                            <button type="button" class="btn btn-default" onclick="">清空</button>
                    </form>
                    </br>
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					    <thead>
							<tr>
							<th><fmt:message key="num"/></th>
							<th><fmt:message key="group.fullName" /></th>
							<th><fmt:message key="group.name" /></th>
							<th><fmt:message key="group.groupType" /></th>
							<th><fmt:message key="group.groupCode" /></th>
							<th><fmt:message key="group.tel" /></th>
							<th><fmt:message key="operate" /></th>
							</tr>
							</thead>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-danger" id="add_btn">添加字典</button>
		</div>
	<input type="hidden" class="databatle_query" name="parent"
		id="parent_ID" value="${parentId}" />
     </div>
</body>
</html>
