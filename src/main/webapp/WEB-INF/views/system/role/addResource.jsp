<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="resource"/></title>
	<link href="${ctx}/resources/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/resources/zTree/js/jquery.ztree.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			initResourceTree();
			$("#submit_btn").click(function() {
				var roleId = $("#roleId").val();
				var ids = new Array();
                var checkNodes = $.fn.zTree.getZTreeObj("resourceTree").getCheckedNodes();
                if (checkNodes.length == 0) {
                	//notify("请选择信息");
                }
                $.each(checkNodes,function(i,n) {
                	ids.push(n.id);
                });
				window.location.href="${ctx}/system/role/saveResources/"+roleId+"/"+ids;
			});
		});
		
		function initResourceTree() {
			  $.ajax({
				  method : "post",
				  url : "${ctx}/system/resource/getResources",
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
						  }, 
						  callback: {
							  onClick:onClick
						  }, 
						  check: {
							  chkStyle:"checkbox",
							  enable:true
						  }
						  };
						  $.fn.zTree.init($("#resourceTree"), setting, data);
						  var tree = $.fn.zTree.getZTreeObj("resourceTree");
						  tree.expandAll(true);
						 // zTree.checkNode(nodes[i], true, false, callbackFlag);
						 // var node = tree.getNodeByParam("id",parentId);
						  $("input[name=resource]").each(function() {
							  var id = $(this).val();
							  var node = tree.getNodeByParam("id",id);
							  tree.checkNode(node, true, false, true);
						  });
					  }
				  }
			  });	
			
			}
		function onClick() {
		}
	</script>
</head>

<body>
	<div class="content">
	 <div class="leftTree box" style="width:400px;margin-left:100px">
	     <div data-original-title="" class="box-header">
			<h2>
				 <i class=""></i><span class="break"></span>系统资源树</a>
			</h2>
			<div class="box-icon">
			</div>
		</div>
		 <ul id="resourceTree" class="ztree"></ul>
		 <div class="form-actions"><br>
		 <input id="submit_btn" class="btn btn-primary" type="button" value="提交"/>&nbsp;	
		 <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
	</div>
	</div>
	</div>
	<input type="hidden" name="roleId" id="roleId" value="${role.id }"/>
	<c:forEach items="${role.resources}" var="resource">
	   <input type="hidden" name="resource" value="${resource.id}"/>
	</c:forEach>
</body>
</html>
