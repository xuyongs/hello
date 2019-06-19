<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
          
              
                 <label class="ui-label">停车场编码:</label><input name="parkCode" class="easyui-box ui-text"
                                                             style="width:100px;"
                                                       onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"      
                                                             >
                 <label class="ui-label">车辆类型:</label><select class="easyui-combobox" style="width:100px" name="type"
								data-options="editable:false,panelHeight:'auto',required:true"
								onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
					<option value="">-请选择车辆类型-</option>
					<option value="0">短租车辆</option> 
					<option value="1">月租车辆</option>
					<!-- <option value="2">免费车</option>
					<option value="3">临时车</option>
					<option value="4">月租车(临时车)</option>
					<option value="5">月租车(月租车)</option>
					<option value="6">黑名单车</option>
					<option value="7">异常临时车(保留)</option>
					<option value="8">预约车</option>
					<option value="9">租赁车(保留)</option>
					<option value="10">充值车</option>
					<option value="11">一人多车</option>
					<option value="12">错时停车</option> -->
					
					</select>
                  <label class="ui-label">停车场名称:</label><input name="name" class="easyui-box ui-text" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$,'')">                                             
                 <label class="ui-label">所在城市:</label><input name="city" class="easyui-box ui-text"
                                                             style="width:100px;" onkeyup="this.value=this.value.replace(/^ +| +$,'')">
                  <label class="ui-label">所在区域:</label><input name="area" class="easyui-box ui-text"
                                                             style="width:100px;" onkeyup="this.value=this.value.replace(/^ +| +$,'')">
            
             
              
                  <label class="ui-label">营业时间:</label><input name="openTime" class="easyui-box ui-text"
                                                             style="width:100px;" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                  <label class="ui-label">&nbsp;&nbsp;&nbsp; &nbsp;联系电话:</label><input name="phone" class="easyui-box ui-text"
                                                             style="width:100px;" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"> 
                  <label class="ui-label">开始时间:</label><input name="startTime" class="easyui-box easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                  <label class="ui-label">结束时间:</label><input name="endTime" class="easyui-box easyui-datetimebox" style="width:100px;"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
               
               
               <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
           
                
                                                                                                                                                                                                          
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
         data-options="closed:true,iconCls:'icon-save',modal:true" style="width:400px;height:380px;">
        <form id="editForm" class="ui-form" method="post">
            <input class="hidden" name="id">
            <div class="ui-edit">
                <div class="ftitle">停车场系统信息</div>
                <div class="fitem">
                    <label>停车场编码:</label>
                    <input class="easyui-validatebox" validtype="parkCode" placeholder="请填写停车场编码" type="text" name="parkCode" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
	                    <label>停车类型:</label>
	                <select class="easyui-combobox" style="width:138px" name="state"
								data-options="editable:false,panelHeight:'auto'"
								onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
					<option value="">-停车类型-</option>
					<option value="0">月租车</option>
					<option value="1">临时车</option>
					<option value="2">免费车</option>
					<option value="3">临时车</option>
					<option value="4">月租车(临时车)</option>
					<option value="5">月租车(月租车)</option>
					<option value="6">黑名单车</option>
					<option value="7">异常临时车(保留)</option>
					<option value="8">预约车</option>
					<option value="9">租赁车(保留)</option>
					<option value="10">充值车</option>
					
					</select> <font color="red">*</font>
                
                </div>
                <div class="fitem">
                    <label>停车场名称:</label>
                    <input class="easyui-validatebox" validtype="CHS" missingMessage="中文或英文" placeholder="请填写停车场名称" type="text" name="name" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>停车场标题:</label>
                    <input class="easyui-validatebox" type="text" placeholder="请填写停车场标题" name="title" data-options="">
                   <font color="red">*</font>
                    
                </div>
                <div class="fitem">
                    <label>停车场描述:</label>
                    <input class="easyui-validatebox" type="text" name="des" placeholder="请填写停车场描述" data-options="">
                                <font color="red">*</font>
                
                </div>
                <div class="fitem">
                    <label>收费描述:</label>
                    <input class="easyui-validatebox" validtype="" placeholder="请填写收费描述" missingMessage="按小时" type="text" name="priceDes" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>所在城市:</label>
                    <input class="easyui-validatebox" validtype="CHS" placeholder="请填写所在城市" missingMessage="中文或英文" type="text" name="city" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>所在区域:</label>
                    <input class="easyui-validatebox" validtype="CHS" placeholder="请填写所在区域" missingMessage="中文或英文" type="text" name="area" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>具体地址:</label>
                    <input class="easyui-validatebox" validtype="CHS" placeholder="请填写具体地址" missingMessage="中文或英文" type="text" name="addr" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>营业时间:</label>
                    <input class="easyui-validatebox" validtype="openTime" placeholder="请填写营业时间" type="text" name="openTime" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>联系人:</label>
                    <input class="easyui-validatebox" validtype="name" placeholder="请填写联系人" missingMessage="中文或英文" type="text" name="contacter" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>联系电话:</label>
                    <input class="easyui-validatebox" validtype="phone" placeholder="请填写联系电话" type="text" name="phone" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>总车位:</label>
                    <input id="totalSpaceNum" class="easyui-numberbox" placeholder="请填写总车位" type="text" name="totalSpace" data-options="min:0">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>剩余车位:</label>
                    <input class="easyui-numberbox" validtype="" placeholder="请填写剩余车位" missingMessage="不能多于总车位" type="text" name="remainSpace" data-options="min:0">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>经度:</label>
                    <input class="easyui-validatebox" placeholder="请填写经度" type="text" name="mapLng" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>纬度:</label>
                    <input class="easyui-validatebox" placeholder="请填写维度" type="text" name="mapLat" data-options="">
                <font color="red">*</font>
                </div> 
                <div class="fitem">
                    <label>施工状态:</label>
                    <select class="easyui-combobox" style="width:138px" name="constructionType" data-options="editable:false,panelHeight:'auto'">
                        <option value="0">-施工状态-</option>
                            <option value="1">装修完成正在使用</option>
                            <option value="2">正在装修</option>
                            <option value="3">未装修</option>
                    </select>
               <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>封顶金额:</label>
                    <input class="easyui-validatebox" validtype="integer" placeholder="请填写封顶金额" missingMessage="单位：元" type="text" name="maxAmountDob" data-options="">
                <font color="red">*</font>
                </div>
                <div class="fitem">
                    <label>每分钟价格:</label>
                    <input class="easyui-validatebox" validtype="integer" placeholder="请填写每分钟价格" missingMessage="单位：元" type="text" name="tenMinutePriceDob" data-options="">
              <font color="red">*</font>
                 </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${msUrl}/static/js/commons/YDataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkSysInfo.js"></script>
<script type="text/javascript" src="${msUrl}/static/js/ux/park/parkReg.js"></script>
</body>
</html>
