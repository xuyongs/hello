$package('YiYa.parkGateLog');
YiYa.parkGateLog = function () {
    var _box = null;
    var _this = {
        config: {
            event: {
                add: function () {
                    _box.handler.add();
                },
                edit: function () {
                    _box.handler.edit();
                },
                excel: function () {
                    _box.handler.excel();
                }
            },
            dataGrid: {
                idField: 'id',
                title: '停车场闸机日志列表',
                url: 'dataList.do',
                fitColumns:true,
                columns: [[
					{field:'id',title:'ID',align:'center',width:80,sortable:true,checkbox:true},
                    {field: 'parkCode', title: '停车场编码', align: 'center', sortable: true},
                    {field: 'gateCode', title: '闸口编码', align: 'center', sortable: true},
                    {field: 'carNo', title: '停车编号', align: 'center', sortable: true},
                    {field: 'plateNum', title: '车牌号码', align: 'center', sortable: true},
                    {field: 'ioStateStr', title: '进出库状态', align: 'center',  sortable: true},
                    {field: 'ioDate', title: '进出库时间', align: 'center',  sortable: true},
                    {field: 'picture', title: '出入库图片名称', align: 'center', width: 80, sortable: true},
                    {field: 'isVipStr', title: '是否月卡用户', align: 'center',  sortable: true},
                    {field: 'carType', title: '车辆类型', align: 'center',  sortable: true},
                    {field: 'payType', title: '支付类型', align: 'center',  sortable: true},
                    {field: 'remainSpace', title: '剩余车位', align: 'center', sortable: true},
                    {field: 'totalSpace', title: '总车位', align: 'center',  sortable: true},
                    {field: 'priceDob', title: '出场时的金额', align: 'center',  sortable: true,
                        formatter:function(val,rowData,rowIndex){
                            if(val!=null)
                                return val.toFixed(2);
                       }},
                    {field: 'orderId', title: '订单标识', align: 'center',  sortable: true},
                    {field: 'createTime', title: '创建时间', align: 'center',  sortable: true}
                ]],
                toolbar: [
                    {id: 'btnadd', text: '新增', btnType: 'add'},
                    {id: 'btnedit', text: '修改', btnType: 'edit'},
                    {id: 'btnremove', text: '删除', btnType: 'remove'},
                    {id: 'btnexcel', text: '导出', btnType: 'excel'}
                ]
            }
        },
        init: function () {
            _box = new YDataGrid(_this.config);
            _box.init();
        }
    };
    return _this;
}();

$(function () {
    YiYa.parkGateLog.init();
});