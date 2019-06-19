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
                <label class="ui-label">用户姓名:</label>
                <input name="userName" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">停车场编码:</label>
                <input name="parkCode" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">车牌号码:</label>
                <input name="plateNum" class="easyui-box ui-text" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                
                <label class="ui-label">订单类型:</label>
	                <select class="easyui-combobox" style="width:95px" name="type" data-options="editable:false,panelHeight:'auto',required:true">
	                    <option value="">-请选择-</option>
                        <c:forEach items="${typeList }" var="item" varStatus="vs">
                            <option value="${item.key }">${item.val }</option>
                        </c:forEach>
	                </select>
                <label class="ui-label">状态:</label>
                	<select class="easyui-combobox" size="6" style="width:95px" name="state" data-options="editable:false,panelHeight:'300',required:true">
	                    <option value="">-请选择-</option>
                        <c:forEach items="${stateList }" var="item" varStatus="vs">
                            <option value="${item.key }">${item.val }</option>
                        </c:forEach>
	                </select>
                <label class="ui-label">开 始 时 间 :</label>
                <input name="startTimeNew" class="easyui-box easyui-datetimebox" style="width:95px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                <label class="ui-label">结 束 时 间 :</label>
                <input name="endTimeNew" class="easyui-box easyui-datetimebox" style="width:95px	;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
            
            </p>
            <a href="#" id="btn-search" class="easyui-linkbutton" style="float:right" iconCls="icon-search">查询</a>
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
                <div class="ftitle">停车场订单信息</div>
                <div class="fitem">
                    <label>用户标识:</label>
                    <input class="easyui-validatebox" validtype="integer" placeholder="请填写用户标识"  missingMessage="整数" type="text" name="userId" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>用户姓名:</label>
                    <input class="easyui-validatebox" validtype="name" placeholder="请填写用户姓名"  missingMessage="中文或英文" type="text" name="userName" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode" placeholder="请填写停车场编码" type="text" name="parkCode" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>车牌号码:</label>
                    <input class="easyui-validatebox" validtype="plateNum" placeholder="车牌号码" type="text" name="plateNum" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>金额:</label>
                    <input class="easyui-validatebox" validtype="integer" placeholder="金额" missingMessage="单位：元，整额。" type="text" name="totalPrice" data-options="required:true">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>订单类型:</label>
                    <select class="easyui-combobox" style="width:150px" name="type" data-options="editable:false,panelHeight:'auto',required:true">
                        <option value="">-请选择-</option>
                        <c:forEach items="${typeList }" var="item" varStatus="vs">
                            <option value="${item.key }">${item.val }</option>
                        </c:forEach>
                    </select>
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>状态:</label>
                    <select class="easyui-combobox" style="width:150px" name="state" data-options="editable:false,panelHeight:'280',required:true">
                        <option value="">-请选择-</option>
                        <c:forEach items="${stateList }" var="item" varStatus="vs">
                            <option value="${item.key }">${item.val }</option>
                        </c:forEach>
                    </select>
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>关联标识:</label>
                    <input class="easyui-validatebox" type="text" name="refId" data-options="">
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkOrderInfo.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
