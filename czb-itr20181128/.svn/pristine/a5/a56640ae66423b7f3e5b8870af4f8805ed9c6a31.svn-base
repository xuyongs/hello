$package('YiYa.payOrderLog');
YiYa.payOrderLog = function(){
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
                idField:'payId',
  				title:'支付操作日志列表',
	   			url:'dataList.do',
	   			columns:[[
                    {field:'payId',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'payTypeStr',title:'支付类型',align:'center',width:80,sortable:true},
                    {field:'payStateStr',title:'支付状态',align:'center',width:80,sortable:true},
                    {field:'payNo',title:'支付流水号',align:'center',width:160,sortable:true},
                    {field:'refTypeStr',title:'商品类型',align:'center',width:80,sortable:true,},
                    {field:'refId',title:'商品标识',align:'center',width:80,sortable:true},
                    {field:'quantity',title:'数量',align:'center',width:80,sortable:true},
                    {field:'price',title:'单价',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                    	if(value!=null){
                    		var valuea=value/100;
                    	return valuea.toFixed(2);
                    	}
                    	}},
                    {field:'amount',title:'支付金额',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                    	
                    	if(value!=null){
                    		var valuea=value/100;
                    	return valuea.toFixed(2);
                    	}
                    	}},
                    {field:'userName',title:'用户标识',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:120,sortable:true},
                    {field:'payTime',title:'支付时间',align:'center',width:120,sortable:true}
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
	YiYa.payOrderLog.init();
});