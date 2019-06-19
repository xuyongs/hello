<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@include file="/WEB-INF/jsp/view/resource.jsp" %>
</head>
<body>

<div class="warp easyui-panel" data-options="border:false">
    <!-- Search panel start -->
    <div class="easyui-panel ui-search-panel" title="搜索框"
         data-options="striped: true,collapsible:true,iconCls:'icon-search'">
        <form id="searchForm">
            <p class="ui-fields">
                <label class="ui-label">停车场编码:</label>
                <input name="parkCode" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">车 牌 号 码 :</label>
                <input name="plateNum" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">车 辆 类 型 :</label>
	                <select class="easyui-combobox" style="width:95px" name="carType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="0">短租车辆</option>
	                    <option value="1">月租车辆</option>
	                </select>
                <label class="ui-label">支 付 类 型 :</label>
                	<select class="easyui-combobox" style="width:95px" name="payType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">APP缴费</option>
	                    <option value="2">现金缴费</option>
	                    <option value="3">自动缴费</option>
	                    <option value="4">新订单支付</option>
	                    <option value="5">计算价格</option>
	                    <option value="100">购买车位</option>
	                    <option value="200">预约</option>
	                </select>
                <label class="ui-label">开 始 时 间 :</label>
                <input name="startTime" class="easyui-box easyui-datetimebox" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">结 束 时 间 :</label>
                <input name="endTime" class="easyui-box easyui-datetimebox" style="width:95px	;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" style="margin-top:3px" iconCls="icon-search">查询</a>
        </form>
    </div>
    <!--  Search panel end -->

    <!-- Data List -->
    <form id="listForm" method="post">
        <table id="data-list"></table>
    </form>
    <!-- Edit Win&Form -->
    <div id="edit-win" class="easyui-dialog" title="编辑"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="editForm" class="ui-form" method="post">
                    <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">停车场闸机日志</div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode" placeholder="请填写停车场编码" type="text" name="parkCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>闸口编码:</label>
                    <input class="easyui-validatebox" validtype="gateCode" placeholder="请填写闸口编码" type="text" name="gateCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>停车编号:</label>
                    <input class="easyui-validatebox" validtype="carNo" placeholder="请填写停车编号" type="text" name="carNo" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>车牌号码:</label>
                    <input class="easyui-validatebox" validtype="plateNum" placeholder="请填写车牌号码" type="text" name="plateNum" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>进出库类型:</label>
                    <select class="easyui-combobox" style="width:150px" name="ioState" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="">-请选择-</option>
                        <option value="1">进</option>
                        <option value="2">出</option>
                    </select>
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>出入库图片名称:</label>
                    <input class="easyui-validatebox" type="text" name="picture" data-options="required:false">
                </div>
                <div class="fitem">
                    <label>是否月卡用户:</label>
                    <select class="easyui-combobox" style="width:150px" name="isVip" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="">-请选择-</option>
                        <option value="1">是</option>
                        <option value="2">否</option>
                    </select>
                <font color="red">*</font>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkGateLog.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
