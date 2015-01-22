<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title><fmt:message key="config"/><fmt:message key="manager"/></title>
   <%@ include file="/resources/impDatatable.jsp"%> 
   <script type="text/javascript">
	$(document).ready(function() {
		initDataTable();
       /* $('#contentTable').contextmenu({
            target: '#context-menu1',
            before: function (e, element, target) {
                e.preventDefault();
                if (e.target.tagName == 'TABLE') {
                    e.preventDefault();
                    this.closemenu();
                    return false;
                }
                return true;
            }
        });*/
        $("#add_btn").click(function() {
            showMyModal("${ctx}/system/config/add","添加配置" ,callBackAction);
        });
		});

    /**
	 * 初始化用户分页列表
	 */
	function initDataTable() {
		var options = {
			divId : "contentTable",
			url : "${ctx}/system/config/search"
		};
		createTable(options);
	}
    
    function operatorConfig(id, action) {
    	if (action == "delete") {
    		alert("确认删除操作？");
    	}
    	window.location.href="${ctx}/system/config/" + action + "/" + id;
    }

    function formatConfigType(data) {
        if (data != null) {
            if (data.configType == 'B') {
                return "业务配置";
            } else if (data.configType == 'S') {
                return "系统配置";
            }
        }
        return null;
    }

    function formatOperator(data) {
        var html = "";
        html += "<a href='javascript:void(0)' onclick='editConfig(\"" + data.id + "\")' title='<fmt:message key="edit"/>'> <i class='fa fa-edit fa-lg'></i> </a> | ";
        html += "<a href='javascript:void(0)' onclick='deleteConfig(\"" + data.id + "\")' title='<fmt:message key="delete"/>'> <i class='fa fa-trash-o fa-lg'></i> </a>";
        return html;
    }

    /**
     * 编辑配置信息
     * @param id 配置ID
     */
    function editConfig(id) {
        if (notNull(id)) {
            top.showMyModal("${ctx}/system/config/edit/"+id, "<fmt:message key="edit" /><fmt:message key="config" />", callBackAction);
        }
    }

    /**
     * 删除配置信息
     * @param id 配置ID
     */
    function deleteConfig(id) {
        if (notNull(id)) {
            if (window.confirm("确认删除数据?")) {
                var ids = [id];
                $.ajax({
                    method:'post',
                    url:'${ctx}/system/config/delete/'+ids,
                    success:function(data) {
                        callBackAction(data);
                    }
                });
            }
        }
    }

    /**
     * 添加删除编辑对象后回调方法，一般用来显示操作后信息提示和刷新表格。
     * @param data 一般对应为WebMessage对象json数据。
     */
    function callBackAction(data) {
        if (data != undefined) {
            alert(data.content);
            refreshTable();
        }
    }
</script>
</head>
<body>
    <ol class="breadcrumb">
        <li><a href="#"><fmt:message key="home"/></a></li>
        <li><a href="#"><fmt:message key="system"/><fmt:message key="manager"/></a></li>
        <li class="active"><fmt:message key="config"/><fmt:message key="manager"/></li>
    </ol>
	<div class="panel panel-primary" data-toggle="context" data-target="#context-menu">
		<div class="panel-heading">
			<h3 class="panel-title"><i class="fa fa-cog"></i> <fmt:message key="config"/><fmt:message key="list"/></h3>
		</div>
		<div class="panel-body">
		   <form class="form-inline" role="form">
				<div class="form-group">
					<label class="" for="name"><fmt:message key="config.name"/>：</label> <input type="text"
						id="name" name="name_like" class="databatle_query form-control">
				</div>
				<button type="button" class="btn btn-primary"
					onclick="refreshTable();"><i class="fa fa-search"></i> <fmt:message key="search"/></button>
				<button type="button" class="btn btn-default" onclick=""><fmt:message key="clear"/></button>
			</form>
			<br>
		   <table id="contentTable"
				class="table table-striped table-bordered table-condensed table-hover" data-toggle="context">
				<thead>
					<tr>
						<th sName="name"><fmt:message key="config.name"/></th>
						<th sName="code"><fmt:message key="config.code"/></th>
						<th sName="value"><fmt:message key="config.value"/></th>
						<th sName="configType" columnRender="formatConfigType"><fmt:message key="config.configType"/></th>
						<th sName="operator" columnRender="formatOperator"><fmt:message key="operate"/></th>
					</tr>
				</thead>
			</table>
		   </div>
	</div>
	<button type="button" class="btn btn-danger" id="add_btn">
        <i class="fa fa-plus-circle"></i> <fmt:message key="add"/><fmt:message key="config"/>
	</button>
    <%--<div id="context-menu">
        <ul class="dropdown-menu" role="menu">
            <li><a tabindex="-1" href="#">Action</a></li>
            ...
            <li><a tabindex="-1" href="#">Separated link</a></li>
        </ul>
    </div>
    --%>
</body>
</html>
