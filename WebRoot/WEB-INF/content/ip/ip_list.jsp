<%@ page contentType="text/html; charset=UTF-8"  %>
<div class="demo-info">
	<div class="demo-tip icon-tip"></div>
	<div>提示：1.ip列表。</div>
</div>
<div id="ipList_toolbar" style="padding:5px;height:auto;">
	<div style="padding: 5px 0 0 4px;">
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_showadd()"
			data-options="iconCls:'icon-add','plain':true">添加</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_editType(true)"
			data-options="'iconCls':'icon-edit','plain':true">标为上网机</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_editType(false)"
			data-options="'iconCls':'icon-edit','plain':true">标为工作机</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_editrow(true)"
			data-options="'iconCls':'icon-edit','plain':true">标为使用</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_editrow(false)"
			data-options="'iconCls':'icon-edit','plain':true">标为停用</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_remove()"
			data-options="'iconCls':'icon-remove','plain':true">删除</a>&ensp;
	</div>
	<div style="padding: 5px 0 0 4px;">
		IP：<input id="ipList_ip" class="index_input200" />&ensp;
		用途：<select id="ipList_type" class="easyui-combobox"
			data-options="width:60, panelHeight:76,editable:false">
			<option value="-1">全部</option>
			<option value="0">上网机</option>
			<option value="1">工作机</option>
		</select>&ensp;
		状态：<select id="ipList_status" class="easyui-combobox"
			data-options="width:56, panelHeight:76,editable:false">
			<option value="-1">全部</option>
			<option value="0">使用</option>
			<option value="1">停用</option>
		</select>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="$('#ipList_table').datagrid('load');"
			data-options="iconCls:'icon-search'">查找</a>&ensp;
		<a href="javascript:;" class="easyui-linkbutton" onclick="ipList_clearData()">清除</a>
	</div>
</div>
<table id="ipList_table"></table>
<div id="ipList_add" style="padding:15px;">
	<div style="padding-top:8px;" id="ipList_addMess"></div>
	<div style="padding-top:8px;">IP：&ensp;<input id="ipList_addIp" class="index_input200"></div>
	<div id="ipList_addtype" style="padding-top:8px;">用途：
		<input type="radio" name="ipList_addtype" checked="checked">上网机&emsp;
		<input type="radio" name="ipList_addtype">工作机
	</div>
	<div id="ipList_addstatus" style="padding-top:8px;">状态：
		<input type="radio" name="ipList_addstatus" checked="checked">使用&emsp;&emsp;
		<input type="radio" name="ipList_addstatus">停用
	</div>
</div>
<br /><br />
