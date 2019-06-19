$package('YiYa.partSysUser');
YiYa.partSysUser = function(){
	var _box = null;
	var _this = {
		config:{
			event:{
				add:function(){
					_box.handler.add();
				},
				edit:function(){
					_box.handler.edit();
				}
			},
  			dataGrid:{
                idField:'id',
  				title:'停车场系统用户列表',
	   			url:'dataList.do',
	   			fitColumns:true,
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'loginName',title:'登录名称',align:'center',sortable:true},
                    {field:'phone',title:'手机',align:'center',sortable:true},
                    {field:'email',title:'电子邮箱',align:'center',width:80,sortable:true},
                    {field:'userName',title:'用户姓名',align:'center',sortable:true},
                    {field:'idNumber',title:'身份证号',align:'center',sortable:true},
                    {field:'parkCode',title:'停车场编码',align:'center',sortable:true},
                    {field:'userId',title:'创建人',align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',sortable:true}
				]],
				toolbar:[
					{id:'btnadd',text:'新增',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btnremove',text:'删除',btnType:'remove'}
				]
			}
		},
		init:function(){
			_box = new YDataGrid(_this.config);
			_box.init();
		}
	}
	return _this;
}();

$(function(){
	YiYa.partSysUser.init();
});