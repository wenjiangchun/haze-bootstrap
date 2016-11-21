<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="add"/><fmt:message key="resource"/></title>
	<link href="${ctx}/resources/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/resources/zTree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewRole_Menu");
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
				//window.location.href="${ctx}/system/role/saveResources/"+roleId+"/"+ids;
				
				if(ids == ""){
					alert("请至少选择一个资源!");
					return false;
				}
				
				$.get("${ctx}/system/role/saveResources/"+roleId+"/"+ids,function(data){
					if(data.alertType == "SUCCESS"){
						alert("角色资源分配成功");
						backRoleList();
					}else{
						alert(data.content);
					}
					
				},"json");
			});
		});
		
		function backRoleList(){
			window.location.href = "${ctx}/system/role/view";
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
			<li><a href="#">角色管理</a></li>
			<li class="active">资源分配</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
	    <div class="row">
				<div class="col-xs-10">
					<form:form id="inputForm" modelAttribute="config" action="${ctx}/system/config/save" method="post" role="form" class="form-horizontal">
						<fieldset>
							<div class="panel panel-info">
							  <div class="panel-heading"><strong><i class="fa fa-cog green"></i> 系统资源信息</strong></div>
								<div class="panel-body">
									<ul id="resourceTree" class="ztree"></ul>
									<input type="hidden" name="roleId" id="roleId" value="${role.id }"/>
									<c:forEach items="${role.resources}" var="resource">
									   <input type="hidden" name="resource" value="${resource.id}"/>
									</c:forEach>
                                    <form class="form-inline">
                                    <table class="table table-hover table-bordered">
                                        <tbody>
                                        <c:forEach items="${menus}" var="menu">
                                        <tr>
                                            <td>
                                                <label>
                                                    <input type="checkbox" value="${menu.id}">${menu.name}
                                                </label>

                                            </td>
                                            <td>
                                                    <c:forEach items="${menu.childrens}" var="child">
                                                        <label>
                                                            <input type="checkbox" value="${child.id}"> ${child.name}
                                                        </label>

                                                    </c:forEach>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    </form>
								</div>
							</div>
				            <div class="form-group">
				                <div class="col-sm-offset-2 col-sm-10">
									 <input id="cancel_btn" class="btn" type="button" value="返回" onclick="backRoleList();"/>&nbsp;	
									   <input id="submit_btn" class="btn btn-primary" type="button" value="提交"/>
				                </div>
				            </div>
						</fieldset>
					</form:form>
			</div>
		</div>
	</div>
	
</body>
</html>
