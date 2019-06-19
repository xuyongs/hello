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
		<style type="text/css">
		html,body{margin:0px; padding:0px; width:100%; height:100%; font-size:14px;}
		.con_left{float:left; width:350px; height:100%; overflow:scroll;}
		.con_right{margin-left:350px; height:100%; overflow:scroll;}
		#content_api{ padding:10px;}
		.head_title{text-align:center; font-size:24px; margin:10px; font-weight: bold;}
		.ol_apis{ padding:5px; font-family:tahoma,Arial,Microsoft YaHei,\5b8b\4f53; list-style:none;}
		.ol_apis li{background:#eee;border-radius:5px; margin-bottom:5px; padding:5px;}
		.ol_apis a{display:inline-block;text-decoration:none;}
		.ol_apis a:hover{color:#F90;}
		.ol_apis .api_title { font-size:15px; font-weight: bold; cursor:pointer;}
		.fun_comment{ background:f3f5f7; }
		.float_r{ float:right;}
		.hidden{ display:none;}
		.clear_both{clear:both; }
		</style>
		<script charset="utf-8" src="${pageContext.request.contextPath }/resources/plugs/jquery/jquery.js"></script>
		<script type="text/javascript">
		$(function(){
			$(".api_title").click(function(){
				var tar = $(this);
				$("#content_api").html($("#description"+tar.attr("data")).html());
				console.log($("#description"+tar.attr("data")).html());
			});
		});
		function apilistDel(id){
			if(confirm("确定删除?")){
				window.location.href = "${pageContext.request.contextPath }/apilist/del/"+id+".do";
			}
		}
		</script>
</head>
<body>
	<div class="con_left">
		<div style="padding:10px;">
			<div class="head_title">API列表 <font style="font-size:small; color:#666;">[点击API标题可查看详情]</font></div>
			<div style="text-align:right;"><a href="${pageContext.request.contextPath }/apilist/toadd.do">新增API</a></div>
			<ol class="ol_apis">
				<c:forEach items="${list }" var="item" varStatus="vs">
				<li class="ol_api">
					<div>
						<span class="api_title" data="${item.id }">${vs.index+1 }、${item.title }</span>
						<a class="float_r" href="javascript:;" onclick="apilistDel(${item.id})">删除</a>
						<a class="float_r" href="${pageContext.request.contextPath }/apilist/edit/${item.id}.do">编辑&nbsp;&nbsp;</a>
						<div class="clear_both"></div>
					</div>
					<div class="fun_comment hidden" id="description${item.id }">${item.description }</div>
				</li>
				</c:forEach>
			</ol>
		</div>
	</div>
	<div class="con_right">
		<h4 style="color:#F00; text-indent:15px">注:严格按照规则来调用接口，get请求参数需要URLEcoder编码，post请求参数不需.需要认证的API，必须带上参数:token(请求令牌)<%--需要认证的API，必须带上参数:user_id(用户id),access_token(请求令牌)--%></h4>
		<div id="content_api"></div>
	</div>
</body>
</html>