$package('YiYa.parkCarState');
YiYa.parkCarState = function(){
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
  				title:'停车场汽车状态列表',
	   			url:'dataList.do',
	   			fitColumns:true,
	   			columns:[[

					{field:'id',title:'ID',align:'center',width:80,sortable:true, checkbox:true},
                    {field:'parkCode',title:'停车场编码',align:'center',sortable:true},
                    {field:'plateNum',title:'车牌号码',align:'center',sortable:true},
                    {field:'state',title:'状态',align:'center',sortable:true,formatter:function(value,row,index){
                        if(value==1){
                            return "场内";
                        }if(value==2){
                            return "场外";
                        }
                        if(value==3){
                            return "场内已支付";
                        }
                        
                        if(value==5){
                            return "超时48小时";
                        }
                        
                       
                        }},
                    {field:'inDate',title:'进库时间',align:'center',sortable:true},
                    {field:'inLogId',title:'进库日志标识',align:'center',sortable:true},
                    {field:'outDate',title:'出库时间',align:'center',sortable:true},
                    {field:'outLogId',title:'出库日志标识',align:'center',sortable:true}
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
	YiYa.parkCarState.init();
});