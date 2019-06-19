<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="/WEB-INF/jsp/view/resource.jsp"%>
</head>
<body>
	<div class="warp easyui-panel" data-options="border:false">
		<!-- Search panel start -->
		<div class="easyui-panel ui-search-panel" title="搜索框"
			data-options="striped: true,collapsible:true,iconCls:'icon-search'">
			<form id="searchForm">
				<p class="ui-fields">
					<table>
					  <tr>
					  <td>
					  <label class="ui-label">用户名:</label> 
					<input name="userName" class="easyui-box ui-text" style="width: 100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
					  <!-- <label class="ui-label">用户名:</label> 
					<input name="userName" class="easyui-box ui-text" style="width: 100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"> -->
					<label class="ui-label">车位编码:</label> 
					<input name="code" class="easyui-box ui-text" style="width: 100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
					<label class="ui-label">手机号:</label> 
					<input name="phone" class="easyui-box ui-text" style="width: 100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
					<label class="ui-label">状态:</label> 
         <select class="easyui-combobox" style="width:100px" name="state" data-options="editable:false,panelHeight:'auto',required:true" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
	                    <option value="">-请选择-</option>
	                    <option value="0">车主</option>
	                    <option value="1">共享</option>
	                </select>
					 <label class="ui-label">停车场编码:</label> 
					<input name="parkCode" class="easyui-box ui-text" style="width: 100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">
					<label class="ui-label">模式:</label> 
					<select class="easyui-combobox" style="width:100px" name="model" data-options="editable:false,panelHeight:'auto',required:true" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
	                    <option value="">-请选择-</option>
	                    <option value="0">短租</option>
	                    <option value="1">长租</option>
	                    <option value="2">未定义</option>
	                </select>
					  
					  </td>
					  
					  
					  </tr>
					   <tr>
					   <td>
					     
					<label class="ui-label">是否有效:</label> 
					<select class="easyui-combobox" style="width:100px" name="isExp" data-options="editable:false,panelHeight:'auto',required:true" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
	                    <option value="">-请选择-</option>
	                    <option value="1">有效</option>
	                    <option value="0">失效</option>
	                </select>
 	                <label class="ui-label">开始时间 :</label> 
                <input name="startTimeNew" class="easyui-box easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">  
				   <label class="ui-label">结束时间 :</label> 
                <input name="endTimeNew" class="easyui-box easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
					    <a href="#" id="btn-search" class="easyui-linkbutton"
					iconCls="icon-search">查询</a>
					   
					   </td>
					  <td></td>
					   </tr>
					
					</table>
					
					<!-- <label class="ui-label">车牌号码:</label> 
					<input name="plateNum" class="easyui-box ui-text" style="width: 100px;"  data-options="editable:false,panelHeight:'auto',required:true" > -->
					  
				</p>
				
			</form>
		</div>
		<!--  Search panel end -->

		<!-- Data List -->
		<form id="listForm" method="post">
			<table id="data-list"></table>
		</form>
		<!-- Edit Win&Form -->
		<div id="edit-win" class="easyui-dialog" title="编辑"
			data-options="closed:true,iconCls:'icon-save',modal:true"
			style="width: 400px; height: 380px;">
			<form id="editForm" class="ui-form" method="post">
				<input class="hidden" name="id">
				<div class="ui-edit">
					<div class="ftitle">停车场白名单表</div>
					<div class="fitem">
						<label>用户标识:</label> <input class="easyui-validatebox"
							validtype="integer" placeholder="请填写用户标识"  type="text" name="userId"
							data-options=""> <font color="red">*</font>
					</div>
					<div class="fitem">
						<label>用户名:</label> <input class="easyui-validatebox"
							validtype="userName" placeholder="请填写用户名" type="text" name="userName"
							data-options=""> <font color="red">*</font>
					</div>
					<div class="fitem">
						<label>车位编码:</label> <input class="easyui-validatebox" 
							validtype="code" placeholder="请填写车位编码" type="text" name="code"
							data-options=""> <font color="red">*</font>
					</div>
					<div class="fitem">
						<label>手机号:</label> <input class="easyui-validatebox" 
							validtype="phone" placeholder="请填写手机号" type="text" name="phone"
							data-options=""> <font color="red">*</font>
					</div>
			 		<div class="fitem">
						<label>状态:</label> 
							<select class="easyui-combobox" style="width:140px" name="state" data-options="editable:false,panelHeight:'auto'">
                        <option value="">-请选择状态-</option>
                            <option value="0">车主</option>
                            <option value="1">共享</option>
						</select>
						<font color="red">*</font>
					</div>
					<div class="fitem">
						<label>停车场编码:</label> <input class="easyui-validatebox"
							validtype="parkCode" placeholder="请填写停车场编码" type="text"
							name="parkCode" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>车牌号码:</label> <input class="easyui-validatebox"
							validtype="plateNum" placeholder="请填写车牌号码" type="text"
							name="plateNum" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>模式:</label> <select class="easyui-combobox" style="width:140px" name="model" data-options="editable:false,panelHeight:'auto'">
                        <option value="">-请选择车位模式-</option>
                            <option value="0">短租</option>
                            <option value="1">长租</option>
						</select>
							 <font color="red">*</font>
					</div>
					<div class="fitem">
						<label>开始时间:</label> <input class="easyui-box easyui-datetimebox"
							validtype="startTime" placeholder="请填写开始时间" type="text"
							name="startTime" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>结束时间:</label> <input class="easyui-box easyui-datetimebox"
							validtype="endTime" placeholder="请填写开始时间" type="text"
							name="endTime" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>创建时间:</label> <input class="easyui-box easyui-datetimebox"
							validtype="createTime" placeholder="请填写开始时间" type="text"
							name="createTime" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>失效时间:</label> <input class="easyui-box easyui-datetimebox"
							validtype="expTime" placeholder="请填写开始时间" type="text"
							name="expTime" data-options=""> <font
							color="red">*</font>
					</div>
					<div class="fitem">
						<label>是否有效:</label> <select class="easyui-combobox" style="width:140px" name="isExp" data-options="editable:false,panelHeight:'auto'">
                        <option value="">-请选择车位模式-</option>
                            <option value="0">失效</option>
                            <option value="1">有效</option>
						</select> <font
							color="red">*</font>
					</div>
					
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript"
		src="${msUrl}/static/js/commons/YDataGrid.js"></script>
	<script type="text/javascript"
		src="${msUrl}/static/js/ux/park/parkWhiteList.js"></script>
	<script type="text/javascript"
		src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
