<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/resources/impTagLib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>创建文件夹</title>
</head>
<body>
<div style="padding: 10px">
    <form:form id="inputForm" modelAttribute="fileEntity" action="${ctx}/file/createPath" method="post">
        <table cellpadding="5">
            <tr>
                <td>文件夹名称:</td>
                <td>
                    <input class="easyui-validatebox" type="text" id="name" name="name" data-options="required:true"/>
                    <input type="hidden" id="parentPath" name="parentPath" value="${parentPath}"/>
                    <input type="hidden" id="fileType" name="fileType" value="DIRECTORY"/>
                </td>
            </tr>
        </table>
    </form:form>
    <div style="text-align: center; padding: 5px">
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="submitForm()">提交</a> <a href="javascript:void(0)"
                                            class="easyui-linkbutton" onclick="closeDialog()">清空</a>
    </div>
</div>
<script type="text/javascript">
    $('#inputForm').form({
        success:function(data){
            closeDialog(eval('(' + data + ')') );
        }
    });
    function submitForm() {
        $('#inputForm').form('submit');
    }
</script>

</body>
</html>
