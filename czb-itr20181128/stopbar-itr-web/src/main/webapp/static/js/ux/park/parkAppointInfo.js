$package('YiYa.parkAppointInfo');
YiYa.parkAppointInfo = function(){
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
  				title:'停车场预约信息列表',
	   			url:'dataList.do',
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'userId',title:'用户ID',align:'center',width:80,sortable:true},
                    {field:'userName',title:'用户姓名',align:'center',width:80,sortable:true},
                    {field:'parkCode',title:'停车场编码',align:'center',width:80,sortable:true},
                    {field:'plateNum',title:'车牌号码',align:'center',width:80,sortable:true},
                    {field:'state',title:'预约状态',align:'center',width:80,sortable:true},
                    {field:'time',title:'预约时间',align:'center',width:80,sortable:true},
                    {field:'totalPrice',title:'金额',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:80,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:80,sortable:true}
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
	YiYa.parkAppointInfo.init();
});