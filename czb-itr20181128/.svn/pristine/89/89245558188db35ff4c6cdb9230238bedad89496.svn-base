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
                <label class="ui-label">闸机编码:</label>
                <input name="gateCode" class="easyui-box ui-text" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
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
                <div class="ftitle">停车场系统闸机信息</div>
                <div class="fitem">
                    <label>闸机编码:</label>
                    <input class="easyui-validatebox" validtype="gateCode" placeholder="请填写闸机编码" type="text" name="gateCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode" placeholder="请填写停车场编码" type="text" name="parkCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>闸机描述:</label>
                    <input class="easyui-validatebox" type="text" name="des" data-options="">
                </div>
                <div class="fitem">
                    <label>状态:</label>
                    <input class="easyui-validatebox" type="text" name="state" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkSysGate.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
