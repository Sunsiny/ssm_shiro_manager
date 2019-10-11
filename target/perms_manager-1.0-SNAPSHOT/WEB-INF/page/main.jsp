<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>后台首页</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css" media="all" />
<link rel="stylesheet" href="${ctx}/css/font_eolqem241z66flxr.css"
	media="all" />
<link rel="stylesheet" href="${ctx}/css/main.css" media="all" />
<script type="text/javascript" src="${ctx}/js/echarts.js"></script>
<script>
	
<%--JS gloable varilible--%>
	var ctx = "${ctx}";
</script>
</head>
<body class="childrenBody" style="margin: 1%">
	<blockquote class="layui-elem-quote">
		<p>欢迎使用信息发布后台系统！</p>
		<p>码云地址：<a href="https://gitee.com/duxiaod/irs-maven" target="_blank">https://gitee.com/duxiaod/irs-maven</a>&nbsp;&nbsp;&nbsp;记得点个star哦!</p>
		<p>
			作者QQ：<a href="tencent://message/?uin=593151321&Site=QQ交谈&Menu=yes"target="blank"><img border="0"src="http://wpa.qq.com/pa?p=1:593151321:7" alt="QQ593151321" width="71" height="24" /></a>
</br>
			QQ交流群：<span style="color: red">473676340</span> 需要的加一下共同学习！
		</p>
	</blockquote>
	<fieldset class="layui-elem-field layui-field-title">
		<legend>信息统计</legend>
	</fieldset>
	<div>
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="200">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th><strong>统计</strong></th>
					<th><strong>用户</strong></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>总数</td>
					<td class="userTotal"></td>
				</tr>
				<tr>
					<td>今日</td>
					<td class="usersToday"></td>
				</tr>
				<tr>
					<td>昨日</td>
					<td class="usersYestoday"></td>
				</tr>
				<tr>
					<td>本周</td>
					<td class="usersYearWeek"></td>
				</tr>
				<tr>
					<td>本月</td>
					<td class="usersMonth"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<fieldset class="layui-elem-field layui-field-title">
		<legend>网站用户性别占比</legend>
	</fieldset>
	<div id="info" style="width: 600px; height: 400px;"></div>

	<!-- 事件线 -->
	<div>
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 30px;">
			<legend>更新历史纪录</legend>
		</fieldset>
		<ul class="layui-timeline">
			<li class="layui-timeline-item"><i
				class="layui-icon layui-timeline-axis"></i>
				<div class="layui-timeline-content layui-text">
					<h3 class="layui-timeline-title">2018年12月11日</h3>
					<p>
						1.新增对多数据源(使用方法：在controller层使用注解@DataSource(dataSource="数据源名")即可)
						<i class="layui-icon"></i>
					</p>
				</div></li>
			<li class="layui-timeline-item"><i
				class="layui-icon layui-timeline-axis"></i>
				<div class="layui-timeline-content layui-text">
					<h3 class="layui-timeline-title">2018年07月29日</h3>
					<p>
						1.新增：菜单排序(tb_menus新增字段sorting)、时间线、菜单排序列单元格单击可快速修改排序值。 <br>
						2.修改：菜单管理页面默认折叠(自行替换treeGrid.js，TbMenus新增字段isOpen是否折叠)、菜单树默认折叠（新增，修改角色）、SQL文件
						<i class="layui-icon"></i>
					</p>
				</div></li>
		</ul>
	</div>

	<div class="sysNotice col">
		<blockquote class="layui-elem-quote title">系统基本参数</blockquote>
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<td>当前版本</td>
					<td class="version"></td>
				</tr>
				<tr>
					<td>开发作者</td>
					<td class="author"></td>
				</tr>
				<tr>
					<td>网站首页</td>
					<td class="homePage"></td>
				</tr>
				<tr>
					<td>服务器环境</td>
					<td class="server"></td>
				</tr>
				<tr>
					<td>数据库版本</td>
					<td class="dataBase"></td>
				</tr>
				<tr>
					<td>最大上传限制</td>
					<td class="maxUpload"></td>
				</tr>
			</tbody>
		</table>

	</div>
	</div>

	<script type="text/javascript" src="${ctx}/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx}/js/main.js"></script>

</body>
</html>