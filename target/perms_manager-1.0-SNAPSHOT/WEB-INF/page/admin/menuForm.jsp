<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>添加管理员</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx }/layui/css/layui.css" media="all" />
<script>  
        <%--JS gloable varilible--%>  
        var ctx = "${ctx}";  
    </script>  
<style type="text/css">
.layui-form-item .layui-inline {
	width: 33.333%;
	float: left;
	margin-right: 0;
}

@media ( max-width :1240px) {
	.layui-form-item .layui-inline {
		width: 100%;
		float: none;
	}
}
</style>
</head>
<body class="childrenBody">
	<form class="layui-form" style="width: 80%;" id="menuF">
		<input type="hidden" name="menuId" value="${menu.menuId }"/>
		<input type="hidden" name="parentId" value="${menu.parentId }"/>
		<input type="hidden" name="flag" value="${flag }"/>
		<div class="layui-form-item">
			<label class="layui-form-label">菜单名</label>
			<div class="layui-input-block">
				<input type="text" id="title" class="layui-input userName"
					lay-verify="required" placeholder="请输入菜单名" name="title" value="${menu.title }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标</label>
			<div class="layui-input-block">
				<input type="text" id="iconPicker" lay-filter="iconPicker" class="layui-input userName"
					 placeholder="请填写图标代码" name="icon" value="${menu.icon }">
					<label></label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">资源路径</label>
			<div class="layui-input-block">
				<input type="text" name="href" class="layui-input userName"
					 placeholder="请输入资源路径" value="${menu.href }">
					<label>（<span style="color: red">*</span>例：log/logList）</label>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="number" name="sorting" class="layui-input userName"
					placeholder="排序(整数)" lay-verify="require" value="${menu.sorting }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限标识</label>
			<div class="layui-input-block">
				<input type="text" name="perms" class="layui-input userName"
					placeholder="权限标识" value="${menu.perms }">
				<label>（例：sys:role:list）</label>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="menuForm">立即提交</button>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx }/page/admin/menuForm.js"></script>
</body>
</html>