$package('YiYa.carportInfo');
YiYa.carportInfo = function(){
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
  				title:'车位信息列表',
	   			url:'dataList.do',
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'code',title:'车位编码',align:'center',width:80,sortable:true},
                    {field:'type',title:'车位类型',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                       if(value==1){
                            return "车位";
                        }if(value==2){
                        	return "充电桩";
                        }if(value==3){
                        	return "加油站";
                        }if(value==4){
                        	return "停车场";
                        }if(value==5){
                        	return "私人车位";
                        }if(value==101){
                        	return "系统车位";
                        }if(value==102){
                        	return "系统停车场";
                        }
                        }},
                    {field:'name',title:'车位名称',align:'center',width:80,sortable:true},
                    {field:'title',title:'车位标题',align:'center',width:80,sortable:true},
                    {field:'model',title:'租车模式',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "短租";
                        }if(value==1){
                            return "长租";
                        }if(value==2){
                        	return "未定义";
                        }
                        }},
                    {field:'des',title:'车位描述',align:'center',width:80,sortable:true},
                    {field:'price',title:'车位价钱',align:'center',width:80,sortable:true},
                    {field:'city',title:'所在城市',align:'center',width:80,sortable:true},
                    {field:'area',title:'所在区域',align:'center',width:80,sortable:true},
                    {field:'addr',title:'车位地址',align:'center',width:80,sortable:true},
                    {field:'openTime',title:'营业时间',align:'center',width:80,sortable:true},
                    {field:'contacter',title:'联系人',align:'center',width:80,sortable:true},
                    {field:'parkCode',title:'系统停车场编码',align:'center',width:80,sortable:true},
                    {field:'phone',title:'联系电话',align:'center',width:80,sortable:true},
                    {field:'imgUrl',title:'图片地址',align:'center',width:80,sortable:true},
                    {field:'state',title:'状态',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "发布";
                        }if(value==1){
                            return "购买";
                        }if(value==2){
                        	return "过期下线";
                        }if(value==3){
                        	return "管理员手动下线";
                        }if(value==4){
                        	return "用户手动下架";
                        }if(value==5){
                        	return "待审核";
                        }if(value==6){
                        	return "短租已购买";
                        }
                        }},
                    {field:'buyLogId',title:'购买日志标识',align:'center',width:80,sortable:true},
                    {field:'userId',title:'联系人Id',align:'center',width:80,sortable:true},
                    {field:'months',title:'发布月数',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:80,sortable:true},
                    {field:'effTime',title:'失效时间',align:'center',width:80,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:80,sortable:true},
                    {field:'isDel',title:'是否删除',align:'center',width:80,sortable:true}
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
	YiYa.carportInfo.init();
});