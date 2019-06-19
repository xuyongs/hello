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
                <label class="ui-label">车牌号码:</label>
                <input name="plateNum" class="easyui-box ui-text" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                <label class="ui-label">停车场编码:</label>
                <input name="parkCode" class="easyui-box ui-text" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
               <label class="ui-label">状态:</label>
<select class="easyui-combobox" style="width:150px" name="state" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="">-请选择-</option>
                        <option value="1">进</option>
                        <option value="2">出</option>
                    </select>
                     <label class="ui-label">进库开始时间:</label>
                <input name="inDatestartTimeNew" class="easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                     <label class="ui-label">进库结束时间:</label>
                <input name="inDateendTimeNew" class="easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                         <label class="ui-label">出库开始时间:</label>
                <input name="outDatestartTimeNew" class="easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                         <label class="ui-label">出库结束时间:</label>
                <input name="outDateendTimeNew" class="easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                                    
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
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
                <div class="ftitle">停车场汽车状态</div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode" placeholder="请填写停车场编码" type="text" name="parkCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>车牌号码:</label>
                    <input class="easyui-validatebox" validtype="plateNum" placeholder="请填写车牌号码" type="text" name="plateNum" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>进出库类型:</label>
                    <select class="easyui-combobox" style="width:150px" name="state" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="">-请选择-</option>
                        <option value="1">进</option>
                        <option value="2">出</option>
                    </select>
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>进库时间:</label>
                    <input class="easyui-datetimebox" type="text" name="inDate" data-options="">
                </div>
                <div class="fitem">
                    <label>进库日志标识:</label>
                    <input class="easyui-validatebox" type="text" name="inLogId" data-options="">
                </div>
                <div class="fitem">
                    <label>出库时间:</label>
                    <input class="easyui-datetimebox" type="text" name="outDate" data-options="">
                </div>
                <div class="fitem">
                    <label>出库日志标识:</label>
                    <input class="easyui-validatebox" type="text" name="outLogId" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkCarState.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
