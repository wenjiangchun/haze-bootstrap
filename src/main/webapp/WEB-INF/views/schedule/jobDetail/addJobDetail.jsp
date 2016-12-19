<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>添加作业</title>
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            validateForm();
        });
        function validateForm() {
            $("#inputForm").validate({ // 注册Form表单验证方法
                success: function(label) {
                },
                errorPlacement: function(error, element) {
                    error.appendTo( element.parent() );
                },
                errorClass: "help-inline",
                focusInvalid: false,
                onkeyup: true,
                highlight: function(element, errorClass) {
                    $(element).parent().parent().removeClass("success").addClass("error");
                },
                unhighlight: function(element, errorClass) {
                    $(element).parent().parent().removeClass("error").addClass("success");
                },
                rules: {
                    "id.jobName": {required:true,
                        remote:{
                            method:"post",
                            url:"${ctx}/schedule/jobDetail/isNotExistJobName/",
                            dataType:'json',
                            data:{
                                jobName:function(){return $("#id_jobName").val();},
                                jobGroup:function(){return $("#id_jobGroup").val();}
                            }
                        }
                    },
                    "jobClassName":{required:true}
                },
                messages: {
                    "id.jobName": {required:"作业名称不能为空",remote: "作业名称已存在，请重新输入！"},
                    "jobClassName":{required:"必须关联作业Job类"}
                }
            });
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
        <li><a href="${ctx}"><i class="icon-home"></i><fmt:message key="home"/></a><span class="divider">/</span></li>
        <li><a href="javascript:void(0)"><fmt:message key="schedule" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li><a href="${ctx}/schedule/jobDetail/view"><fmt:message key="jobDetail" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
        <li class="active"><fmt:message key="add" /><fmt:message key="jobDetail" /></li>
    </ul>
    <!-- .breadcrumb -->
</div>

<div class="page-content">
    <div class="row">
        <div class="col-xs-10">
            <form:form id="inputForm" modelAttribute="jobDetail" action="${ctx}/quartz/jobDetail/save" method="post" class="form-horizontal">
                <fieldset>
                    <legend><fmt:message key="add" /><fmt:message key="jobDetail" /></legend>
                    <div class="control-group">
                        <label for="id.jobGroup" class="control-label"><fmt:message key="jobDetail.jobGroup" />:</label>
                        <div class="controls">
                            <input type="hidden" id="id_jobGroup" name="id.jobGroup" class="input-large uneditable-input" value="DEFAULT"/>
                            <span class="input-large uneditable-input">DEFAULT</span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="id.jobName" class="control-label"><fmt:message key="jobDetail.jobName" />:</label>
                        <div class="controls">
                            <input type="text" id="id_jobName" name="id.jobName" class="input-large" placeholder="请输入作业名"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="jobClassName" class="control-label"><fmt:message key="jobDetail.jobClassName" />:</label>
                        <div class="controls">
                            <select id="jobClassName" name="jobClassName">
                                <c:forEach items="${jobClassNames}" var="name">
                                    <option>${name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label for="description" class="control-label"><fmt:message key="description" />:</label>
                        <div class="controls">
                            <textarea rows="3" id="description" name="description"  class="input-large" placeholder="请输入作业描述"></textarea>
                        </div>
                    </div>
                    <div class="form-actions">
                        <input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message key="submit" />"/>&nbsp;
                        <input id="cancel_btn" class="btn" type="button" value="<fmt:message key="cancel" />" onclick="history.back()"/>
                    </div>
                </fieldset>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>