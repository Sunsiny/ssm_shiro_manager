var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;
		
		form.on("submit(editRole)",function(data){
			var treeObj = $.fn.zTree.getZTreeObj("xtree1");
	 		var list = treeObj.getCheckedNodes(true);
	 		//菜单id
	 		var mStr="";
	 		for(var i=0;i<list.length;i++){
	 			mStr+=list[i].menuId+",";
	 		}
	 		//去除字符串末尾的‘,’
	 		mStr=mStr.substring(0,mStr.length-1)
	 		var m=$("#m");
	 		//将权限字符串写进隐藏域
	 		m.val(mStr)
	 		//弹出loading
	 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	        setTimeout(function(){
	        	$.ajax({
	                type: "POST",
	                async:false,
	                url: ctx+"/sys/updRole",
	                data:$("#arf").serialize(),
	            });
	            top.layer.close(index);
				top.layer.msg("角色修改成功！");
	 			layer.closeAll("iframe");
		 		//刷新父页面
		 		parent.location.reload();
	        },2000);
	 		return false;
	 	})
 	
 	//角色名唯一性校验
 	$("#roleName").blur(function(){
 		$.ajax({
            type: "get",
            url: ctx+"/sys/checkRoleName/"+$("#roleName").val(),
            success:function(data){
            	if(data.code!=0){
            		top.layer.msg(data.msg);
            		$("#roleName").val("");
            		$("#roleName").focus();
            	}
            }
        });
 	})
})
var roleId=$("#roleId").val();
var menu = {
	setting: {
		view : {
            showIcon : false,
        },	
		   
	    data:{
	    	simpleData: {
	            enable: true,
	            idKey: "menuId",
	            pIdKey: "parentId",
	        },
	    	key:{
	    		name:"title",
	    	}
	    },
		check:{
			enable:true
		}
	},
	loadMenuTree:function(){
		$.ajax({
			type: "post",
			url: ctx+'/sys/xtreedata',
			data:{roleId:roleId},
			dataType:"json",
			success:function(data){
				//$("#xtree1").zTree(menu.setting, data);
				$.fn.zTree.init($("#xtree1"), menu.setting, data);
				}
			})
	}
};

$().ready(function(data){
	menu.loadMenuTree();
});
function checkNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("xtree1"),
        type = e.data.type,
        nodes = zTree.getSelectedNodes();
	console.log(type.indexOf("All"));
	if (type.indexOf("All") < 0 && nodes.length == 0) {
	    alert("请先选择一个节点");
	}
	if (type == "checkAllTrue") {
	    zTree.checkAllNodes(true);
	} else if (type == "checkAllFalse") {
	        zTree.checkAllNodes(false);
    }
}
$("#checkAllTrue").bind("click", { type: "checkAllTrue" }, checkNode);
$("#checkAllFalse").bind("click", { type: "checkAllFalse" }, checkNode);