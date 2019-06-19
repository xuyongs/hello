$package('YiYa.appVersionInfo');
YiYa.appVersionInfo = function(){
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
  				title:'应用版本信息列表',
	   			url:'dataList.do',
	   		    fitColumns:true,
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
					{field:'type',title:'类型ID',align:'center',width:80,sortable:true},
                    {field:'typeStr',title:'类型名',align:'center',width:80,sortable:true},
                    {field:'currentVersion',title:'当前版本',align:'center',width:80,sortable:true},
                    {field:'lowestVersion',title:'最低版本',align:'center',width:80,sortable:true},
                    {field:'apiVersion',title:'对应接口版本',align:'center',width:80,sortable:true},
                    {field:'description',title:'升级说明',align:'center',width:80,sortable:true},
                    {field:'downloadUrl',title:'下载地址',align:'center',width:200,sortable:true},
                    {field:'isForce',title:'是否强制升级',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "不强制";
                        }if(value==1){
                            return "强制";
                        }}},
                    {field:'isActivate',title:'是否激活',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "未激活";
                        }if(value==1){
                            return "激活";
                        }}},
                    {field:'sysUserId',title:'系统用户ID',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:120,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:120,sortable:true}
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
	YiYa.appVersionInfo.init();
});