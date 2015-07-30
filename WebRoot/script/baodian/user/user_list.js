/**
 * 用户搜索ip查询框的工具栏，添加到这里是防止重复加载
 */
if($('#usli_ipToolbar').length == 0) {
	$('body').append('<div id="usli_ipToolbar" style="padding:5px;height:auto;">'+
			'<div style="padding: 5px 0 0 4px;">'+
			'IP：<input id="usli_ipInput" class="index_input160" />'+
			'&ensp;<a href="javascript:;" class="easyui-linkbutton"'+
				'onclick="$(\'#usli_ip\').combogrid(\'grid\').datagrid(\'load\');"'+
				'data-options="iconCls:\'icon-search\'">查找</a>'+
		'</div></div>');
} else {
	$('#usli_ipInput').val('');
}
/**
 * 用户搜索IP查询框
 */
$('#usli_ip').combogrid({
	panelHeight: 250,
	panelWidth: 430,
	idField: 'id',
	textField: 'ip',
	fitColumns: true,//自动调整单元格宽度
	pagination: true,//分页
	multiple: false,//多选
	editable: false,//编辑
	toolbar: '#usli_ipToolbar',//工具条
	url: 'ip_list_js.action?page.status=0&_=' + Math.random(),
	queryParams: {
    	"page.ip": {"id":"usli_ipInput","type":"text"}
    }, paramsName: {
    	page: "page.page",
    	num: "page.num"
    }, columns: [[
        {field: 'ck', checkbox:true},
        {field: "id", hidden: true},
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
     ]]
});
$('#usli_table').datagrid({
    url: 'user_list_js.action?_=' + Math.random(),
    iconCls: "icon-edit",
    title: "用户列表",
    striped: true,//条纹
    fitColumns: true,//自动调整单元格宽度
    rownumbers: true,//显示行号
    nowrap: false,//取消单行显示
    //singleSelect: true,//单选
    pagination: true,//分页
    toolbar: '#usli_toolbar',
    queryParams: {
    	"page.id": {"id":"usli_id","type":"text"},
    	"page.name": {"id":"usli_name","type":"text"},
    	"page.account": {"id":"usli_account","type":"text"},
    	"page.ip": {"id":"usli_ip","type":"combogrid"},
    }, paramsName: {
    	page: "page.page",
    	num: "page.num"
    }, columns: [[
        {field: 'ck', checkbox:true},
        {field: 'id', title: 'ID', width: 10, align: 'center'},
        {field: 'user.name', title: '名字', width: 20},
        {field: 'user.account', title: '账号', width: 20},
        {field: 'role', title: '角色', width: 25},
        {field: 'dpmName', title:'部门', width: 25},
        {field: 'workIp', title:'允许下载工作机', width: 28,
			formatter: function(value, row, index){
				var ips = new Array();
				$.each(row.ip, function(k, v) {
					if(v.type == 1) {
						ips.push(v.ip);
					}
				});
				return ips.join('<br />');
			}},
		{field: 'netIp', title:'允许下载上网机', width: 28,
			formatter: function(value, row, index){
				var ips = new Array();
				$.each(row.ip, function(k, v) {
					if(v.type == 0) {
						ips.push(v.ip);
					}
				});
				return ips.join('<br />');
			}},
        {field: 'maxSize', title:'总容量', width: 15,
	        formatter: function(fsize, row, index){
				return usli_formatFileSize(fsize);
			}},
        {field: 'uploadSize', title:'单文件上传大小', width: 15,
			formatter: function(fsize, row, index){
				return usli_formatFileSize(fsize);
			}},
        {field: 'roleId', hidden: true},
        {field: 'depmId', hidden: true}
    ]], onDblClickRow: function(index, data) {
    	usli_editUser(data);
    }
});
/**
 * 格式化输出文件大小
 */
function usli_formatFileSize(fsize) {
	if(fsize < 1024)
		fsize = fsize + " B";
	else
		if(fsize >= 1099511627776)
			fsize = (fsize/1099511627776).toFixed(2) + " TB";
		else if(fsize >= 1073741824)
			fsize = (fsize/1073741824).toFixed(2) + " GB";
		else if(fsize >= 1048576)
			fsize = (fsize/1048576).toFixed(1) + " MB";
		else
			fsize = (fsize/1024).toFixed(0) + " KB";
	return fsize;
}
function usli_search() {
	$('#usli_table').datagrid("load");
}
var usli_url = "";
/**
 * 弹出添加用户窗
 */
function usli_newUser() {
	$("#usli_ac_span").html("");
	$("#usli_pw_span").html("为空，使用默认123456。");
	$('#usli_dlg').dialog('open').dialog('setTitle','添加用户');
	$('#usli_fm').form('clear');
	$('#usli_maxSize').numberbox('setValue', 1);
	$('#usli_uploadSize').numberbox('setValue', 100);
	$('#usli_netIp').combogrid('clear');
	$('#usli_workIp').combogrid('clear');
	usli_url = 'user_save_js.action';
	//0:未更改 1:添加 2:修改
	usli_row.edit = 1;
}
var usli_row = {};//更改前row数据
/**
 * 弹出修改用户窗
 */
function usli_editUser(data) {
	if(data == null) usli_row = $('#usli_table').datagrid('getSelected');
	else usli_row = data;
	if (usli_row){
		$("#usli_ac_span").html("更改后，密码为空置123456。");
		$("#usli_pw_span").html("为空，不更改。");
		$('#usli_dlg').dialog('open').dialog('setTitle','修改用户');
		usli_row["user.password"] = "";
		$('#usli_fm').form('load',usli_row);
		$('#usli_maxSize').numberbox('setValue', (usli_row['maxSize']/1073741824).toFixed(2));
		$('#usli_uploadSize').numberbox('setValue', (usli_row['uploadSize']/1048576).toFixed(2));
		usli_setIp('usli_netIp', 0);
		usli_setIp('usli_workIp', 1);
		usli_url = 'user_change_js.action?user.id='+usli_row.id;
		usli_row.edit = 2;
	} else {
		index_mess("请先选择", 4);
	}
}
/**
 * 为更新用户设置ip
 */
function usli_setIp(domId, type) {
	var ipNoRead = new Array();
	var ipSet = {};
	$.each($('#' + domId).combogrid('grid').datagrid('getRows'), function(k, v) {
		ipSet[v.id] = true;
	});
	var ips = new Array();
	$.each(usli_row.ip, function(k, v) {
		if(v.type == type) {
			ips.push(v.id);
			if(! ipSet[v.id]) {//下拉框中不存在的ip
				ipNoRead.push(v.id);
			}
		}
	});
	if(ipNoRead.length != 0) {
		index_mess("读取中...", 0);
		$.ajax({
			url: 'ip_ipList_js.action?json=' + ipNoRead.join('-'),
			async: false,
			cache: false,
			dataType: 'json',
			success: function(data) {
				if(data.status == 1) {
					index_mess(data.mess, 1);
					if(data.login == false) {
						index_login();
					}
				} else {
					index_mess("读取完成！", 2);
					$.each(data, function(k, v) {
						$('#' + domId).combogrid('grid').datagrid('appendRow', v);
					});
				}
			}
		});
	}
	$('#' + domId).combogrid('clear');
	$('#' + domId).combogrid('setValues', ips);
}
function usli_removeUser() {
	if(usli_row && usli_row.edit==2) {
		index_mess("正在修改，不能删除！", 4);
		return;
	}
	var row =  $('#usli_table').datagrid('getSelected');
	if (row){
		$.messager.prompt('删除用户-' + row["user.name"] + '-确定', '请输入你的密码:', function(r){
			if (r){
				index_mess("删除中...", 0);
				$.post("user_remove_js.action", {"user.id":row.id,"user.password":r}, function(data) {
					if(data.status == 0) {
						index_mess("删除成功！", 2);
						$('#usli_table').datagrid('reload');
					} else {
						index_mess(data.mess, 1);
						if(data.login == false) {
							index_login();
						}
						if(data.password == false) {
							return false;
						}
					}
				}, "json");
			}
		},"password");
	} else {
		index_mess("请先选择用户！", 4);
	}
}
/**
 * 添加或更改用户到数据库
 */
function usli_saveUser() {
	if(!$("#usli_fm").form('validate')) {
		return;
	}
	//0:user.name 1:user.account 2:user.password 3-Last:roleId Last:depmId
	var params = $("#usli_fm").serializeArray();
	if(usli_url.substring(5, 6) == "c") {
		if(params[0].value == usli_row["user.name"]) {
			params[0].value = "";
		}
		if(params[1].value == usli_row["user.account"]) {
			if(params[2].value == "")
				params[1].value = "";
		}
		if(params[params.length - 1].value == usli_row.depmId) {
			params[params.length - 1].value = -1;
		}
		var rids = "", newrids = "";
		$.each(usli_row.roleId, function(k, v) {
			rids = rids + v + ",";
		});
		for(var i=3;i<params.length-1;i++) {
			newrids = newrids + params[i].value + ",";
		}
		if(rids == newrids) {
			if(params.length > 4)
				params[3].value = "-1";
			else
				params.push({"name": "roleId","value": "-1"});
		}
	}
	var fileSize = $('#usli_maxSize').numberbox('getValue');
	fileSize = (fileSize * 1073741824).toFixed(0);
	params.push({"name": "user.maxSize","value": fileSize});
	fileSize = $('#usli_uploadSize').numberbox('getValue');
	fileSize = (fileSize * 1048576).toFixed(0);
	params.push({"name": "user.uploadSize","value": fileSize});
	var ips = $('#usli_netIp').combogrid('getValues');
	$.each($('#usli_workIp').combogrid('getValues'), function(k, v) {
		ips.push(v);
	});
	params.push({"name": "json", "value": ips.join('-')});
	index_mess("操作中...", 0);
	$.post(usli_url, params, function(data) {
		if(data.status == 0) {
			$('#usli_dlg').dialog('close');
			if(usli_url == "user_save_js.action") {
				index_mess("添加成功！", 2);
			} else {
				index_mess("修改成功！", 2);
			}
			$('#usli_table').datagrid('reload');
		} else {
			index_mess(data.mess, 1);
			if(data.login == false) {
				index_login();
			}
			if(data.account == false) {
				$("#usli_ac_span").html(data.mess);
			}
		}
	}, "json");
}
setTimeout(function() {
	//角色下拉多选框
	$('#usli_roles').combobox({
		valueField: 'id',
		textField: 'name',
		data: usli_RDs.roles,
		editable: false,
		multiple: true,
		formatter: function(row){
			return '<img class="usli_item" src="script/easyui/themes/icons/role.png"/><span class="usli_item">'+row.name+'</span>';
		}
	});
	//更改角色树
	$('#usli_role').tree({
		checkbox: true,
		cascadeCheck: false,
		data: index_changeTree(usli_RDs.roles),
		onClick: function(node) {
			$('#usli_role').tree(node.checked? 'uncheck': 'check', node.target);
		}, onDblClick: function(node) {
			$('#usli_role').tree('toggle', node.target);
		}
	});
	var dpms = index_changeTree(usli_RDs.dpms);
	//部门下拉树
	$('#usli_cc').combotree({
	    required: true,
	    data: dpms
	});
	//移动部门树
	$('#usli_dpm').tree({
		data: dpms,
		onDblClick: function(node) {
			$('#usli_dpm').tree('toggle', node.target);
		}
	});
}, 1000);
/**
 * 打开移动部门对话框
 */
function usli_openChdpm() {
	$('#usli_chdpm').dialog('open');
}
/**
 * 为用户移动部门
 */
function usli_chdpm() {
	var users = $('#usli_table').datagrid("getChecked");
	if(users.length == 0) {
		index_mess("请先选择用户！", 4);
		return;
	}
	var dpm = $('#usli_dpm').tree('getSelected');
	if(dpm == null) {
		index_mess("请先选择部门！", 4);
		return;
	}
	var uids = "";
	$.each(users, function(k, user) {
		uids = uids + user.id + "a";
	});
	index_mess("移动中...", 0);
	$.getJSON("user_changeDpm_js.action?json=" + dpm.id + "A" + uids + "&_=" + Math.random(), function(data) {
		if(data.status == 1) {
			index_mess(data.mess, 1);
			if(data.login == false) {
				index_login();
			}
		} else {
			index_mess("移动成功！", 2);
			$('#usli_chdpm').dialog('close');
			$('#usli_table').datagrid('reload');
		}
	});
}
/**
 * 打开更改角色对话框
 */
function usli_openChrole() {
	$('#usli_chrole').dialog('open');
}
/**
 * 为用户更改角色
 */
function usli_chrole() {
	var users = $('#usli_table').datagrid("getChecked");
	if(users.length == 0) {
		index_mess("请先选择用户！", 4);
		return;
	}
	var roles = $('#usli_role').tree('getChecked');
	if(roles.length == 0) {
		index_mess("请先选择角色！", 4);
		return;
	}
	var param = $('#usli_chtype').combobox('getValue') + "A";
	$.each(roles, function(k, role) {
		param = param + role.id + "a";
	});
	param = param + "A";
	$.each(users, function(k, user) {
		param = param + user.id + "a";
	});
	index_mess("更改中...", 0);
	$.getJSON("user_changeRole_js.action?json=" + param + "&_=" + Math.random(), function(data) {
		if(data.status == 1) {
			index_mess(data.mess, 1);
			if(data.login == false) {
				index_login();
			}
		} else {
			index_mess("更改成功！", 2);
			$('#usli_chrole').dialog('close');
			$('#usli_table').datagrid('reload');
		}
	});
}
/**
 * 批量修改总容量窗口
 */
$('#usli_changeMaxSize').dialog({
	closed: true,
	//resizable: true,
	//maximizable: true,
	//modal: true,锁定其余部分
	title: '批量修改总容量',
	width: 280,
	height: 170,
	buttons: [{
		text: '保存',
		iconCls: 'icon-ok',
		handler: function() {
			var fileSize = $('#usli_MaxSizeInput').numberbox('getValue');
			if(fileSize == '') {
				index_mess("请输入总容量！", 4);
				return;
			}
			fileSize = (fileSize * 1073741824).toFixed(0);
			var users = $('#usli_table').datagrid("getChecked");
			if(users.length == 0) {
				index_mess("请先选择用户！", 4);
				return;
			}
			var uids = "";
			$.each(users, function(k, user) {
				uids = uids + user.id + "a";
			});
			index_mess("更改中...", 0);
			$.getJSON("user_changeMaxSize_js.action?json=" + fileSize + "A" + uids + "&_=" + Math.random(), function(data) {
				if(data.status == 1) {
					index_mess(data.mess, 1);
					if(data.login == false) {
						index_login();
					}
				} else {
					index_mess("更改成功！", 2);
					$('#usli_changeMaxSize').dialog('close');
					$('#usli_table').datagrid('reload');
				}
			});
		}
	},{
		text:'取消',
		iconCls: 'icon-cancel',
		handler: function(){
			$('#usli_changeMaxSize').dialog('close');
		}
	}]
});
/**
 * 批量修改上传大小窗口
 */
$('#usli_changeUploadSize').dialog({
	closed: true,
	//resizable: true,
	//maximizable: true,
	//modal: true,锁定其余部分
	title: '批量修改上传大小',
	width: 280,
	height: 170,
	buttons: [{
		text: '保存',
		iconCls: 'icon-ok',
		handler: function() {
			var fileSize = $('#usli_UploadSizeInput').numberbox('getValue');
			if(fileSize == '') {
				index_mess("请输入上传大小！", 4);
				return;
			}
			fileSize = (fileSize * 1048576).toFixed(0);
			var users = $('#usli_table').datagrid("getChecked");
			if(users.length == 0) {
				index_mess("请先选择用户！", 4);
				return;
			}
			var uids = "";
			$.each(users, function(k, user) {
				uids = uids + user.id + "a";
			});
			index_mess("更改中...", 0);
			$.getJSON("user_changeUploadSize_js.action?json=" + fileSize + "A" + uids + "&_=" + Math.random(), function(data) {
				if(data.status == 1) {
					index_mess(data.mess, 1);
					if(data.login == false) {
						index_login();
					}
				} else {
					index_mess("更改成功！", 2);
					$('#usli_changeUploadSize').dialog('close');
					$('#usli_table').datagrid('reload');
				}
			});
		}
	},{
		text:'取消',
		iconCls: 'icon-cancel',
		handler: function(){
			$('#usli_changeUploadSize').dialog('close');
		}
	}]
});
/**
 * 上网ip查询框的工具栏，添加到这里是防止重复加载
 */
if($('#usli_netIpToolbar').length == 0) {
	$('body').append('<div id="usli_netIpToolbar" style="padding:5px;height:auto;">'+
			'<div style="padding: 5px 0 0 4px;">'+
			'IP：<input id="usli_netIpInput" class="index_input160" />'+
			'&ensp;<a href="javascript:;" class="easyui-linkbutton"'+
				'onclick="$(\'#usli_netIp\').combogrid(\'grid\').datagrid(\'load\');"'+
				'data-options="iconCls:\'icon-search\'">查找</a>'+
		'</div></div>');
} else {
	$('#usli_netIpInput').val('');
}
/**
 * 上网IP查询框
 */
$('#usli_netIp').combogrid({
	panelHeight: 250,
	panelWidth: 430,
	idField: 'id',
	textField: 'ip',
	fitColumns: true,//自动调整单元格宽度
	pagination: true,//分页
	multiple: true,//多选
	editable: false,//编辑
	toolbar: '#usli_netIpToolbar',//工具条
	url: 'ip_list_js.action?page.status=0&page.type=0&_=' + Math.random(),
	queryParams: {
    	"page.ip": {"id":"usli_netIpInput","type":"text"}
    }, paramsName: {
    	page: "page.page",
    	num: "page.num"
    }, columns: [[
        {field: 'ck', checkbox:true},
        {field: "id", hidden: true},
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
     ]]
});
/**
 * 工作ip查询框的工具栏，添加到这里是防止重复加载
 */
if($('#usli_workIpToolbar').length == 0) {
	$('body').append('<div id="usli_workIpToolbar" style="padding:5px;height:auto;">'+
			'<div style="padding: 5px 0 0 4px;">'+
			'IP：<input id="usli_workIpInput" class="index_input160" />'+
			'&ensp;<a href="javascript:;" class="easyui-linkbutton"'+
				'onclick="$(\'#usli_workIp\').combogrid(\'grid\').datagrid(\'load\');"'+
				'data-options="iconCls:\'icon-search\'">查找</a>'+
		'</div></div>');
} else {
	$('#usli_workIpInput').val('');
}
/**
 * 工作IP查询框
 */
$('#usli_workIp').combogrid({
	panelHeight: 250,
	panelWidth: 430,
	idField: 'id',
	textField: 'ip',
	fitColumns: true,//自动调整单元格宽度
	pagination: true,//分页
	multiple: true,//多选
	editable: false,//编辑
	toolbar: '#usli_workIpToolbar',//工具条
	url: 'ip_list_js.action?page.status=0&page.type=1&_=' + Math.random(),
	queryParams: {
    	"page.ip": {"id":"usli_workIpInput","type":"text"}
    }, paramsName: {
    	page: "page.page",
    	num: "page.num"
    }, columns: [[
        {field: 'ck', checkbox:true},
        {field: "id", hidden: true},
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
     ]]
});
/**
 * 清除查询输入
 */
function usli_clearData() {
	$('#usli_id').numberbox('clear');
	$('#usli_name').val('');
	$('#usli_account').val('');
	$('#usli_ip').combogrid('clear');
}