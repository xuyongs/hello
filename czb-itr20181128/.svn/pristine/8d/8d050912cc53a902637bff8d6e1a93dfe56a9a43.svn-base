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
                <label class="ui-label">登录名:</label>
                <input name="loginName" class="easyui-box ui-text" style="width:80px;" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            <label class="ui-label">手机号:</label>
                <input name="phone" class="easyui-box ui-text" style="width:80px;" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            <label class="ui-label">停车场编码:</label>
                <input name="parkCode" class="easyui-box ui-text" style="width:80px;" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            
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
                <div class="ftitle">停车场系统用户</div>
                <div class="fitem">
                    <label>登录名称:</label>
                    <input class="easyui-validatebox" validtype="loginName"  missingMessage="字母开头，允许2-16字节，允许字母数字下划线" type="text" name="loginName" >
                	
                </div>
                <div class="fitem">
                    <label>登录密码:</label>
                    <input id="fitemLoginPWD" class="easyui-validatebox" validtype=" missingMessage="字母开头，允许2-16字节，允许字母数字下划线" type="text" name="loginPwd" >
                
                </div>
                <div class="fitem">
                    <label>手机:</label>
                    <input class="easyui-validatebox" validtype="phone" type="text" name="phone" >
                
                </div>
                <div class="fitem">
                    <label>电子邮箱:</label>
                    <input class="easyui-validatebox" type="text" name="email" >
                </div>
                <div class="fitem">
                    <label>用户姓名:</label>
                    <input class="easyui-validatebox" validtype="name"  missingMessage="中文或英文" type="text" name="userName" >
                
                </div>
                <div class="fitem">
                    <label>身份证号:</label>
                    <input class="easyui-validatebox" validtype="idNumber" ptype="text" name="idNumber" >
                
                </div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode"  type="text" name="parkCode" >
                
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/partSysUser.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
