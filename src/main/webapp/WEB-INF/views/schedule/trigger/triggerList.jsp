<%@ page contentType="text/html;charset=UTF-8" import="org.apache.commons.lang3.time.DateFormatUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title><fmt:message key="trigger"/><fmt:message key="manager"/></title>
    <link href="${ctx}/resources/datatable/css/jquery.dataTables.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${ctx}/resources/datatable/jquery.dataTables.js"></script>
    <script type="text/javascript" src="${ctx}/resources/datatable/hazeTable.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#add_btn").click(function() {
                window.location.href="${ctx}/schedule/trigger/add";
            });
        });
    </script>
</head>

<body>
<ul class="breadcrumb">
    <li><a href="${ctx}"><i class="icon-home"></i> <fmt:message key="home"/></a><span class="divider">/</span></li>
    <li><a href="javascript:void(0)"><fmt:message key="schedule" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
    <li class="active"><fmt:message key="trigger" /><fmt:message key="manager" /></li>
</ul>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-${message.alertType.messageValue}">
        <button data-dismiss="alert" class="close">×</button>
        <fmt:message key="${message.content}" />
    </div>
</c:if>
<div class="box">
    <div data-original-title="" class="box-header">
        <h2>
            <i class="icon-table"></i><span class="break"></span><a><fmt:message key="trigger"/><fmt:message key="list" /></a>
        </h2>
        <div class="box-icon">
        </div>
    </div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
        <thead>
        <tr>
            <th><fmt:message key="trigger.triggerGroup"/></th>
            <th><fmt:message key="trigger.triggerName"/></th>
            <th><fmt:message key="trigger.prevFireTime"/></th>
            <th><fmt:message key="trigger.nextFireTime"/></th>
            <th><fmt:message key="trigger.startTime"/></th>
            <th><fmt:message key="trigger.triggerState"/></th>
            <th><fmt:message key="trigger.triggerType"/></th>
            <th><fmt:message key="operate"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="trigger">
            <c:set var="prevFireTime" value="${trigger[3]}" scope="request"/>
            <c:set var="nextFireTime" value="${trigger[2]}" scope="request"/>
            <c:set var="startTime" value="${trigger[5]}" scope="request"/>
            <tr>
                <td>${trigger[1]}</td>
                <td>${trigger[0]}</td>
                <td>
                    <%
                        String prevFireTime = request.getAttribute("prevFireTime").toString();
                        out.println(DateFormatUtils.format(Long.valueOf(prevFireTime), "yyyy-MM-dd HH:mm:ss")) ;
                    %>
                </td>
                <td>
                    <%
                        String nextFireTime = request.getAttribute("nextFireTime").toString();
                        out.println(DateFormatUtils.format(Long.valueOf(nextFireTime), "yyyy-MM-dd HH:mm:ss")) ;
                    %>
                </td>
                <td>
                    <%
                        String startTime = request.getAttribute("startTime").toString();
                        out.println(DateFormatUtils.format(Long.valueOf(startTime), "yyyy-MM-dd HH:mm:ss")) ;
                    %>
                </td>
                <td>${trigger[6]}</td>
                <td>
                    <c:if test="${trigger[7]=='CRON' }">
                        ${trigger[7]}/${trigger[11]}
                    </c:if>
                    <c:if test="${trigger[7]=='SIMPLE' }">
                        ${trigger[7]}/${trigger[11]}/${trigger[12]}/${trigger[13]}
                    </c:if>
                </td>
                <td>
                    <c:if test="${trigger[6] == 'PAUSED' }">
                        <a class="btn btn-link" href="${ctx}/schedule/trigger/resume/${trigger[1]}/${trigger[0]}" title="<fmt:message key="resume"/>"><i class="icon-play"></i></a>
                    </c:if>
                    <c:if test="${trigger[6] != 'PAUSED' }">
                        <a class="btn btn-link" href="${ctx}/schedule/trigger/pause/${trigger[1]}/${trigger[0]}" title="<fmt:message key="pause"/>"><i class="icon-pause"></i></a>
                    </c:if>
                    <a class="btn btn-link" href="${ctx}/schedule/trigger/delete/${trigger[1]}/${trigger[0]}" title="<fmt:message key="delete"/>"><i class="icon-trash"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<button type="button" class="btn btn-primary" id="add_btn"><fmt:message key="add"/><fmt:message key="trigger"/></button>
</body>
</html>