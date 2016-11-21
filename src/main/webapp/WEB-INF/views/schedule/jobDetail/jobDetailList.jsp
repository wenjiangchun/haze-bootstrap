<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title><fmt:message key="jobDetail"/><fmt:message key="manager"/></title>
    <script type="text/javascript" src="${ctx}/resources/datatable/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${ctx}/resources/datatable/hazeTable.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#add_btn").click(function() {
                window.location.href="${ctx}/quartz/jobDetail/add";
            });
        });

        function operatorUser(id,operator) {
            if (id != null) {
                window.location.href="${ctx}/schedule/jobDetail/"+operator+"/"+id;
            }
        }
    </script>
</head>

<body>
<ul class="breadcrumb">
    <li><a href="${ctx}"><i class="icon-home"></i> <fmt:message key="home"/></a><span class="divider">/</span></li>
    <li><a href="javascript:void(0)"><fmt:message key="schedule" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
    <li class="active"><fmt:message key="jobDetail" /><fmt:message key="manager" /></li>
</ul>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-${message.alertType.messageValue}">
        <button data-dismiss="alert" class="close">×</button>
        <fmt:message key="${message.content}" />
    </div>
</c:if>
<div class="page-content">
    <div class="hr hr-18 hr-dotted"></div>
    <div class="row">
        <div class="col-xs-12">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-group"></i> <fmt:message key="jobDetail"/><fmt:message key="list" /></h3>
                    </div>
                    <div class="panel-body">
                        <form class="form-inline" role="form">
                            <input type="hidden" name="group.id" id="groupId" class="databatle_query" value="${groupId}"/>
                            <div style="width:100%">
                                <label class="" for="status" style="margin-left:5px;">状　　态：</label>
                                <select id="status" name="status" class="databatle_query input-large">
                                    <option value=""></option>
                                    <c:forEach items="${statuss}" var="status">
                                        <option value="${status}">${status.statusName}</option>
                                    </c:forEach>
                                </select>

                                <button type="button" class="btn btn-sm btn-danger" onclick="" style="margin-left:10px;">清空</button>
                                <button type="button" class="btn btn-sm btn-primary" onclick="refreshTable();" style="margin-left:5px;">
                                    <i class="fa fa-search"></i> 查询
                                </button>
                            </div>
                        </form>
                        <div class="hr hr-18 hr-dotted"></div>
                        <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
                            <thead>
                            <tr>
                                <th><fmt:message key="jobDetail.jobGroup"/></th>
                                <th><fmt:message key="jobDetail.jobName"/></th>
                                <th><fmt:message key="jobDetail.jobClassName"/></th>
                                <th><fmt:message key="jobDetail.isDurable"/></th>
                                <th><fmt:message key="operate"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="jobDetail">
                                <tr>
                                    <td>${jobDetail.id.jobGroup}</td>
                                    <td>${jobDetail.id.jobName}</td>
                                    <td>${jobDetail.jobClassName}</td>
                                    <td>
                                        <c:if test="${jobDetail.isDurable}">是</c:if>
                                        <c:if test="${!jobDetail.isDurable}">否</c:if>
                                    </td>
                                    <td>
                                        <a class="btn btn-link" href="${ctx}/schedule/jobDetail/resume/${jobDetail.id.jobGroup}/${jobDetail.id.jobName}" title="<fmt:message key="resume"/>"><i class="icon-play"></i></a>
                                        <a class="btn btn-link" href="${ctx}/schedule/jobDetail/pause/${jobDetail.id.jobGroup}/${jobDetail.id.jobName}" title="<fmt:message key="pause"/>"><i class="icon-pause"></i></a>
                                        <a class="btn btn-link" href="${ctx}/schedule/jobDetail/delete/${jobDetail.id.jobGroup}/${jobDetail.id.jobName}" title="<fmt:message key="delete"/>"><i class="icon-trash"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <button type="button" class="btn btn-primary" id="add_btn"><fmt:message key="add"/><fmt:message key="jobDetail"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>