<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="renderer" content="webkit" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>功能清单</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/main.css" />
		<script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugs/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugs/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugs/jquery/jquery.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath }/resources/js/open/apilist/edit.js"></script>
	</head>
<body>
	<div class="content">
		<a href="${pageContext.request.contextPath }/apilist/index.do">返回列表</a>
		<h1 class="line">新增/编辑API接口信息</h1>
		<form id="form" action="${pageContext.request.contextPath }/apilist/save.do" method="post">
			<input type="hidden" name="id" value="${model.id }">
			<div class="form-row">
				接口名称:&nbsp;<input class="form-input w5" type="text" name="title" value="${model.title }">
			</div>
			<input type="hidden" id="description" name="description">
			<textarea id="editor_id" style="width:100%;height:500px;">
			${model.description }
			<c:if test="${model==null }">
			<p><strong>请求说明：</strong></p><p>URL: http://localhost:8080/</p><p>http请求方式:GET/POST</p><p><br /></p><p><strong>请求参数：</strong></p><p>无</p><p><br /></p><p><strong>返回参数:</strong></p><p>无</p><p>	<br /></p>
			</c:if>
			</textarea>
			<div class="form-row">
				<input class="button" id="submitBtn" type="button" value="提交"> <font class="tip_font">提交成功请在列表中检测一下添加的功能是否可以显示</font>
			</div>
		</form>
	</div>
</body>
</html>