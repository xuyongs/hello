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
                <label class="ui-label">用户ID:</label>
                <input name="userId" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">状态:</label>
	                <select class="easyui-combobox" style="width:95px" name="state" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="0">申请</option>
	                    <option value="1">同意</option>
	                    <option value="2">拒绝</option>
	                </select>
	               
                <label class="ui-label">开 始 时 间 :</label>
                <input name="startTime" class="easyui-box easyui-datetimebox" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">结 束 时 间 :</label>
                <input name="endTime" class="easyui-box easyui-datetimebox" style="width:95px	;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                
            </p>
            <a href="#" id="btn-search" style="margin-top:3px" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </form>
    </div>
    <!--  Search panel end -->

    <!-- Data List -->
    <form id="listForm" method="post">
        <table id="data-list"></table>
    </form>
    <!-- Edit Win&Form -->
    <div id="edit-win" class="easyui-dialog" title="提现"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="editForm" class="ui-form" method="post">
                    <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">用户提现审核</div>
                <div class="fitem">
                    <label>提现备注:</label>
                    <input class="easyui-validatebox" type="text" name="remark" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/userWithdrawalInfo.js"></script>
</body>
</html>
