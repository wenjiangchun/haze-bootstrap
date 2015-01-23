var dataTables;
function createTable(options) {
	var tableId = options.divId;
	var url = options.url;
    var columns = getColumns(tableId);
	//var columns = options.columns;
	if (columns == null ) {
		alert("未定义表格列");
		return;
	}
	var fnCreatedRow = options.fnCreatedRow;
	var grid =  $('#'+tableId).dataTable({
		"bProcessing": true,
	    "bServerSide": true,   
		"sAjaxSource": url,
		"bFilter": false,
		"bInfo":true,
		"bSort":true,
		"bAutoWidth":true,
		"sPaginationType": "full_numbers",
		"aoColumns":columns,
		"fnCreatedRow": fnCreatedRow,
		"oLanguage": {
            "sLengthMenu": "每页显示 _MENU_条记录",  
            "sZeroRecords": "没有检索到数据",  
            "sInfo": "显示第 _START_ - _END_ 条记录；共 _TOTAL_ 条记录",  
            "sInfoEmpty": "",  
            "sProcessing": "正在加载数据...",  
            "sSearch":"检索：",
            "oPaginate": {  
                "sFirst": "首页",  
                "sPrevious": "上一页",  
                "sNext": "下一页",  
                "sLast": '尾页'
            }  
        }  ,
		"fnServerData": function(sSource,aoData,fnCallback) {   
			var json = "";
			for (var i = 0; i < aoData.length;i++) {
				var ob = aoData[i];
					json=json+ob.name+"="+ob.value+"&";
			}
			var queryVariables = getQueryVairables();
			json =json+queryVariables;
		        $.ajax( {    
		            "type": "post",     
		            "url": sSource, 
		            "dataType":"json",
		            "data":json,
		            "success": function(resp) {
                       var data = resp.aaData;
                        var newData = [];
                        $(data).each(function(i, index) {
                            var newColumn = [];
                            $(columns).each(function(j,c) {
                                var name = c.sName;
                                if (name == "id") {
                                    newColumn.push("<input type='checkbox'/>");
                                } else {
                                    try{
                                        var p = eval(c.columnRender);
                                        if (c.columnRender != null && typeof(eval(p)) == "function") {
                                            newColumn.push(p(index));
                                        } else {
                                        	if (name.split(".").length == 2) {
                                        		var nameArray = name.split(".");
                                        		if (index[nameArray[0]] != undefined && index[nameArray[0]][nameArray[1]] != undefined) {
                                        			newColumn.push(index[nameArray[0]][nameArray[1]]);
                                        		} else {
                                        			newColumn.push("");
                                        		}
                                        	} else if (name.split(".").length == 3) {
                                        		var nameArray = name.split(".");
                                        		if (index[nameArray[0]] != undefined && index[nameArray[0]][nameArray[1]] != undefined && index[nameArray[0]][nameArray[1]][nameArray[2]] != undefined) {
                                        			newColumn.push(index[nameArray[0]][nameArray[1]][nameArray[2]]);
                                        		} else {
                                        			newColumn.push("");
                                        		}
                                        	}
                                        	else if (index[name] != undefined) {
                                        		newColumn.push(index[name]);
                                            } else {
                                                newColumn.push("");
                                            }
                                        }
                                    }catch(e){
                                        alert(c.columnRender + "方法未定义");
                                        newColumn.push("");
                                    }
                                }
                            });
                            newData.push(newColumn);
                        });
                        resp.aaData = newData;
		                fnCallback(resp);
		            }
		        });    
		    }    
	});
	dataTables = grid;
	return grid;
}

/**
 * 组装页面中列表查询参数
 * @returns {String}
 */
function getQueryVairables() {
	var queryVariables = "";
	$(".databatle_query").each(function() {
		var name = $(this).attr("name");
		var value = $(this).val();
		if (value != "" && value != null) {
			queryVariables +="&queryVairables['"+name+"']="+value;
		}
	});
	return queryVariables;
}

/**
 * 刷新表格
 */
function refreshTable() {
	dataTables.fnClearTable();
}

/**
 * 获取表格列对象
 * @param tableId 表格ID
 * @returns {Array}
 */
function getColumns(tableId) {
    var columns = new Array();
    var table = $("#" + tableId + " thead").find("tr").find("th");
    $(table).each(function(index,column) {
        var bSortable = false;
        if ($(this).attr("bSortable") != undefined && $(this).attr("bSortable") == "true") {
            bSortable = true;
        }
        var columnRender = null;
        if ($(this).attr("columnRender") != undefined) {
            columnRender = $(this).attr("columnRender");
        }
        var type = null;
        if ($(this).attr("sType") != undefined) {
            bSortable = true;
            type = $(this).attr("sType");
        }
        var cssClass=null;
        if ($(this).attr("class") != undefined) {
            cssClass = $(this).attr("class");
        }
        var column = {
            "sName" : $(this).attr("sName"),
            "bSortable" : bSortable,
            "data":"id",
            "sType":type,
            "columnRender" : columnRender,
            "sClass":cssClass
        }
        columns.push(column);
    });
    return columns;
}