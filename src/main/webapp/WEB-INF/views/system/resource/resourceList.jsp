<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title><fmt:message key="resource"/><fmt:message key="manager"/></title>
	<%@ include file="/resources/impDatatable.jsp"%> 
	<script type="text/javascript">
		$(document).ready(function() {
			initMenu("viewResource_Menu");
			initDataTable();			
			$("#clearBtn").click(function(){ //清空按钮
				$(".databatle_query").val("");
			});
			$("#delete_btn").click(function() {
				var ids = new Array();
				$("input[name=resource_id_checkbox]").each(function() {
					if (this.checked) {
						ids.push($(this).val());
					}
				});
				window.location.href="${ctx}/system/resource/delete/" + ids ;
			});
			$("#add_btn").click(function() {
				window.location.href="${ctx}/system/resource/add";
			});
		});
		
		function operatorResource(id,operator) {
            if (id != null) {
                window.location.href="${ctx}/system/resource/"+operator+"/"+id;
            }
        }
        
        function initDataTable() {
            var options = {
                    divId:"contentTable",
                    url : "${ctx}/system/resource/search"
                    
            };
            createTable(options);
        }
        function tbreresh() {
			dataTable.fnClearTable();
		}
        
        function editResource(id){
        	 window.location.href="${ctx}/system/resource/edit/"+id;
        }
        
        function deleteResource(id){
        	if(window.confirm("确定删除该资源")){
        		 window.location.href="${ctx}/system/resource/delete/"+id;
        	}
        }
        
        function formatOperator(data) {
            var html = "";
            html += "<a href='javascript:void(0)' onclick='editResource(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit fa-lg'></i> </a> | ";
            html += "<a href='javascript:void(0)' onclick='deleteResource(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o fa-lg'></i> </a>";
            return html;
        }
        
        function formatType(data) {
        	var html = "";
        	if (data.resourceType == "M") {
        		return "菜单资源";
        	} else {
        		return "操作资源";
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
			<li class="active">资源管理</li>
		</ul>
		<!-- .breadcrumb -->
	</div>
	<div class="page-content">
		<div class="hr hr-18 hr-dotted"></div>
		<div class="row">
			<div class="col-xs-12">
				<form class="form-inline" role="form">
						<label class="" for="name">资源名：</label> 
						<input type="text" id="name" name="name_like" class="databatle_query input-middle">
					
						<label class="" for="name">资源类型：</label> 
						<select id="resourceType" name="resourceType" class="databatle_query input-large">
							<option value=""></option>
							 <c:forEach items="${resourceTypes}" var="resourceTypes">
							 <option value="${resourceTypes}">${resourceTypes.typeName}</option>
							</c:forEach>
						</select>
					
					<button type="button" class="btn btn-sm btn-danger" onclick="">清空</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();" style="margin-left:5px;">
						<i class="fa fa-search"></i> 查询
					</button>
				</form>
				<div class="hr hr-18 hr-dotted"></div>
				<div class="table-header">
					<i class="fa fa-user"></i> 资源列表
				</div>
				<div class="table-responsive">
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
		                        <th sName="id" type="checkbox"><fmt:message key="num"/></th>
		                        <th sName="name"><fmt:message key="resource.name"/></th>
					            <th sName="permission"><fmt:message key="resource.permission"/></th>
					            <th sName="resourceType" columnRender="formatType"><fmt:message key="resource.resourceType"/></th>
					            <th sName="url"><fmt:message key="resource.url"/></th>
					            <th sName="operator" columnRender="formatOperator"><fmt:message key="operate"/></th>
		                    </tr>
						</thead>
					</table>
					<div class="hr hr-18 hr-dotted"></div>
					<button type="button" class="btn btn-danger" id="add_btn">
						<i class="fa fa-plus"></i>
						<fmt:message key="add"/>
						<fmt:message key="resource"/>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
