<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@include file="/WEB-INF/jsp/view/resource.jsp" %>
</head>
<body>
<div class="warp easyui-panel" data-options="border:false">
    <!-- Search panel start -->
    <div class="easyui-panel ui-search-panel" title="搜索框" data-options="striped: true,collapsible:true,iconCls:'icon-search'">
        <form id="searchForm">
            <input class="hidden" id='search_parentId' name="parentId">

            <p class="ui-fields">
                <label class="ui-label">名称:</label>
                <input name="name" class="easyui-box ui-text" style="width:100px;">
            </p>
            <a id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </form>
    </div>
    <!--  Search panel end -->
    <!-- DataList  -->
    <form id="listForm" method="post">
        <table id="data-list"></table>
    </form>

    <!-- Edit Win&From -->
    <div id="edit-win" class="easyui-dialog" title="编辑" data-options="closed:true,iconCls:'icon-save',modal:true"
         style="width:500px;height:450px;">
        <form id="editForm" class="ui-form" method="post">
            <!-- 隐藏文本框 -->
          <input class="hidden" name="id">
          <input class="hidden" name="deleted">
          <input class="hidden" name="parentId" id='edit_parentId'>
               <div data-options="region:'north',split:true" style="height:185px;padding:10px">
                        <div class="ftitle">系统菜单</div>
                        <div class="fitem">
                            <label >名称:</label>
                            <input  class="easyui-validatebox"  type="text" name="name" data-options="required:true">
                        </div>
                        <div class="fitem">
                            <label>URL:</label>
                            <input type="text"  name="url" >
                        </div>
                        <div class="fitem">
                            <label>排名:</label>
                            <input class="easyui-numberbox" type="text"   name="rank" data-options="required:true,min:0,max:999">
                        </div>
                    </div>
        </form>
    </div>


<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/system/sysMenu.js"></script>
</body>
</html>
