$package('YiYa.serveInfo');
YiYa.serveInfo = function(){
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
  				title:'设备服务信息列表',
	   			url:'dataList.do',
	   			columns:[[
                    {field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'type',title:'设备类型',align:'center',width:80,sortable:true},
                    {field:'name',title:'设备名称',align:'center',width:80,sortable:true},
                    {field:'title',title:'设备标题',align:'center',width:80,sortable:true},
                    {field:'des',title:'设备描述',align:'center',width:80,sortable:true},
                    {field:'priceDes',title:'收费描述',align:'center',width:80,sortable:true},
                    {field:'city',title:'所在城市',align:'center',width:80,sortable:true},
                    {field:'area',title:'所在区域',align:'center',width:80,sortable:true},
                    {field:'addr',title:'具体地址',align:'center',width:80,sortable:true},
                    {field:'openTime',title:'营业时间',align:'center',width:80,sortable:true},
                    {field:'contacter',title:'联系人',align:'center',width:80,sortable:true},
                    {field:'phone',title:'联系电话',align:'center',width:80,sortable:true},
                    {field:'imgUrl',title:'图片地址',align:'center',width:80,sortable:true},
                    {field:'state',title:'状态',align:'center',width:80,sortable:true},
                    {field:'userId',title:'上传人',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:80,sortable:true},
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
	YiYa.serveInfo.init();
});