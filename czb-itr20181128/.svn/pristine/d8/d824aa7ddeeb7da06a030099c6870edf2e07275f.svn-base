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
                <label class="ui-label">类型ID:</label>
                <input name="type" class="easyui-box ui-text" style="width:100px;">
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
                <div class="ftitle">应用版本信息</div>
                <div class="fitem">
                    <label>类型:</label>
                    <select class="easyui-combobox" name="type" data-options="required:true" style="width:150px">
                        <option value="">-请选择-</option>
                        <option value="0">IOS</option>
                        <option value="1">安卓</option>
                        <option value="2">微信</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>当前版本:</label>
                    <input class="easyui-validatebox" type="text" name="currentVersion" data-options="">
                </div>
                <div class="fitem">
                    <label>最低版本:</label>
                    <input class="easyui-validatebox" type="text" name="lowestVersion" data-options="">
                </div>
                <div class="fitem">
                    <label>对应接口版本:</label>
                    <input class="easyui-validatebox" type="text" name="apiVersion" data-options="">
                </div>
                <div class="fitem">
                    <label>升级说明:</label>
                    <textarea class="easyui-validatebox" data-options="length:[0,100]" name="description"></textarea>
                </div>
                <div class="fitem">
                    <label>下载地址:</label>
                    <input class="easyui-validatebox" type="text" name="downloadUrl" data-options="">
                </div>
                <div class="fitem">
                    <label>是否强制升级:</label>
                    <select class="easyui-combobox" name="isForce" data-options="required:true" style="width:150px">
                        <option value="0" selected="selected">不强制</option>
                        <option value="1">强制</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>是否激活:</label>
                    <select class="easyui-combobox" name="isActivate" data-options="required:true" style="width:150px">
                        <option value="0" selected="selected">未激活</option>
                        <option value="1">已激活</option>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/appVersionInfo.js"></script>
</body>
</html>
