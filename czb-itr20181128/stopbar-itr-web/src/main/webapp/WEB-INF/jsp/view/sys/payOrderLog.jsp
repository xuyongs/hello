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
                <label class="ui-label">ID:</label>
                <input name="payId" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">支付状态:</label>
	                <select class="easyui-combobox" style="width:95px" name="payState" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">未支付</option>
	                    <option value="2">已支付</option>
	                </select>
                <label class="ui-label">支 付 类 型 :</label>
                	<select class="easyui-combobox" style="width:95px" name="payType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">支付宝购买车位</option>
	                    <option value="2">支付宝充值</option>
	                    <option value="3">余额购买车位</option>
	                    <option value="4">手动充值</option>
	                    <option value="5">支付宝缴费</option>
	                    <option value="6">余额缴费</option>
	                    <option value="7">提现</option>
	                    <option value="8">车位收入</option>
	                </select>
	            <label class="ui-label">支付流水号:</label>
                <input name="payNo" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">商品类型:</label>
	                <select class="easyui-combobox" style="width:95px" name="refType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">车位</option>
	                    <option value="2">停车场缴费</option>
	                </select>
                
                <label class="ui-label">开 始 时 间 :</label>
                <input name="startTime" class="easyui-box easyui-datetimebox" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">结 束 时 间 :</label>
                <input name="endTime" class="easyui-box easyui-datetimebox" style="width:95px	;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                
                
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" style="float:right" iconCls="icon-search">查询</a>
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
                    <input class="hidden" name="payId">
            <div class="ui-edit">
                <div class="ftitle">支付操作日志</div>
                <div class="fitem">
                                           <span  class='newFlag red'>*</span>
                   
                  
                    <label>支付类型:</label>
	                <select class="easyui-combobox" style="width:150px" name="carType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">支付宝购买车位</option>
	                    <option value="2">支付宝充值</option>
	                    <option value="3">余额购买车位</option>
	                    <option value="4">手动充值</option>
	                    <option value="5">支付宝缴费</option>
	                    <option value="6">余额缴费</option>
	                    <option value="7">提现</option>
	                    <option value="8">车位收入</option>
	                </select>
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                    <label>支付状态:</label>
	                <select class="easyui-combobox" style="width:150px" name="carType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">未支付</option>
	                    <option value="2">已支付</option>
	                </select>
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>支付流水号:</label>
                    <input class="easyui-validatebox" type="text" name="payNo" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                    <label>商品类型:</label>
	                <select class="easyui-combobox" style="width:150px" name="carType" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="1">车位</option>
	                    <option value="2">停车场缴费</option>
	                </select>
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>商品标识:</label>
                    <input class="easyui-validatebox" type="text" name="refId" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>数量:</label>
                    <input class="easyui-validatebox" type="text" name="quantity" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>单价:</label>
                    <input class="easyui-validatebox" type="text" name="price" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>金额:</label>
                    <input class="easyui-validatebox" type="text" name="amount" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>用户标识:</label>
                    <input class="easyui-validatebox" type="text" name="userId" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>创建时间:</label>
                    <input class="easyui-validatebox easyui-datetimebox" type="text" name="createTime" data-options="">
                </div>
                <div class="fitem">
                                        <span  class='newFlag red'>*</span>
                
                    <label>支付时间:</label>
                    <input class="easyui-validatebox easyui-datetimebox" type="text" name="payTime" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/payOrderLog.js"></script>
</body>
</html>
