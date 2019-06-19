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
                <label class="ui-label">停车场名称:</label>
                <input name="name" class="easyui-box ui-text" style="width:80px;">
                <label class="ui-label">停车场类型:</label>
                 <select class="easyui-combobox" name="type"   style="width:80px" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1" >车位</option>
                        <option value="2">充电桩</option>
                        <option value="3" >加油站</option>
                        <option value="4" selected="selected">停车场</option>
                        <option value="5">私人车位</option>
                        <option value="101" >系统车位</option>
                        <option value="102">系统停车场</option>
                    </select>
                <label class="ui-label">所在城市:</label>
                <input name="city" class="easyui-box ui-text" style="width:80px;">
                <label class="ui-label">所在区域:</label>
                <input name="area" class="easyui-box ui-text" style="width:80px;">
                <label class="ui-label">营业时间:</label>
                <input name="openTime" class="easyui-box ui-text" style="width:80px;">
                <label class="ui-label">联系电话:</label>
                <input name="phone" class="easyui-box ui-text" style="width:80px;">
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
                <div class="ftitle">停车场信息</div>
                <div class="fitem">
                    <label>停车场类型:</label>
                     <select class="easyui-combobox" name="type"   style="width:100px" data-options="editable:false,panelHeight:'auto'">
                        <option value="1" >车位</option>
                        <option value="2">充电桩</option>
                        <option value="3" >加油站</option>
                        <option value="4" selected="selected">停车场</option>
                        <option value="5">私人车位</option>
                        <option value="101" >系统车位</option>
                        <option value="102">系统停车场</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>停车场名称:</label>
                    <input class="easyui-validatebox" type="text" name="name" data-options="">
                </div>
                <div class="fitem">
                    <label>停车场标题:</label>
                    <input class="easyui-validatebox" type="text" name="title" data-options="">
                </div>
                <div class="fitem">
                    <label>停车场描述:</label>
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
                    <label>系统停车场编码:</label>
                    <input class="easyui-validatebox" type="text" name="parkCode" data-options="">
                </div>
                <div class="fitem">
                    <label>总车位:</label>
                    <input class="easyui-validatebox" type="text" name="totalSpace" data-options="">
                </div>
                <div class="fitem">
                    <label>剩余车位:</label>
                    <input class="easyui-validatebox" type="text" name="remainSpace" data-options="">
                </div>     
                <div class="fitem">
                    <label>经度:</label>
                    <input class="easyui-validatebox" type="text" name="mapLng" data-options="">
                </div>
                <div class="fitem">
                    <label>纬度:</label>
                    <input class="easyui-validatebox" type="text" name="mapLat" data-options="">
                </div>
                   <div class="fitem">
                    <label>封顶价格:</label>
                    <input class="easyui-validatebox" type="text" name="maxAmount" data-options="">
                </div>
                <div class="fitem">
                    <label>分时价格:</label>
                    <input class="easyui-validatebox" type="text" name="tenMinutePrice" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/parkInfo.js"></script>
</body>
</html>
