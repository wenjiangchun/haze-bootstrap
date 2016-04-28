<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title><fmt:message key="process"/><fmt:message key="manager"/></title>
    <script type="text/javascript">
        var ctx = "${ctx}";
        $(document).ready(function () {
            initMenu("viewProcessDefinition_Menu");
        });

        function deleteProcess(deploymentId) {
            if (window.confirm("确认删除该流程信息？")) {
                window.location.href = ctx + "/workflow/processDefinition/deleteProcessDefination/" + deploymentId;
            }
        }

        function updateProcessState(processId, state, operate) {
            if (window.confirm("确认" + operate + "流程信息？")) {
                window.location.href = ctx + "/workflow/processDefinition/updateProcessDefinitionState/" + processId + "/" + state;
            }
        }

        function openDeploymentDialog() {
            alert(55);
            $('#myModal1').modal('show')
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
        <li><a href="#">流程<fmt:message key="manager"/></a></li>
        <li class="active">流程定义<fmt:message key="manager"/></li>
    </ul>
    <!-- .breadcrumb -->
</div>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">
        <button data-dismiss="alert" class="close">×</button>
        <fmt:message key="${message}"/></div>
</c:if>
<div class="page-content">
    <div class="hr hr-18 hr-dotted"></div>
    <div class="row">
        <div class="col-xs-12">
            <form class="form-inline" role="form">
                <label class="" for="name"><fmt:message key="config.name"/>：</label>
                <input type="text" id="name" name="name_like" class="databatle_query input-middle">

                <button type="button" class="btn btn-sm btn-default" onclick=""><fmt:message key="clear"/></button>
                <button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();">
                    <i class="fa fa-search"></i> <fmt:message key="search"/>
                </button>
            </form>
            <div class="hr hr-18 hr-dotted"></div>
            <div class="table-header">
                <i class="fa fa-cog"></i> 流程定义列表
            </div>
            <div class="table-responsive">
                <table id="contentTable" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>流程定义ID</th>
                        <th>部署ID</th>
                        <th>名称</th>
                        <th>KEY</th>
                        <th>版本号</th>
                        <th>XML</th>
                        <th>图片</th>
                        <th>部署时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${processList}" var="object">
                        <c:set var="process" value="${object[0] }"/>
                        <c:set var="deployment" value="${object[1] }"/>
                        <%
                            pageContext.setAttribute("isSuspended", ((org.activiti.engine.repository.ProcessDefinition) pageContext.getAttribute("process")).isSuspended());
                        %>
                        <tr>
                            <td>${process.id }</td>
                            <td>${process.deploymentId }</td>
                            <td>${process.name }</td>
                            <td>${process.key }</td>
                            <td>${process.version }</td>
                            <td><a target="_blank"
                                   href='${ctx }/workflow/processDefinition/getProcessResource?deploymentId=${process.deploymentId}&resourceName=${process.resourceName }'>${process.resourceName }</a>
                            </td>
                            <td><a target="_blank"
                                   href='${ctx }/workflow/processDefinition/getProcessResource?deploymentId=${process.deploymentId}&resourceName=${process.diagramResourceName }'>${process.diagramResourceName }</a>
                            </td>
                            <td><fmt:formatDate value="${deployment.deploymentTime }"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
                            <td>
                                <c:if test="${!isSuspended}">
                                    <span class="label label-success">已激活</span>
                                </c:if>
                                <c:if test="${isSuspended}">
                                    <span class="label label-important">已挂起</span>
                                </c:if>
                            </td>
                            <td>
                                <a class="btn btn-link" href='javascript:void(0)'
                                   onclick="deleteProcess('${process.deploymentId}')">删除</a>
                                <c:if test="${isSuspended }">
                                    <a class="btn btn-link" href="javascript:void(0)"
                                       onclick="updateProcessState('${process.id}','active','激活')">激活</a>
                                </c:if>
                                <c:if test="${!isSuspended }">
                                    <a class="btn btn-link" href="javascript:void(0)"
                                       onclick="updateProcessState('${process.id}','suspend','挂起')">挂起</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="hr hr-18 hr-dotted"></div>
                <a href="javascript:void(0)" role="button" class="btn btn-primary" data-toggle="modal"
                   onclick="openDeploymentDialog()">部署流程</a>
            </div>
        </div>
    </div>
</div>

<div id="myModal1" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <a id="myModalLabel">部署流程</a>
    </div>
    <div class="modal-body">
        <div class="alert alert-error">
            <strong>注意!</strong> 流程文件格式为.zip,.bar,.bpmn.xml.,.bpmn20.xml.
        </div>
        <form class="form-horizontal" action="${ctx}/workflow/processDefinition/deployProcessDefinition" method="post"
              enctype="multipart/form-data">
            <div class="control-group">
                <label class="control-label" for="file">选择流程文件</label>
                <div class="controls">
                    <input type="file" name="file" id="file"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">
                </label>
                <div class="controls">
                    <input class="btn btn-primary" type="submit" value="确认部署"/>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
    </div>
</div>
</body>
</html>
