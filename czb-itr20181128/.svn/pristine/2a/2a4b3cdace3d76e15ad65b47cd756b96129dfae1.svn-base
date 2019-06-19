$package('YiYa.parkWhiteList');
YiYa.parkWhiteList = function () {
    var _box = null;
    var _this = {
        config: {
            event: {
                add: function () {
                    _box.handler.add();
                },
                edit: function () {
                    _box.handler.edit();
                }
            },
            dataGrid: {
                idField: 'id',
                title: '停车场白名单表列表',
                url: 'dataList.do',
                fitColumns:true,
                columns: [[

					{field:'id',title:'ID',align:'center',width:80,sortable:true, checkbox:true},
                    {field: 'userId', title: '用户ID', align: 'center', sortable: true},
                    {field: 'userName', title: '用户名', align: 'center', sortable: true},
                    {field: 'code', title: '车位编码', align: 'center',sortable: true},
                    {field: 'phone', title: '手机号', align: 'center',  sortable: true},
                    {field: 'state', title: '状态', align: 'center',sortable: true,formatter:function(value,row,index){
                        if(value==0){
                            return "车主";
                        }if(value==1){
                            return "共享";
                        }
                        }},
                    {field: 'parkCode', title: '停车场编码', align: 'center',  sortable: true},
                    {field: 'plateNum', title: '车牌号码', align: 'center',  sortable: true},
                    {field: 'model', title: '模式', align: 'center',  sortable: true,formatter:function(value,row,index){
                        if(value==0){
                            return "短租";
                        }if(value==1){
                            return "长租";
                        }if(value==2){
                        	return "未定义";
                        }
                        }},
                    {field: 'startTime', title: '开始时间', align: 'center',  sortable: true},
                    {field: 'endTime', title: '结束时间', align: 'center',  sortable: true},
                    {field: 'createTime', title: '创建时间', align: 'center',  sortable: true},
                    {field: 'expTime', title: '失效时间', align: 'center',  sortable: true},
                    {field: 'isExp', title: '是否有效', align: 'center',  sortable: true,formatter:function(value,row,index){
                        if(value==0){
                            return "失效";
                        }if(value==1){
                            return "有效";
                        }
                        
                        }},
                    {field: 'carportId', title: '车位Id', align: 'center',  sortable: true}
                ]],
                toolbar: [
                    {id: 'btnadd', text: '新增', btnType: 'add'},
                    {id: 'btnedit', text: '修改', btnType: 'edit'},
                    {id: 'btnremove', text: '删除', btnType: 'remove'}
                ]
            }
        },
        init: function () {
            _box = new YDataGrid(_this.config);
            _box.init();
        }
    }
    return _this;
}();

$(function () {
    YiYa.parkWhiteList.init();
});