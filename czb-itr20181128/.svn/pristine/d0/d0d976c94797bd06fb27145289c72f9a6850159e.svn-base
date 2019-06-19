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
                <label class="ui-label">字典名称:</label>
                <input name="dictName" class="easyui-box ui-text" style="width:100px;">
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
            <input class="hidden" name="dictId">
            <div class="ui-edit">
                <div class="ftitle">字典明细</div>
                <div class="fitem">
                    <label>父字典id:</label>
                    <input class="easyui-validatebox"  type="text" name="parentDictId" data-options="">
                </div>
                <div class="fitem">
                    <label>字典名称:</label>
                    <input class="easyui-validatebox"  type="text" name="dictName" data-options="">
                </div>
                <div class="fitem">
                    <label>字典代码:</label>
                    <input class="easyui-validatebox"  type="text" name="dictCode" data-options="">
                </div>
                <div class="fitem">
                    <label>字典key:</label>
                    <input class="easyui-validatebox" type="text" name="dictKey" data-options="">
                </div>
                <div class="fitem">
                    <label>字典value:</label>
                    <input class="easyui-validatebox"  type="text" name="dictValue" data-options="">
                </div>
                <div class="fitem">
                    <label>说明:</label>
                    <input class="easyui-validatebox"  type="text"  name="note" data-options="">
                </div>
                <div class="fitem">
                    <label>创建人:</label>
                    <input class="easyui-validatebox"  type="text"  name="userId" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/system/dataDict.js"></script>
</body>
</html>
