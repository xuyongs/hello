$package('YiYa.advertInfo');
YiYa.advertInfo = function(){
	var _box = null;
	var _this = {
		saveFileAction:'saveFile.do',
		saveFileForm:function(){
			return $("#saveFile");
		},
		saveFileWin:function(){
			return $("#save-file-win");
		},
		saveFile:function(){
			if(_this.saveFileForm().form('validate')){
				_this.saveFileForm().attr('action',_this.saveFileAction);
				YiYa.saveForm(_this.saveFileForm(),function(data){
					_this.saveFileWin().dialog('close');
					_box.handler.refresh();
				});
			}
		},
		initForm:function(){
			// 初始化按钮
			_this.saveFileWin().find("#save-file-submit").click(function(){
				_this.saveFile();
			});
			_this.saveFileWin().find("#save-file-close").click(function(){
				$.messager.confirm('确认','你确定要关闭窗口吗？',function(r){
					if (r){
						_this.saveFileWin().dialog('close');
					}
				});
			});
		},
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
  				title:'广告信息列表',
	   			url:'dataList.do',
	   			columns:[[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field:'seq',title:'广告序号',align:'center',width:80,sortable:true},
                    {field:'title',title:'广告标题',align:'center',width:80,sortable:true},
                    {field:'linkUrl',title:'链接地址',align:'center',width:80,sortable:true},
                    {field:'imgUrl',title:'图片地址',align:'center',width:80,sortable:true},
                    {field:'createTime',title:'创建时间',align:'center',width:80,sortable:true},
                    {field:'updateTime',title:'修改时间',align:'center',width:80,sortable:true}
				]],
				toolbar:[
					{id:'btnedit',text:'新增',btnType:'SaveFile',iconCls:'icon-add',handler:function(){
						_this.saveFileForm().resetForm();
						_this.saveFileWin().window('open');
					}},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btnedit',text:'修改图片',btnType:'SaveFile',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							_this.saveFileForm().resetForm();
							_this.saveFileForm().find("input[name='id']").val(selected[0].id);
							_this.saveFileForm().find("input[name='seq']").val(selected[0].seq);
							_this.saveFileForm().find("input[name='title']").val(selected[0].title);
							_this.saveFileForm().find("input[name='linkUrl']").val(selected[0].linkUrl);
							_this.saveFileWin().window('open');
						}
					}},
					{id:'btndelete',text:'删除',btnType:'remove'}
				]
			}
		},
		init:function(){
			_this.initForm();
			_box = new YDataGrid(_this.config);
			_box.init();
		}
	}
	return _this;
}();

$(function(){
	YiYa.advertInfo.init();
});