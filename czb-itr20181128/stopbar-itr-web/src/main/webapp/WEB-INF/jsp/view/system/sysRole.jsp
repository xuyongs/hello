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
                <label class="ui-label">角色名称:</label>
                <input name="roleName" class="easyui-box ui-text" style="width:80px;">
                <label class="ui-label">状态:</label>
                 <select class="easyui-combobox" style="width:80px" name="state" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
	                    <option value="0">可用</option>
	                    <option value="1">禁用</option>
	             </select>
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
        </form>
    </div>
    <!--  Search panel end -->

    <!-- DataList  -->
    <form id="listForm" method="post">
        <table id="data-list"></table>
    </form>
    <!-- Edit Form -->
    <div id="edit-win" class="easyui-dialog" title="编辑" data-options="closed:true,iconCls:'icon-save',modal:true"
         style="width:400px;height:410px;">
        <form id="editForm" class="ui-form" method="post">
            <input class="hidden" type="text" name="id">

            <div class="ui-edit">
                <div class="ftitle">角色信息</div>
                <div class="fitem">
                 <label>角色名称:</label>
                <input class="easyui-validatebox"  type="text" name="roleName" data-options="validType:'length[1,10]'"/>             
          </div>
                <div class="fitem">
                    <label>状态:</label>
                    <select class="easyui-combobox" name="state" >
                        <option value="0" selected="selected">可用</option>
                        <option value="1">禁用</option>
                    </select>
                </div>
               
                <div class="fitem">
                    <label>描述:</label>
                    <textarea class="easyui-validatebox" data-options="length:[0,100]" name="descr"></textarea>
                </div>
                
                
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGridd.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/system/sysRole.js"></script>

</script>
</body>
</html>
