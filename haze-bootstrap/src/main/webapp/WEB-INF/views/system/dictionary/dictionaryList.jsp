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
			$("#add_btn").click(function() { //添加组织机构按钮事件
				var parentId = $("#parent_ID").val();
			    var url = "${ctx}/system/dictionary/add";
			    if (parentId != null && parentId != "") {
			    	url += "?parentId="+parentId;
			    }
				window.location.href=url;
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
		
		function operatorDictionary(id,operator) {
			if (id != null) {
				window.location.href="${ctx}/system/dictionary/"+operator+"/"+id;
			}
		}
		
		/**
		 * 初始化用户分页列表
		 */
		function initDataTable(groupId) {
			var options = {
                    divId:"contentTable",
                    url : "${ctx}/system/dictionary/search",
                    columns:[
                    	 {
                    		 "sName": "id",
                             "bSortable": false,
                             "bSearchable":false,
                             "sWidth":50
                         },
                         {
                             "sName": "name",
                             "bSortable": true,
                             "sWidth":80
                         },
                         {
                             "sName": "code",
                             "bSortable": true,
                             "sWidth":80
                         },
                         {
                             "sName": "parent.name",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "isEnabled",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "operator",
                             "bSearchable":false,
                             "bSortable": false,
                             "sWidth":80
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
	<c:if test="${not empty message}">
		<div id="message"
			class="alert alert-${message.alertType.messageValue}">
			<button data-dismiss="alert" class="close">×</button>
			<fmt:message key="${message.content}" />
		</div>
	</c:if>
	<div class="row">
		<div class="col-md-3 alert">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">字典树</h3>
				</div>
				<div class="panel-body">
					<ul id="dictionaryTree" class="ztree">
					</ul>
				</div>
			</div>
		</div>
		<div class="col-md-9 alert">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">字典列表</h3>
				</div>
				<div class="panel-body">
				    <form class="form-inline" role="form">
		<div class="form-group">
			<label class="" for="name">字典名称：</label> <input type="text" id="name"
				name="name_like" class="databatle_query form-control">
		</div>
		<div class="form-group">
			<label class="" for="code">字典代码：</label> <input type="text" id="code"
				name="code_like" class="databatle_query form-control" />
		</div>
		<div class="form-group">
			<label class="" for="isEnabled">状态：</label> <select id="isEnabled"
				name="isEnabled" class="databatle_query form-control">
				<option value=""></option>
				<option value="true">启用</option>
				<option value="false">禁用</option>
			</select>
		</div>
		<button type="button" class="btn btn-primary"
			onclick="refreshTable();">查询</button>
		<button type="button" class="btn btn-default" onclick="">清空</button>
	</form>
	 <br>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th><fmt:message key="num" /></th>
								<th><fmt:message key="dictionary.name" /></th>
								<th><fmt:message key="dictionary.code" /></th>
								<th>字典分类</th>
								<th><fmt:message key="status" /></th>
								<th><fmt:message key="operate" /></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-danger" id="add_btn">添加字典</button>
		</div>
	</div>
	<input type="hidden" class="databatle_query" name="parent"
		id="parent_ID" value="${parentId}" />
</body>
</html>
