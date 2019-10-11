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
	<form class="layui-form" style="width: 80%;" id="aaf">
		<div class="layui-form-item">
			<label class="layui-form-label">描述信息</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input userName"
					lay-verify="required" placeholder="请输入描述信息" name="remark" value="">
			</div>
		</div>

		<!--轮播图片  -->
		<div class="layui-form-item">
		<label class="layui-form-label">新闻封面</label>
		  <div class="layui-input-block">
		    <button type="button" class="layui-btn" id="onePicUpload"><i class="layui-icon"></i>上传文件</button><br />
			<img class="layui-upload-img" id="preview" style="width: 150px;margin-top: 5px">
			<p id="uploadError"></p>
			<input type="hidden" lay-verify="imgurl" name="imgurl" id="newsImg" value=""/>
		  </div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">链接地址</label>
			<div class="layui-input-block">
				<input type="text" name="imglink" class="layui-input userName"
					lay-verify="url" placeholder="链接地址" value="">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">排序</label>
			<div class="layui-input-block">
				<input type="text" name="sorting" class="layui-input userName"
					lay-verify="number" placeholder="排序" value="">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">是否展示</label>
			<div class="layui-input-block">
				<input type="radio" name="status" value="1" title="是" checked>
				<input type="radio" name="status" value="0" title="否">
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="addCarousel">立即提交</button>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx }/page/carousel/addCarousel.js"></script>
</body>
</html>