$package('YiYa.parkSysInfo');
YiYa.parkSysInfo = function(){
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
  				title:'停车场系统信息列表',
	   			url:'dataList.do',
	   			fitColumns:true,
	   			columns:[[
					
				    {field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'parkCode',title:'停车场编码',align:'center',sortable:true},
                    {field:'type',title:'车辆类型',align:'center',sortable:true,formatter:function(value,row,index){
                       
                        if(value==0){
                            return "短租车辆";
                        }
                        if(value==1){
                            return "月租车辆";
                        }
                       /* if(value==2){
                            return "免费车";
                        }
                        if(value==3){
                            return "临时车";
                        }
                        if(value==4){
                            return "月租车(临时车)";
                        }
                        if(value==5){
                            return "月租车(月租车)";
                        }
                        if(value==6){
                            return "黑名单车";
                        }
                        if(value==7){
                            return "异常临时车(保留)";
                        }
                        if(value==8){
                            return "预约车";
                        }
                        if(value==9){
                            return "租赁车(保留)";
                        }
                        if(value==10){
                            return "充值车";
                        }
                         if(value==11){
                            return "一人多车";
                        }
                        if(value==12){
                            return "错时停车";
                        }*/
                        }},
                    {field:'name',title:'停车场名称',align:'center',sortable:true},
                    {field:'title',title:'停车场标题',align:'center',width:80,sortable:true},
                    {field:'des',title:'停车场描述',align:'center',width:80,sortable:true},
                    {field:'priceDes',title:'收费描述',align:'center',sortable:true},
					{field:'maxAmountDob',title:'封顶金额',align:'center',sortable:true,
                        formatter:function(val,rowData,rowIndex){
                            if(val!=null)
                                return val.toFixed(2);
                       }},
					{field:'tenMinutePriceDob',title:'每分钟价格',align:'center',sortable:true,
                    	    formatter:function(val,rowData,rowIndex){
                    	        if(val!=null)
                    	            return val.toFixed(2);
                    	   }},
                    {field:'city',title:'所在城市',align:'center',sortable:true},
                    {field:'area',title:'所在区域',align:'center',sortable:true},
                    {field:'addr',title:'具体地址',align:'center',width:80,sortable:true},
                    {field:'openTime',title:'营业时间',align:'center',sortable:true},
                    {field:'contacter',title:'联系人',align:'center',sortable:true},
                    {field:'phone',title:'联系电话',align:'center',sortable:true},
                    {field:'totalSpace',title:'总车位',align:'center',sortable:true},
                    {field:'remainSpace',title:'剩余车位',align:'center',sortable:true},
                    {field:'state',title:'状态',align:'center',sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "车主";
                        }if(value==1){
                            return "共享";
                        }
                        }},
                    {field:'userId',title:'上传人',align:'center',sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',sortable:true},
                    {field:'constructionType',title:'停车场施工状态',align:'center',sortable:true,formatter:function(value,row,index){
                        if(value==1){
                            return "装修完成正在使用";
                        }if(value==2){
                            return "正在装修";
                        }
                        if(value==3){
                            return "未装修";
                        }
                        }}
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
	YiYa.parkSysInfo.init();
});