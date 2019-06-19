$package('YiYa.parkOrderInfo');
YiYa.parkOrderInfo = function(){
	var _box = null;
	var _this = {
		config:{
			event:{
				add:function(){
					_box.handler.add();
				},
				edit:function(){
					_box.handler.edit();
				},
                excel: function () {
                    _box.handler.excel();
                }
			},
  			dataGrid:{
                idField:'id',
  				title:'停车场订单信息列表',
	   			url:'dataList.do',
	   			fitColumns:true,
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'userId',title:'用户ID',align:'center',sortable:true},
                    {field:'userName',title:'用户姓名',align:'center',sortable:true},
                    {field:'parkCode',title:'停车场编码',align:'center',sortable:true},
                    {field:'plateNum',title:'车牌号码',align:'center',sortable:true},
                    {field:'totalPriceDob',title:'金额',align:'center',sortable:true,
                        formatter:function(val,rowData,rowIndex){
                            if(val!=null)
                                return val.toFixed(2);
                       }},
                    {field:'typeStr',title:'订单类型',align:'center',sortable:true},
                    {field:'stateStr',title:'状态',align:'center',sortable:true},
                    {field:'refId',title:'关联标识',align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',sortable:true}
				]],
				toolbar:[
					{id:'btnadd',text:'新增',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btnremove',text:'删除',btnType:'remove'},
                    {id: 'btnexcel', text: '导出', btnType: 'excel'}
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
	YiYa.parkOrderInfo.init();
});