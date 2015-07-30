var ipList_data = {};

$('#ipList_table').datagrid({
	url: 'ip_list_js.action?_=' + Math.random(),
    striped: true,//条纹
    fitColumns: true,//自动调整单元格宽度
    rownumbers: true,//显示行号
    pagination: true,//分页
    toolbar: '#ipList_toolbar',//工具条
    queryParams: {
    	"page.ip": {"id":"ipList_ip", "type":"text"},
    	"page.status": {"id":"ipList_status", "type":"combobox"},
    	"page.type": {"id":"ipList_type", "type":"combobox"}
    },
    paramsName: {
    	page: "page.page",
    	num: "page.num"
    },
    columns: [[
       {field: 'ck', checkbox:true},
	   {field: "id", title: "ID", align: 'center'},
       {field: "ip", title: "IP", width: 10, align: 'center'},
       {field: "type", title: "用途", width: 4, align: 'center' ,
    	   formatter: function(value, row ,index) {
    		   if(value == 1) {
    			   return '工作机';
    		   } else {
    			   return '上网机';
    		   }
    	   }},
       {field: "status", title: "状态", width: 3, align: 'center' ,
    	   formatter: function(value, row ,index) {
    		   if(value == 1) {
    			   return '停用';
    		   } else {
    			   return '使用';
    		   }
    	   }},
       {field: "date", title: "添加时间", width: 10, align: 'center'},
       {field: "updateDate", title: "更新时间", width: 10, align: 'center'},
	   {field: 'action', title: '操作', width: 5, align: 'center',
			formatter: function(value, row, index) {
				var e = '<a href="javascript:;" onclick="ipList_showrow(' + index + ')">更新</a>&nbsp;&nbsp;';
				var d = '<a href="javascript:;" onclick="ipList_remove([' + row.id + '])">删除</a>&nbsp;&nbsp;';
				return e + d;
			}}
	]], onLoadSuccess: function() {
		/*if(ipList_data.reload) {
			ipList_showrow(ipList_data.rowId);
		}
		ipList_data.reload = false;*/
		//index_loadtask(true);//更新首页任务提示
	}
});
/**
 * 添加窗口
 */
$('#ipList_add').dialog({
	closed: true,
	resizable: true,
	maximizable: true,
	//modal: true,锁定其余部分
	//title: "添加任务",
	width:340,
	height: 215,
	buttons: [{
		text: '保存',
		iconCls: 'icon-ok',
		handler: function() {
			var params = {};
			//ip
			var param = $('#ipList_addIp').val();
			if(param.length == 0) {
				index_mess("请输入IP！", 4);
				$('#ipList_addIp').focus();
				return false;
			}
			var isIp = true;
			if(ipList_data.change) {
				if(! index_checkIp(param)) {
					isIp = false;
				}
			} else {
				var ips = param.split('..');
				if(ips.length != 2) {//单个ip
					if(! index_checkIp(param)) {
						isIp = false;
					}
				} else {//多个ip
					if(! index_checkIp(ips[0])) {
						isIp = false;
					} else {
						var ipEnd = parseInt(ips[1]);
						if(isNaN(ipEnd) || ipEnd < 1 || ipEnd > 255) {
							isIp = false;
						} else {
							var ipIndex = ips[0].lastIndexOf('.');
							var ipBegin = ips[0].substring(ipIndex + 1);
							var ipBase = ips[0].substring(0, ipIndex);
							if(ipBegin >= ipEnd) {
								isIp = false;
							} else if(! confirm('是否添加从 ' + ips[0] + ' ~ ' + ipBase + '.' + ipEnd +
									'，共' + (ipEnd-ipBegin+1) + '个IP？')) {
								return false;
							}
						}
					}
				}
			}
			if(! isIp) {
				index_mess("请输入正确的IP！", 4);
				$('#ipList_addIp').focus();
				return false;
			}
			params['ip.ip'] = param;
			params['ip.status'] = $('#ipList_addstatus input:first').is(':checked')? 0: 1;
			params['ip.type'] = $('#ipList_addtype input:first').is(':checked')? 0: 1;
			var url = '';
			if(ipList_data.change) {
				params['ip.id'] = ipList_data.id;
				url = "ip_changeIp_js.action";
				index_mess("更新中...", 0);
			} else {
				url = "ip_addIp_js.action";
				index_mess("添加中...", 0);
			}
			$.post(url, params, function(data) {
				if(data.status == 1) {
					index_mess(data.mess, 1);
					return false;
				}
				index_mess(data.mess, 2);
				$('#ipList_add').dialog('close');
				if(ipList_data.change) {
					$('#ipList_table').datagrid('reload');
				} else {
					$('#ipList_table').datagrid('load');
				}
			}, "json");
		}
	},{
		text:'取消',
		iconCls: 'icon-cancel',
		handler: function(){
			$('#ipList_add').dialog('close');
		}
	}]
});
/**
 * 批量删除
 */
function ipList_remove(ids) {
	if(ids == null) {
		ids = new Array();
		var nps = $('#ipList_table').datagrid("getChecked");
		if(nps.length == 0) {
			index_mess("请先选择要删除的IP！", 4);
			return;
		}
		$.each(nps, function(k, np) {
			ids.push(np.id);
		});
	}
	$.messager.confirm('提示', '确定要删除选中的 ' + ids.length + ' 个IP吗?', function(r){
		if (r){
			index_mess("删除中...", 0);
			$.getJSON("ip_removeIp_js.action?json=" + ids.join('-') + "&_=" + Math.random(), function(data) {
				if(data.status == 0) {
					$('#ipList_table').datagrid('reload');
		    		index_mess(data.mess, 2);
	    		} else {
	    			index_mess(data.mess, 1);
	    		}
	    	});
		}
	});
}
/**
 * 批量更新IP状态
 * @param status true:设为使用 false:设为停用
 */
function ipList_editrow(status, ids, reload) {
	if(ids == null) {
		ids = new Array();
		var nps = $('#ipList_table').datagrid("getChecked");
		if(nps.length == 0) {
			index_mess("请先选择要更新的IP！", 4);
			return;
		}
		$.each(nps, function(k, np) {
			ids.push(np.id);
		});
	}
	index_mess("更新中...", 0);
	$.getJSON('ip_changeIpStatus_js.action?json=' +
			(status? '0': '1') + 'A' + ids.join('-') + '&_=' + Math.random(), function(data) {
		if(data.status == 0) {
			/*if(reload) {
				ipList_data.reload = true;
			}*/
			$('#ipList_add').dialog('close');
			$('#ipList_table').datagrid('reload');
    		index_mess(data.mess, 2);
		} else {
			index_mess(data.mess, 1);
		}
	});
}
/**
 * 批量更新IP使用类型
 * @param status true:设为上网机 false:设为工作机
 */
function ipList_editType(status, ids, reload) {
	if(ids == null) {
		ids = new Array();
		var nps = $('#ipList_table').datagrid("getChecked");
		if(nps.length == 0) {
			index_mess("请先选择要更新的IP！", 4);
			return;
		}
		$.each(nps, function(k, np) {
			ids.push(np.id);
		});
	}
	index_mess("更新中...", 0);
	$.getJSON('ip_changeIpType_js.action?json=' +
			(status? '0': '1') + 'A' + ids.join('-') + '&_=' + Math.random(), function(data) {
		if(data.status == 0) {
			/*if(reload) {
				ipList_data.reload = true;
			}*/
			$('#ipList_add').dialog('close');
			$('#ipList_table').datagrid('reload');
    		index_mess(data.mess, 2);
		} else {
			index_mess(data.mess, 1);
		}
	});
}
/**
 * 设置状态为完成
 */
function ipList_changeStatus() {
	ipList_editrow(true, [ipList_data.id], true);
}
/**
 * 打开查看窗口
 */
function ipList_showrow(rowId) {
	ipList_data.change = true;
	ipList_data.rowId = rowId;
	var row = $('#ipList_table').datagrid('getRows')[rowId];
	ipList_data.id = row.id;
	$('#ipList_add').dialog('open').dialog('setTitle','更新IP - ' + row.ip);
	$('#ipList_addMess').html('只允许输入正确的单个ip');
	$('#ipList_addIp').val(row.ip);
	if(row.status == 0) {
		$('#ipList_addstatus input:first').attr("checked",'true');
	} else {
		$('#ipList_addstatus input:eq(1)').attr("checked",'true');
	}
	if(row.type == 0) {
		$('#ipList_addtype input:first').attr("checked",'true');
	} else {
		$('#ipList_addtype input:eq(1)').attr("checked",'true');
	}
}
/**
 * 打开添加窗口
 */
function ipList_showadd() {
	ipList_data.change=false;
	$('#ipList_add').dialog('open').dialog('setTitle','添加IP');
	$('#ipList_addMess').html('允许输入连续ip的简写方式，如：10.0.100.1..10');
	$('#ipList_addIp').val('');
	$('#ipList_addstatus input:first').attr("checked",'true');
	$('#ipList_addtype input:first').attr("checked",'true');
}
/**
 * 清除查询输入
 */
function ipList_clearData() {
	$('#ipList_ip').val('');
	$('#ipList_status').combobox('setValue', '-1');
	$('#ipList_type').combobox('setValue', '-1');
}
