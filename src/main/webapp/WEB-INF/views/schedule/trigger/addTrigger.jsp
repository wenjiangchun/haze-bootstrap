<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>添加作业</title>
    <link href="${ctx}/resources/datetimePicker/css/datetimepicker.css" rel="stylesheet" media="screen">
    <%@ include file="/resources/impForm.jsp"%>
    <script type="text/javascript" src="${ctx}/resources/datetimePicker/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="${ctx}/resources/datetimePicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript">
        jQuery.validator.addMethod("isCronTrigger", function(value, element) {
            var triggerType = $("#triggerType_select").val();
            if (triggerType == "CRON") {
                return value != null && value !="";
            } else {
                return true;
            }
        }, "CRON表达式不能为空，请填写");
        jQuery.validator.addMethod("isSimpleTrigger", function(value, element) {
            var triggerType = $("#triggerType_select").val();
            if (triggerType == "SIMPLE") {
                return value != null && value !="";
            } else {
                return true;
            }
        }, "执行间隔不能为空，请填写");
        $(document).ready(function() {
            init();
            changeTriggerType($("#triggerType_select").val());
            $("#startTime").datetimepicker({language:"zh-CN"});
            $("#endTime").datetimepicker({language:"zh-CN"});
            $("#jobGroup_select").change(function() {
                init();
            });
            $("#triggerType_select").change(function() {
                changeTriggerType($(this).val());
            });
            validateForm(); // 注册Form表单验证方法
        });
        function init() {
            var str = "";
            $("#jobGroup_select option:selected").each(function () {
                str += $(this).attr("jobName");
            });
            $("#jobDetail_id_jobName").val(str);
        }
        function changeTriggerType(triggerType) {
            if (triggerType == "CRON") {
                $('.cron_trigger_div').show();
                $(".simple_trigger_div").hide();
            } else {
                $('.cron_trigger_div').hide();
                $(".simple_trigger_div").show();
            }
        }

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
                    "id.triggerName": {required:true,
                        remote:{
                            method:"post",
                            url:"${ctx}/schedule/trigger/isNotExistTriggerName/",
                            dataType:'json',
                            data:{
                                triggerName:function(){return $("#id_triggerName").val();}
                            }
                        }
                    },
                    "cronExpression":{isCronTrigger:true},
                    "repeatCount":{digits:true,min:0},
                    "repeatInteval":{isSimpleTrigger:true,digits:true,min:0}
                },
                messages: {
                    "id.triggerName": {required:"调度名称不能为空",remote: "调度名称已存在，请重新输入！"},
                    "repeatCount":{digits:"请输入大于等于0的整数",min:"请输入大于等于0的整数"},
                    "repeatInteval":{digits:"请输入大于等于0的整数",min:"请输入大于等于0的整数"}
                }
            });
        }
    </script>
</head>

<body>
<ul class="breadcrumb">
    <li><a href="${ctx}"><i class="icon-home"></i><fmt:message key="home"/></a><span class="divider">/</span></li>
    <li><a href="javascript:void(0)"><fmt:message key="schedule" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
    <li><a href="${ctx}/schedule/trigger/view"><fmt:message key="trigger" /><fmt:message key="manager" /></a><span class="divider">/</span></li>
    <li class="active"><fmt:message key="add" /><fmt:message key="trigger" /></li>
</ul>
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
        <li><a href="${ctx}/system/user/view">用户管理</a></li>
        <li class="active">添加用户</li>
    </ul>
    <!-- .breadcrumb -->
<form:form id="inputForm" modelAttribute="trigger" action="${ctx}/schedule/trigger/save" method="post" class="form-horizontal">
    <fieldset>
        <legend><fmt:message key="add" /><fmt:message key="trigger" /></legend>
        <div class="form-group">
            <label for="id.triggerName" class="control-label"><fmt:message key="trigger.triggerName" />:</label>
            <div class="controls">
                <input type="text" id="id_triggerName" name="id.triggerName" class="input-large" placeholder="请输入触发器名称"/>
            </div>
        </div>
        <div class="form-group">
            <label for="triggerType" class="control-label"><fmt:message key="trigger.triggerType" />:</label>
            <div class="controls">
                <select name="triggerType" id="triggerType_select">
                    <option value="CRON" selected="selected">CRON表达式调度</option>
                    <option value="SIMPLE">简单调度</option>
                </select>
            </div>
        </div>
        <div class="form-group cron_trigger_div">
            <label for="cronExpression" class="control-label">CRON表达式:</label>
            <div class="controls">
                <input type="text" id="cronExpression" name="cronExpression" class="input-large" placeholder="请输入调度表达式"/>
            </div>
        </div>
        <div class="form-group simple_trigger_div" style="display:none">
            <label for="repeatInteval" class="control-label">执行间隔:</label>
            <div class="controls">
                <input type="text" class="input-mini" id="repeatInteval" name="repeatInteval">
                <select name="repeatIntevalUnit" class="span1">
                    <c:forEach items="${triggerTypes}" var="triggerType" varStatus="index">
                        <c:if test="${index.index == 0 }">
                            <option value="${triggerType}" selected="selected">${triggerType.unitName}</option>
                        </c:if>
                        <c:if test="${index.index != 0 }">
                            <option value="${triggerType}">${triggerType.unitName}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <span class="help-inline">执行一次</span>
            </div>
        </div>
        <div class="form-group simple_trigger_div" style="display:none">
            <label for="repeatCount" class="control-label">执行次数:</label>
            <div class="controls">
                <input type="text" name="repeatCount" class="input-mini"/><span class="help-inline">次</span>
            </div>
        </div>
        <div class="form-group simple_trigger_div" style="display:none">
            <label for="startTime" class="control-label"><fmt:message key="trigger.startTime" />:</label>
            <div class="controls">
                <input type="text" id="startTime" name="startTime" class="input-large" />
            </div>
        </div>
        <div class="form-group simple_trigger_div" style="display:none">
            <label for="endTime" class="control-label"><fmt:message key="trigger.endTime" />:</label>
            <div class="controls">
                <input type="text" id="endTime" name="endTime" class="input-large" />
            </div>
        </div>
        <div class="form-group">
            <label for="jobClassName" class="control-label"><fmt:message key="jobDetail" />:</label>
            <div class="controls">
                <select id="jobGroup_select" name="qrtzJobDetail.id.jobGroup">
                    <c:forEach items="${jobDetails}" var="jobDetail">
                        <option value="${jobDetail.id.jobGroup}" jobName="${jobDetail.id.jobName}">${jobDetail.id.jobGroup}.${jobDetail.id.jobName}</option>
                    </c:forEach>
                </select>
                <input type="hidden" id="jobDetail_id_jobName" name="qrtzJobDetail.id.jobName" class="input-large"/>
            </div>
        </div>
        <div class="form-group">
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
</body>
</html>