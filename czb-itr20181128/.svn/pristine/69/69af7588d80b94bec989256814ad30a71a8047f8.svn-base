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
                
                <label class="ui-label">手机号:</label>
                <input name="phone" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">登录名称:</label>
                <input name="loginName" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">昵称:</label>
                <input name="nickName" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                
                <label class="ui-label">开 始 时 间 :</label>
                <input name="startTime" class="easyui-box easyui-datetimebox" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">结 束 时 间 :</label>
                <input name="endTime" class="easyui-box easyui-datetimebox" style="width:95px	;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" style="margin-top:3px" iconCls="icon-search">查询</a>
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
                <div class="ftitle">用户表</div>
                 <div class="fitem">
                    <label>手机号:</label>
                    <input class="easyui-validatebox" type="text" name="phone" data-options="">
                </div>
                <div class="fitem">
                    <label>登录名称:</label>
                    <input class="easyui-validatebox" type="text" name="loginName" data-options="">
                </div>
                <div class="fitem">
                    <label>登录密码:</label>
                    <input class="easyui-validatebox" type="text" name="loginPwd" data-options="">
                </div>
                <div class="fitem">
                    <label>昵称:</label>
                    <input class="easyui-validatebox" type="text" name="nickName" data-options="">
                </div>
               
                <div class="fitem">
                    <label>个性签名:</label>
                    <input class="easyui-validatebox" type="text" name="signa" data-options="">
                </div>
                <div class="fitem">
                    <label>年龄:</label>
                    <input class="easyui-validatebox" type="text" name="age" data-options="">
                </div>
                <div class="fitem">
                    <label>性别:</label>
                    <select class="easyui-combobox" name="sex"  data-options="required:true" style="width:150px">
                        <option value="">-请选择-</option>          
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>头像地址:</label>
                    <input class="easyui-validatebox" type="text" name="imgUrl" data-options="">
                </div>
                <div class="fitem">
                    <label>电子邮箱:</label>
                    <input class="easyui-validatebox" type="text" name="email" data-options="">
                </div>
                <div class="fitem">
                    <label>实名认证:</label>
                    <select class="easyui-combobox" name="isAuth"  data-options="required:true" style="width:150px">
                        <option value="">-请选择-</option>          
                        <option value="0">未认证</option>
                        <option value="1">已认证</option>
                    </select>
                    
                </div>
                <div class="fitem">
                    <label>用户姓名:</label>
                    <input class="easyui-validatebox" type="text" name="userName" data-options="">
                </div>
                <div class="fitem">
                    <label>身份证号:</label>
                    <input class="easyui-validatebox" type="text" name="idNumber" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/sys/userInfo.js"></script>
</body>
</html>
