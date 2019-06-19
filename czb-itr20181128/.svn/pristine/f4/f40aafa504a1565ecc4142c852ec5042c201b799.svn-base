$package('YiYa.dataDict');
YiYa.dataDict = function(){
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
				idField:'dictId',
				title:'字段数据列表',
				url:'dataList.do',
	   			fitColumns:true,
				columns:[[
					{field:'dictId',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
					{field:'parentDictId',title:'父字典id',align:'center',width:80,sortable:true},
					{field:'dictName',title:'字典名称',align:'center',width:80,sortable:true},
					{field:'dictCode',title:'字典代码',align:'center',width:80,sortable:true},
					{field:'dictKey',title:'字典key',align:'center',width:80,sortable:true},
					{field:'dictValue',title:'字典value',align:'center',width:80,sortable:true},
					{field:'note',title:'说明',align:'center',width:80,sortable:true},
					{field:'userId',title:'创建人',align:'center',width:80,sortable:true},
					{field:'updateDate',title:'修改时间',align:'center',width:80,sortable:true},
					{field:'createDate',title:'创建时间',align:'center',width:80,sortable:true}
				]]
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
	YiYa.dataDict.init();
});