$package('YiYa.userInfo');
YiYa.userInfo = function(){
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
  				title:'用户表列表',
	   			url:'dataList.do',
	   		    fitColumns:true,
	   			columns:[[

					{field:'id',title:'用户ID',align:'center',width:80,sortable:true, checkbox:true},
                    {field:'phone',title:'手机号'},
                    {field:'loginName',title:'登录名称',align:'center',width:80,sortable:true},
                    {field:'loginPwd',title:'登录密码',align:'center',sortable:true},
                    {field:'nickName',title:'昵称',align:'center',width:80,sortable:true},
                    {field:'signa',title:'个性签名',align:'center',width:80,sortable:true},
                    {field:'age',title:'年龄',align:'center',width:80,sortable:true},
                    {field:'sexStr',title:'性别',align:'center',width:80,sortable:true},
                    {field:'imgUrl',title:'头像地址',align:'center',width:80,sortable:true},
                    {field:'email',title:'电子邮箱',align:'center',sortable:true},
                    {field:'isAuth',title:'实名认证',align:'center',width:80,sortable:true,formatter:function(value,row,index){
                        if(value==0){
                            return "未认证";
                        }if(value==1){
                            return "已认证";
                        }}},
                    {field:'userId',title:'用户ID',align:'center',width:80,sortable:true},
                    {field:'userName',title:'用户姓名',align:'center',width:80,sortable:true},
                    {field:'idNumber',title:'身份证号',align:'center',sortable:true},
                   
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
	YiYa.userInfo.init();
});