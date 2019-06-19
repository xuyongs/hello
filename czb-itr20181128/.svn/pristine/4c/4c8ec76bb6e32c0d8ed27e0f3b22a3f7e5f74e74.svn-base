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
                <label class="ui-label">设备名称:</label>
                <input name="name" class="easyui-box ui-text" style="width:100px;">
                <label class="ui-label">开始时间:</label>
                <input name="startTime" class="easyui-datetimebox" style="width:100px;">
                <label class="ui-label">结束时间:</label>
                <input name="endTime" class="easyui-datetimebox" style="width:100px;">
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
                <div class="ftitle">设备服务信息</div>
                <div class="fitem">
                    <label>设备类型:</label>
                    <input class="easyui-validatebox" type="text" name="type" data-options="">
                </div>
                <div class="fitem">
                    <label>设备名称:</label>
                    <input class="easyui-validatebox" type="text" name="name" data-options="">
                </div>
                <div class="fitem">
                    <label>设备标题:</label>
                    <input class="easyui-validatebox" type="text" name="title" data-options="">
                </div>
                <div class="fitem">
                    <label>设备描述:</label>
                    <input class="easyui-validatebox" type="text" name="des" data-options="">
                </div>
                <div class="fitem">
                    <label>收费描述:</label>
                    <input class="easyui-validatebox" type="text" name="priceDes" data-options="">
                </div>
                <div class="fitem">
                    <label>所在城市:</label>
                    <input class="easyui-validatebox" type="text" name="city" data-options="">
                </div>
                <div class="fitem">
                    <label>所在区域:</label>
                    <input class="easyui-validatebox" type="text" name="area" data-options="">
                </div>
                <div class="fitem">
                    <label>具体地址:</label>
                    <input class="easyui-validatebox" type="text" name="addr" data-options="">
                </div>
                <div class="fitem">
                    <label>营业时间:</label>
                    <input class="easyui-validatebox" type="text" name="openTime" data-options="">
                </div>
                <div class="fitem">
                    <label>联系人:</label>
                    <input class="easyui-validatebox" type="text" name="contacter" data-options="">
                </div>
                <div class="fitem">
                    <label>联系电话:</label>
                    <input class="easyui-validatebox" type="text" name="phone" data-options="">
                </div>
                <div class="fitem">
                    <label>图片地址:</label>
                    <input class="easyui-validatebox" type="text" name="imgUrl" data-options="">
                </div>
                <div class="fitem">
                    <label>状态:</label>
                    <input class="easyui-validatebox" type="text" name="state" data-options="">
                </div>
                <div class="fitem">
                    <label>上传人:</label>
                    <input class="easyui-validatebox" type="text" name="userId" data-options="">
                </div>
                <div class="fitem">
                    <label>创建时间:</label>
                    <input class="easyui-datetimebox" type="text" name="createTime" data-options="">
                </div>
                <div class="fitem">
                    <label>修改时间:</label>
                    <input class="easyui-datetimebox" type="text" name="updateTime" data-options="">
                </div>
                <div class="fitem">
                    <label>是否删除:</label>
                    <input class="easyui-validatebox" type="text" name="isDel" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/serveInfo.js"></script>
</body>
</html>
