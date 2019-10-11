<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>layout Layui</title>
<link rel="stylesheet" href="${ctx }/layui/css/layui.css">
<style type="text/css">
/* 数据表格复选框正常显示 */
.layui-table-cell .layui-form-checkbox[lay-skin="primary"] {
	top: 50%;
	transform: translateY(-50%);
}
</style>
<script src="${ctx }/layui/layui.js"></script>
<script>
	var ctx = "${ctx}";
</script>

</head>
<body class="layui-layout-body" style="overflow: auto">
	<br />
	<div class="layui-btn-group TableTools" style="margin-left: 10px">
		<shiro:hasPermission name="sys:menu:save">
			<button class="layui-btn" id="addMenu">添加菜单</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:menu:update">
			<button class="layui-btn" id="editMenu">编辑菜单</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:menu:delete">
			<button class="layui-btn layui-btn-danger" id="delMenu">删除菜单</button>
		</shiro:hasPermission>
		<button class="layui-btn layui-btn-primary">（不选中为添加顶级菜单，选中添加子菜单）</button>
	</div>
	<div>
	
		<table class="layui-hidden" id="treeTable" lay-filter="treeTable"></table>
	</div>
	<script>
		layui.use(
				[ 'element', 'layer', 'form', 'upload', 'treeGrid', 'jquery' ],
				function() {
					var treeGrid = layui.treeGrid, form = layui.form, //很重要
					$ = layui.jquery, table = layui.table;
					var treeTable = treeGrid.render({
						id : 'treeTable',
						elem : '#treeTable',
						url : ctx + '/sys/menuData',
						cellMinWidth : 100,
						treeId : 'menuId'//树形id字段名称
						,
						treeUpId : 'parentId'//树形父id字段名称
						,
						treeShowName : 'title'//以树形式显示的字段
						,
						cols : [ [ {
							field : 'menuId',
							title : ' ',
							//templet : "#radioTpl",
							type: 'checkbox',
							unresize : true
						}, {
							field : 'title',
							title : '菜单名'
						}, {
							field : 'icon',
							title : '图标',
							templet : '#iconTpl'
						}, {
							field : 'href',
							title : '链接'
						}, {
							field : 'perms',
							title : '权限标识'
						}, {
							field : 'sorting',
							title : '排序',
							event: 'sorting', 
							style:'cursor: pointer;'
						}         
						] ],
						page : false
					});

					//监听单元格编辑
					treeGrid.on('tool(treeTable)', function(obj) {
						 var data = obj.data;
						 if(obj.event === 'sorting'){
							 var msg='',flag=false;
						      layer.prompt({
							        formType: 2
							        ,title: '修改 ID 为 ['+ data.menuId +'] 的排序'
							        ,value: data.sorting
							      }, function(value, index){
							        layer.close(index);
							        //这里一般是发送修改的Ajax请求
							        if(data.sorting!=value){
							        	$.ajax({
							                type: "POST",
							                url: "updMenuSortingById",
							                async:false,
							                data:{'menuId':data.menuId,'sorting':value},
							                success:function(d){
							                	if(d.code==0){
							    					msg="修改成功";
							    					flag=true;
							    				}else{
							    					if(d.code==null||d.code==''){
							    						msg="权限不足，联系超管！";
							    					}else{
							    						msg=d.msg;
							    					}
							    					value=data.sorting
							    				}
							                }
							            });
							        	if(flag){
							        		layer.msg(msg,{icon: 1});
							        	}else{
							        		layer.msg(msg,{icon: 5});
							        	}
							        }
							        //同步更新表格和缓存对应的值
							        treeGrid.reload("treeTable",{})
							      });
						 }
					});

					$("#addMenu").click(function() {
						var checkStatus = treeGrid.checkStatus('treeTable')
					      ,data = checkStatus.data,a=0;
						if(data.length>1){
							layer.msg("只能选择一个！", {
								icon : 5
							});
							return ;
						}
						if(data!=''){
							a=data[0].menuId;
						}
						if (a == undefined || a != 1) {
							if (a == undefined) {
								a = 0;
							}
							//添加顶级菜单
							layer.open({
								type : 2,
								title : "添加菜单",
								area : [ '470px', '420px' ],
								content : ctx + "/sys/toSaveMenu/" + a, //这里content是一个普通的String
								end: function () {
					                location.reload();
					            }
							})
						} else {
							layer.msg("此菜单不允许操作！", {
								icon : 5
							});
							return;
						}

					})

					$("#editMenu").click(function() {
						var checkStatus = treeGrid.checkStatus('treeTable')
					      ,data = checkStatus.data,a='';
						if(data.length>1){
							layer.msg("只能选择一个！", {
								icon : 5
							});
							return ;
						}
						if(data!=''){
							a=data[0].menuId;
						}
						
						if (a == '') {
							layer.msg("请选择要操作的菜单！", {
								icon : 5
							});
							return;
						}
						if (a == 1) {
							layer.msg("不允许操作的菜单！", {
								icon : 5
							});
							return;
						}
						//添加顶级菜单
						layer.open({
							type : 2,
							title : "编辑菜单",
							area : [ '470px', '420px' ],
							content : ctx + "/sys/toEditMenu/" + a //这里content是一个普通的String
						})

					})

					$("#delMenu").click(function() {
						var checkStatus = treeGrid.checkStatus('treeTable')
					      ,data = checkStatus.data,a='';
						if(data.length>1){
							layer.msg("只能选择一个！", {
								icon : 5
							});
							return ;
						}
						if(data!=''){
							a=data[0].menuId;
						}
						if (a == '') {
							layer.msg("请选择要操作的菜单！", {
								icon : 5
							});
							return;
						}
						if (a == 1) {
							layer.msg("不允许删除！", {
								icon : 5
							});
							return;
						}
						layer.confirm('真的删除行么', function(index) {
							$.ajax({
								url : ctx + '/sys/delMenuById/' + a,
								type : "post",
								success : function(d) {
									if (d.code == 0) {
										layer.msg("删除成功！", {
											icon : 1
										});
										setTimeout(function() {
											treeGrid.reload("treeTable",{})
										}, 500);
									} else {
										layer.msg(d.msg, {
											icon : 5
										});
									}
								}
							})
							layer.close(index);
						});

					})
				});
	</script>

	<script type="text/html" id="barTools">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</script>
	<script type="text/html" id="iconTpl">
 		 {{#  if(d.icon === null){ }}
   			
		{{#  } else{ }}
			<i class="layui-icon">{{ d.icon }}</i>  
  		{{#  } }}
	</script>
	<script type="text/html" id="radioTpl">
  		<span style="top:50%"><input type="radio" name="menuId" value="{{d.menuId}}" title=" " lay-filter="radiodemo"></span>
	</script>
</body>
</html>