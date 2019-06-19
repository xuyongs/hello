# 默认用户密码
admin@qq.com/654321

# 修改全局的Urls配置
stopbar-itr-web\src\main\resources\urls.properties
stopbar-itr-web\src\main\webapp\static\js\commons\urls.js
# 按钮
stopbar-itr-web\src\main\webapp\static\js\commons\YDataGrid.js

# 前台分页参数
page:1
rows:10
sort:dictCode
order:asc
pagerOrder:order by id desc
pagerLimit:limit 0, 10

# input标签
<input class="easyui-validatebox" type="text" name="nickName"
                           data-options="required:true,validType:'length[1,10]'">
说明 data-options 属性：
required:true              必填
validType:'length[1,10]'   长度为1-10
validType:'email'          邮箱
min:0,max:99999999         最小值，最大值
validType:'url'            Url
validType:'number'         是否为数字

说明 validType 属性：
validType="equals['#newPwd']"    判断是否相等

# 关联查询
前台
<input id='typeIds_combobox' class="easyui-combobox" name="typeIds"
							data-options="
								url:'${msUrl}/siteType/typeListJson.do',
								valueField:'id',
								textField:'name',
								multiple:true,
								panelHeight:'auto'">

后台
@RequestMapping("/typeListJson")
public void  typeListJson(HttpServletResponse response) throws Exception{
    List<SiteType> dataList = siteTypeService.queryByAll();
    HtmlUtil.writerJson(response, dataList);
}

# 查询时格式化参数
formatter:function(value,row,index){
	if(value == 1){
		return "普通";
	} else if (value == 2){
		return "置頂";
	}
}

# 时间控件
<input class="easyui-datetimebox" name="currentTime" style="width:200px">

# select标签
<select class="easyui-combobox" name="state" data-options="required:true">
    <option value="0" selected="selected">可用</option>
    <option value="1">禁用</option>
</select>

# 多行标签
<textarea class="easyui-validatebox" data-options="length:[0,100]" name="descr"></textarea>

# 上传文件
（参考 static/js/ux/file/fileUpdateInfo.js 和 WEB-INF/jsp/view/file/fileUpdateInfo.jsp）
1. 新建一个保存的Win
<!-- SaveFile Win&Form -->
<div id="save-file-win" class="easyui-dialog" buttons="#SaveFilebtn" title="Save File"
	 data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
	<form id="saveFile" class="ui-form" method="post" enctype="multipart/form-data" >
		<div class="ui-edit">
			<div class="ftitle">文件上传</div>
			<div class="fitem">
				<label>说明:</label>
				<input class="easyui-validatebox" type="text" name="fileName" data-options="">
			</div>
			<div class="fitem">
				<label>文件上傳:</label>
				<input type="file" name="fileUrl" data-options="">
			</div>
		</div>
	</form>
	<div id="SaveFilebtn" class="dialog-button">
		<a href="javascript:void(0)" class="easyui-linkbutton" id="save-file-submit">Save</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="save-file-close">Close</a>
	</div>
</div>

2. 添加按钮事件
初始化按钮
toolbar:[
	{id:'btnadd',text:'Add',btnType:'add'},
	{id:'btnedit',text:'Edit',btnType:'edit'},
	{id:'btnedit',text:'Save File',btnType:'SaveFile',iconCls:'icon-edit',handler:function(){
		//var selected = _box.utils.getCheckedRows();
		//_box.utils.checkSelectOne(selected)
		_this.saveFileForm().resetForm();
		_this.saveFileWin().window('open');
	}},
	{id:'btndelete',text:'Delete',btnType:'remove'}
]

3. 菜单栏添加事件（数据库表中）
添加菜单时候，添加按钮类型事件


5.动态添加多选框（放在js文件的init()方法中）
$("#roleIds").combobox({
	url:'../sysRole/loadRoleList.do',
	valueField:'id',
	textField:'roleName',
	multiple:true,
	formatter:function(row){
	  var s = "<span><input type='checkbox' class='selectId' style='vertical-align: middle' id='selectId_"+row.id+"'>"+row.roleName+"<span>"
	  return s;
	},
	onSelect:function(record){
		$("#selectId_"+record.id).attr("checked", true);
	},
	onUnselect:function(record){
		$("#selectId_"+record.id).attr("checked", false);
	}
});

6. 区域选择框
<input id='region_combobox' class="easyui-combobox" name="regionId"
							data-options="
								url:'${msUrl}/siteType/typeListJson.do',
								valueField:'id',
								textField:'areaName',
								multiple:true,
								panelHeight:'auto'">


7. 消息提醒
YiYa.alert('提示', "处理成功", 'info');
YiYa.alert('提示', "没有登陆", 'warning');
YiYa.alert('提示', "请求异常", 'error');

8. 消息确认框
YiYa.confirm('Confirm','Are you sure you want to delete record?',function(r){
					_this.delAllLine(false);
				});

http://blog.csdn.net/lybwwp/article/details/9028741
required: "必选字段",
remote: "请修正该字段",
email: "请输入正确格式的电子邮件",
url: "请输入合法的网址",
date: "请输入合法的日期",
dateISO: "请输入合法的日期 (ISO).",
number: "请输入合法的数字",
digits: "只能输入整数",
creditcard: "请输入合法的信用卡号",
equalTo: "请再次输入相同的值",
accept: "请输入拥有合法后缀名的字符串",
maxlength: jQuery.format("请输入一个长度最多是 {0} 的字符串"),
minlength: jQuery.format("请输入一个长度最少是 {0} 的字符串"),
rangelength: jQuery.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
range: jQuery.format("请输入一个介于 {0} 和 {1} 之间的值"),
max: jQuery.format("请输入一个最大为 {0} 的值"),
min: jQuery.format("请输入一个最小为 {0} 的值")
