var $;
var form;
layui.config({
	base : "js/"
}).use(['form','layer','jquery','laydate'],function(){
	var layer = parent.layer === undefined ? layui.layer : parent.layer,
		laydate = layui.laydate;
		$ = layui.jquery;
		form = layui.form;
		
		laydate.render({
			elem: '#birthday' //指定元素
			,max: 'new Date()'
		});
		
		$("#nickname").blur(function(){
			$.ajax({
	            type: "get",
	            url: ctx+"/user/checkUserByNickname/"+$("#uid").val()+"&nickname="+$("#nickname").val(),
	            success:function(data){
	            	if(data.code!=0){
	            		top.layer.msg(data.msg);
	            		$("#nickname").val("");
	            		$("#nickname").focus();
	            	}
	            }
	        });
		});
		
		$("#eMail").blur(function(){
			$.ajax({
				type: "post",
				url: ctx+"/user/checkUserByEmail/"+$("#uid").val()+"&eMail="+$("#eMail").val(),
				success:function(data){
					if(data.code!=0){
						top.layer.msg(data.msg);
						$("#eMail").val("");
						$("#eMail").focus();
					}
				}
			});
		});

		
 	form.on("submit(updUser)",function(data){
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
 		var msg;
 		$.ajax({
    		type: "post",
            url: ctx+"/user/updUser",
            data:data.field,
			dataType:"json",
			success:function(d){
				if(d.code==0){
		        	msg="修改成功！";
				}else{
		        	msg=d.msg;
				}
			}
        });
 		setTimeout(function(){
 			top.layer.close(index);
 			top.layer.msg(msg);
 			layer.closeAll("iframe");
 			//刷新父页面
	 		parent.location.reload();
        },2000);
 		return false;
 	})
	
})