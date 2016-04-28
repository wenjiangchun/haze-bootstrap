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
		$(document).ready(function() {
            initMenu("viewProcessInstance_Menu");
		});
		
        function deleteProcess(deploymentId) {
            if (window.confirm("确认删除该流程信息？")) {
                window.location.href= ctx + "/workflow/processDefinition/deleteProcessDefination/" + deploymentId;
            }
        }
        
        function updateProcessState(processId, state, operate) {
            if (window.confirm("确认" + operate + "流程信息？")) {
                window.location.href= ctx + "/workflow/processDefinition/updateProcessDefinitionState/" + processId + "/" + state;
            }
        }
        
        function openDeploymentDialog() {
            $('#myModal').modal('show')
        }
	</script>
</head>

<body>
	<ul class="breadcrumb">
		<li><a href="${ctx}"><i class="icon-home"></i> <fmt:message key="home" /></a><span class="divider">/</span></li>
		<li><a href="javascript:void(0)">流程<fmt:message key="manager" /></a><span class="divider">/</span></li>
		<li class="active">流程实例<fmt:message key="manager" /></li>
	</ul>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button><fmt:message key="${message}"/></div>
	</c:if>
	<div class="content">
	<table class="table table-striped table-bordered .table-hover">
               <thead>
            <tr>
                <th>执行ID</th>
                <th>流程实例ID</th>
                <th>流程定义ID</th>
                <th>当前节点</th>
                <th>是否挂起</th>
                <th>操作</th>
            </tr>
               </thead>
               <tbody>
           <c:forEach items="${processInstanceList}" var="p">
               <c:set var="pdid" value="${p.processDefinitionId }" />
               <c:set var="activityId" value="${p.activityId }" />
               <tr>
                   <td>${p.id }</td>
                   <td>${p.processInstanceId }</td>
                   <td>${p.processDefinitionId }</td>
                   <td><a class="trace" href='#' pid="${p.id }" title="点击查看流程图"></a></td>
                   <td>
                        <c:if test="${!p.suspended}">
                           <span class="label label-success">已激活</span>
                        </c:if>
                     <c:if test="${p.suspended}">
                              <span class="label label-important">已挂起</span>
                     </c:if> 
                   </td>
                   <td>
                     <c:if test="${p.suspended }">
                                 <a class="btn btn-link" href="javascript:void(0)" onclick="updateProcessState('${p.processInstanceId}','active','激活')">激活</a>
                             </c:if>
                             <c:if test="${!p.suspended }">
                                 <a class="btn btn-link" href="javascript:void(0)" onclick="updateProcessState('${p.processInstanceId}','suspend','挂起')">挂起</a>
                           </c:if>
                   </td>
               </tr>
           </c:forEach>
         </tbody>
       </table>
      </div>
</body>
</html>
