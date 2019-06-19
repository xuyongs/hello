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
                <label class="ui-label">广告标题:</label>
                <input name="title" class="easyui-box ui-text" style="width:100px;">
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
    <div id="edit-win" class="easyui-dialog" title="基本信息"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="editForm" class="ui-form" method="post">
                    <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">广告信息</div>
                <div class="fitem">
                    <label>广告序号:</label>
                    <input class="easyui-validatebox" type="text" name="seq" data-options="">
                </div>
                <div class="fitem">
                    <label>广告标题:</label>
                    <input class="easyui-validatebox" type="text" name="title" data-options="">
                </div>
                <div class="fitem">
                    <label>链接地址:</label>
                    <input class="easyui-validatebox" type="text" name="linkUrl" data-options="">
                </div>
            </div>
        </form>
    </div>

    <div id="save-file-win" class="easyui-dialog" buttons="#SaveFilebtn" title="编辑"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="saveFile" class="ui-form" method="post" action="" enctype="multipart/form-data" >
            <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">广告信息</div>
                <div class="fitem">
                    <label>广告序号:</label>
                    <input class="easyui-validatebox" type="text" name="seq" data-options="required:true">
                </div>
                <div class="fitem">
                    <label>广告标题:</label>
                    <input class="easyui-validatebox" type="text" name="title" data-options="required:true">
                </div>
                <div class="fitem">
                    <label>链接地址:</label>
                    <input class="easyui-validatebox" type="text" name="linkUrl" data-options="required:true">
                </div>
                <div class="fitem">
                    <label>图片上传:</label>
                    <input type="file" id="imgFile" name="imgFile" data-options="required:true">
                </div>
            </div>
        </form>
        <div id="SaveFilebtn" class="dialog-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="save-file-submit">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="save-file-close">Close</a>
        </div>
    </div>

</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/advertInfo.js"></script>
</body>
</html>
