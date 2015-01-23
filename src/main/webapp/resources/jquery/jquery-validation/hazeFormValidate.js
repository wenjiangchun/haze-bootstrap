/**
 * 初始化系统Form验证
 * @param formId Form ID
 * @param rule 校验规则
 * @param message 校验信息
 */
function initFormValidate(formId, rules, messages, submitHandler) {
	if (formId == null) {
		alert("表单ID不能为空！");
		return;
	}
	//判断验证错误信息所在Div是否在页面中存在，不存在则创建该Div
	var $errorDiv = $("#" + formId + " div.formError");
	if ($errorDiv.length == 0) {
		$errorDiv = $("<div>").addClass("formError alert alert-danger hide").appendTo($("#"+formId));
	} else {
		$errorDiv.html("");
	}
	var option = {
        focusInvalid: true,   
        onkeyup: false,
        onfocusout: true,
        errorLabelContainer: $errorDiv,
        wrapper: "li",
        showErrors: function(errorMap, errorList) {
        	this.defaultShowErrors();
        	$errorDiv.addClass("show");
        	},
        highlight: function(element, errorClass) {
            $(element).parent().parent().addClass("has-error");
         },
        unhighlight: function(element, errorClass) {
            $(element).parent().parent().removeClass("has-error");
        }
	};

	if (rules != undefined && rules != null) {
		option.rules = rules;
	}
	if (messages != undefined && messages != null) {
		option.messages = messages;
	}
    if (typeof(submitHandler) == "function") {
        option.submitHandler = submitHandler;
    } else {
        option.submitHandler = defaultSubmitHandler;
    }
	$("#"+formId).validate(option);
}

/**
 * 默认表单验证完成后提交函数，假设表单都是在弹出窗口中打开
 * @param form 表单对象
 * @returns {boolean}
 */
function defaultSubmitHandler(form) {
    $(form).ajaxSubmit({
        dataType : 'json',
        success : function(data) {
            //top.hideMyModal(data);
        	if (data.alertType == "SUCCESS") {
        		alert("操作成功");
        		var directionUrl = $(form).attr("directUrl");
        		if(directionUrl != null && directionUrl != ""){//如果有跳转地址则转向跳转地址，否则刷新当前页面
        			window.location.href = directionUrl;
        		}else{
        			window.location.reload();
        		}
        		
        	} else {
        		alert("操作失败【"+data.content+"】");
        	}
        }
    });
    //return false;
}
