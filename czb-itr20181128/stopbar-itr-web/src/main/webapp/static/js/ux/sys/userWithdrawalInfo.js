$package('YiYa.userWithdrawalInfo');
YiYa.userWithdrawalInfo = function(){
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
  				title:'用户提现信息列表',
	   			url:'dataList.do',
	   		    fitColumns:true,
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'userId',title:'用户ID',align:'center',width:80,sortable:true},
					{field:'userName',title:'用户名称',align:'center',width:80,sortable:true},
                    {field:'amount',title:'提现金额',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                    	
                    	if(value!=null){
                    		var valuea=value/100;
                    	return valuea.toFixed(2);
                    	}
                    	}},
                    {field:'description',title:'提现说明',align:'center',sortable:true},
                    {field:'stateStr',title:'状态',align:'center',width:80,sortable:true},
                    {field:'remark',title:'备注',align:'center',width:200,sortable:true},
                    {field:'createTime',title:'创建日期',align:'center',width:120,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:120,sortable:true}
				]],
				toolbar:[
					// {id:'btnadd',text:'新增',btnType:'add'},
					{id:'btnedit',text:'提现',btnType:'edit'},
					// {id:'btnremove',text:'删除',btnType:'remove'},
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
	YiYa.userWithdrawalInfo.init();
});