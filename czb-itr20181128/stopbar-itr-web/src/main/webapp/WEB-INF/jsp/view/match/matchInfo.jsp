<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <label class="ui-label">Name:</label>
                <input name="name" class="easyui-box ui-text" style="width:100px;">
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">Search</a>
        </form>
    </div>
    <!--  Search panel end -->

    <!-- Data List -->
    <form id="listForm" method="post">
        <table id="data-list"></table>
    </form>
    <!-- Edit Win&Form -->
    <div id="edit-win" class="easyui-dialog" title="Basic window"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="editForm" class="ui-form" method="post">
                    <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">MatchInfo Information</div>
                <div class="fitem">
                    <label>比赛名称:</label>
                    <input class="easyui-validatebox" type="text" name="matchName" data-options="">
                </div>
                <div class="fitem">
                    <label>比赛描述:</label>
                    <input class="easyui-validatebox" type="text" name="matchDesc" data-options="">
                </div>
                <div class="fitem">
                    <label>比赛时间:</label>
                    <input class="easyui-validatebox" type="text" name="matchTime" data-options="">
                </div>
                <div class="fitem">
                    <label>联系人:</label>
                    <input class="easyui-validatebox" type="text" name="contacts" data-options="">
                </div>
                <div class="fitem">
                    <label>联系电话:</label>
                    <input class="easyui-validatebox" type="text" name="phone" data-options="">
                </div>
                <div class="fitem">
                    <label>是否生效:</label>
                    <select class="easyui-combobox" name="isValid" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">生效</option>
                        <option value="2">失效</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>状态:</label>
                    <select class="easyui-combobox" name="state" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">普通</option>
                        <option value="2">置顶</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>区域:</label>
                    <select class="easyui-combobox" name="regionId" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">西安</option>
                        <option value="2">咸阳</option>
                        <option value="3">宝鸡</option>
                        <option value="4">杨凌</option>
                        <option value="5">汉中</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>备注:</label>
                    <input class="easyui-validatebox" type="text" name="remark" data-options="">
                </div>
            </div>
        </form>
    </div>

    <!-- SaveFile Win&Form -->
    <div id="save-file-win" class="easyui-dialog" buttons="#SaveFilebtn" title="Save File"
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="saveFile" class="ui-form" method="post" enctype="multipart/form-data" >
            <div class="ui-edit">
                <div class="fitem">
                    <label>比赛名称:</label>
                    <input class="easyui-validatebox" type="text" name="matchName" data-options="">
                </div>
                <div class="fitem">
                    <label>比赛描述:</label>
                    <input class="easyui-validatebox" type="text" name="matchDesc" data-options="">
                </div>
                <div class="fitem">
                    <label>比赛时间:</label>
                    <input class="easyui-validatebox" type="text" name="matchTime" data-options="">
                </div>
                <div class="fitem">
                    <label>联系人:</label>
                    <input class="easyui-validatebox" type="text" name="contacts" data-options="">
                </div>
                <div class="fitem">
                    <label>联系电话:</label>
                    <input class="easyui-validatebox" type="text" name="phone" data-options="">
                </div>
<%--                <div class="fitem">
                    <label>是否生效:</label>
                    <select class="easyui-combobox" name="isValid" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">生效</option>
                        <option value="2">失效</option>
                    </select>
                </div>--%>
                <div class="fitem">
                    <label>状态:</label>
                    <select class="easyui-combobox" name="state" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">普通</option>
                        <option value="2">置顶</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>区域:</label>
                    <select class="easyui-combobox" name="regionId" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="1">西安</option>
                        <option value="2">咸阳</option>
                        <option value="3">宝鸡</option>
                        <option value="4">杨凌</option>
                        <option value="5">汉中</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>备注:</label>
                    <input class="easyui-validatebox" type="text" name="remark" data-options="">
                </div>
                <div class="ui-edit">
                    <div class="ftitle">文件上传</div>
                    <div class="fitem">
                        <label>文件上传:</label>
                        <input type="file" name="fileUrl" data-options="">
                    </div>
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
<script type="text/javascript" src="${msUrl}/static/js/ux/match/matchInfo.js"></script>
</body>
</html>
