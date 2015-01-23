<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title><fmt:message key="dictionary" /><fmt:message key="manager" /></title>
    <%@ include file="/resources/impDatatable.jsp"%> 
	<%@ include file="/resources/impZtree.jsp"%> 
	<script type="text/javascript">
	    var dataTable ;
		$(document).ready(function() {
			initMenu("viewDictionary_Menu");
			initDictionaryTree(); //初始化字典树
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
			$("#add_btn").click(function() { //添加用户按钮事件
				var parentId = $("#parent_ID").val();
			    var url = "${ctx}/system/dictionary/add";
			    if (parentId != null && parentId != "") {
			    	url += "?parentId="+parentId;
			    }
			    window.location.href=url;
	            //showMyModal(url,"添加字典",callBackAction);
			});
		});
		
		function initDictionaryTree() {
		  $.ajax({
			  method : "post",
			  url : "${ctx}/system/dictionary/getDictionaryTree",
			  dataType : "json",
			  success : function(data) {
				  if (data.length > 0) {
					  var setting = {data:{
						  simpleData:{
							  enable:true,
							  idKey:"id",
							  pIdKey:"pid",
							  rootPId:null
						  }
					  }, callback: {
						  onClick:onClick
					  }};
					  $.fn.zTree.init($("#dictionaryTree"), setting, data);
					  var tree = $.fn.zTree.getZTreeObj("dictionaryTree");
					  var parentId = $("#parent_ID").val();
					  if (parentId != null && parentId != "") {
						  var node = tree.getNodeByParam("id",parentId);
						  tree.expandNode(node, true, false, true);
					  } else {
						  var node = tree.getNodeByParam("name","字典树");
						  tree.expandNode(node, true, false, true);
					  }
				  }
				  
			  }
		  });	
		
		}
		
		function onClick(event, treeId, treeNode, clickFlag) {
			$("#parent_ID").val(treeNode.id);
			refreshTable();
		}	
		
		/**
		 * 初始化字典分页列表
		 */
		function initDataTable(groupId) {
			var options = {
                    divId:"contentTable",
                    url : "${ctx}/system/dictionary/search"
            };
			dataTable = createTable(options);
		}
	 function callBackAction(data) {
	        if (data != undefined) {
	            alert(data.content);
	            refreshTable();
	        }
	    }
	 function formatParentCode(data) {
	        if (data.parent != null) {
	            return data.parent.name;
	        }
	        return "";
	}
	 function formatStatus(data) {
	        if (!data.isEnabled) {
	            return "<span class='label label-danger'>禁用</span>";
	        }
	        return "<span class='label label-success'>启用</span>";
	    }
	 function formatOperator(data) {
	        var html = "";
	        html += "<a href='javascript:void(0)' onclick='editDictionary(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit fa-lg'></i> </a> | ";
	        html += "<a href='javascript:void(0)' onclick='deleteDictionary(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o fa-lg'></i> </a>";
	        return html;
	    }
	 
	 function editDictionary(userId) {
	        if (userId == null || userId == "") {
	            alert("字典ID不能为空");
	        } else {
	            //showMyModal("${ctx}/system/dictionary/edit/"+userId, "编辑字典", callBackAction);
	            window.location.href = "${ctx}/system/dictionary/edit/"+userId;
	        }
	    }

	    function deleteDictionary(id) {
	        if (id == null || id == "") {
	            alert("字典ID不能为空");
	        } else {
	            if (window.confirm("确认删除数据?")) {
	                var id = [id];
	                $.ajax({
	                    method:'post',
	                    url:'${ctx}/system/dictionary/delete/'+id,
	                    success:function(data) {
	                    	if(data.alertType == "SUCCESS"){
	                        	alert("操作成功!");
	                        }else{
	                        	alert(data.content);
	                        }
	                        refreshTable();
	                        removeTreeNodeByNodeId(id);
	                    }
	                });
	            }
	        }
	    }
	    
		function removeTreeNodeByNodeId(id){
			var treeObj = $.fn.zTree.getZTreeObj("dictionaryTree");
		    if (id != null && id != "") {
			  var node = treeObj.getNodeByParam("id", id);
			  if(node != null){
				  treeObj.removeNode(node);
			  }
		    }
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
			<li class="active">字典管理</li>
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
					<h3 class="panel-title"><i class="fa fa-tree"></i> 字典树</h3>
				</div>
				<div class="panel-body">
					<ul id="dictionaryTree" class="ztree">
					</ul>
				</div>
			</div>
		</div>
		<div class="col-md-9">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title"><i class="fa fa-book"></i> 字典列表</h3>
				</div>
				<div class="panel-body">
				    <form class="form-inline" role="form">
						<label class="" for="name">字典名称：</label> 
						<input type="text" id="name" name="name_like" class="databatle_query input-small">
						<label class="" for="code">字典代码：</label> 
						<input type="text" id="code" name="code_like" class="databatle_query input-small" />
						<label class="" for="isEnabled">状态：</label>
						<select id="isEnabled"
							name="isEnabled" class="databatle_query input-middle">
							<option value=""></option>
							<option value="true">启用</option>
							<option value="false">禁用</option>
						</select>
						
						<button type="button" class="btn btn-sm btn-default" onclick="">清空</button>
						<button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();"><i class="fa fa-search"></i> 查询</button>
					</form>
					 <br>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th sName="sn"><fmt:message key="num" /></th>
								<th sName="name"><fmt:message key="dictionary.name" /></th>
								<th sName="code"><fmt:message key="dictionary.code" /></th>
								<th sName="parent" columnRender="formatParentCode">字典分类</th>
								<th sName="status" columnRender="formatStatus"><fmt:message key="status" /></th>
								<th sName="operate" columnRender="formatOperator"><fmt:message key="operate" /></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-danger" id="add_btn"><i class="fa fa-plus"></i> 添加字典</button>
		</div>
		</div>
		</div>
		</div>
	<input type="hidden" class="databatle_query" name="parent"
		id="parent_ID" value="${parentId}" />
</body>
</html>
