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
                    url : "${ctx}/system/resource/search",
                    columns:[
                         {
                             "sName": "id",
                             "bSortable": false,
                             "sWidth":80
                         },
                         {
                             "sName": "name",
                             "bSortable": false,
                             "sWidth":100
                         },
                         {
                             "sName": "permission",
                             "bSortable": false,
                             "sWidth":100
                         },
                         {
                             "sName": "type",
                             "bSortable": false,
                             "sWidth":100
                         },
                         {
                             "sName": "url",
                             "bSortable": false,
                             "sWidth":200
                         },
                         {
                             "sName": "operator",
                             "bSortable": false,
                             "sWidth":100
                         }
                   ]
            };
            createTable(options);
        }
        function tbreresh() {
			dataTable.fnClearTable();
		}
        
	</script>
</head>

<body>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">资源列表</h3>
		</div>
		<div class="panel-body">
			<form class="form-inline" role="form">
				<div class="form-group">
					<label class="" for="name">资源名：</label> 
					<input type="text" id="name" name="name_like" class="databatle_query form-control">
				</div>
				<div class="form-group">
					<label class="" for="name">资源类型：</label> 
					<select id="resourceType" name="resourceType" class="databatle_query form-control">
						<option value=""></option>
						 <c:forEach items="${resourceTypes}" var="resourceTypes">
						 <option value="${resourceTypes}">${resourceTypes.typeName}</option>
						</c:forEach>
					</select>
				</div>
				<button type="button" class="btn btn-primary" onclick="refreshTable();">查询</button>
				<button type="button" class="btn btn-default" onclick="">清空</button>
			</form>
			<br>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
                    <thead>
                        <tr>
                        <th><fmt:message key="num"/></th>
                        <th><fmt:message key="resource.name"/></th>
			            <th><fmt:message key="resource.permission"/></th>
			            <th><fmt:message key="resource.resourceType"/></th>
			            <th><fmt:message key="resource.url"/></th>
			            <th><fmt:message key="operate"/></th>
                        </tr>
                        </thead>
                </table>
		</div>
	</div>
	<button type="button" class="btn btn-danger" id="add_btn"><i class="icon-plus icon-white"></i><fmt:message key="add"/><fmt:message key="resource"/></button>
	<button type="button" class="btn" id="delete_btn" style="display:none"><fmt:message key="delete"/><fmt:message key="resource"/></button>
</body>
</html>
