$package('YiYa.parkInfo');
YiYa.parkInfo = function(){
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
  				title:'停车场信息列表',
	   			url:'dataList.do',
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'type',title:'停车场类型',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==1){
                            return "车位";
                        }if(value==2){
                            return "充电桩";
                        }
                        if(value==3){
                            return "加油站";
                        }if(value==4){
                            return "停车场";
                        }
                        if(value==5){
                            return "私人车位";
                        }if(value==101){
                            return "系统车位";
                        }if(value==102){
                            return "系统停车场";
                        }
                        }},
                    {field:'name',title:'停车场名称',align:'center',width:80,sortable:true},
                    {field:'title',title:'停车场标题',align:'center',width:80,sortable:true},
                    {field:'des',title:'停车场描述',align:'center',width:80,sortable:true},
                    {field:'priceDes',title:'收费描述',align:'center',width:80,sortable:true},
                    {field:'city',title:'所在城市',align:'center',width:80,sortable:true},
                    {field:'area',title:'所在区域',align:'center',width:80,sortable:true},
                    {field:'addr',title:'具体地址',align:'center',width:80,sortable:true},
                    {field:'openTime',title:'营业时间',align:'center',width:80,sortable:true},
                    {field:'contacter',title:'联系人',align:'center',width:80,sortable:true},
                    {field:'phone',title:'联系电话',align:'center',width:80,sortable:true},
                    {field:'parkCode',title:'系统停车场编码',align:'center',width:80,sortable:true},
                    {field:'totalSpace',title:'总车位',align:'center',width:80,sortable:true},
                    {field:'remainSpace',title:'剩余车位',align:'center',width:80,sortable:true},
                    {field:'mapLng',title:'经度',align:'center',width:80,sortable:true},
                    {field:'mapLat',title:'纬度',align:'center',width:80,sortable:true},
                    {field:'maxAmount',title:'封顶价格',align:'center',width:80,sortable:true},
                    {field:'tenMinutePrice',title:'分时价格',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:80,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:80,sortable:true},
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
	YiYa.parkInfo.init();
});