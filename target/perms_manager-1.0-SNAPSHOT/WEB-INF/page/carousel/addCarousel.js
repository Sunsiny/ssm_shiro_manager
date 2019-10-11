var $;
var $form;
var form;
layui.config({
	base : "js/"
}).use(['form','layer','jquery','upload'],function(){
	var layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,upload = layui.upload;
		$ = layui.jquery;
		form = layui.form;
		
		//imgurl
		form.verify({
			imgurl:function(value){
				if(value.length==0){
					return "请上传图片！";
				}
			}
		})
		
		//普通图片上传
		  var uploadInst = upload.render({
		    elem: '#onePicUpload'
		    ,url: ctx+'/file/upload'
		    ,before: function(obj){
		      //预读本地文件
		      obj.preview(function(index, file, result){
		        $('#preview').attr('src', result); //图片链接（base64）
		      });
		    }
		    ,done: function(res){
		      //如果上传失败
		      if(res.code > 0){
		        return layer.msg(res.msg);
		      }
		      //上传成功
		      //设置src给图片隐藏域
		      $("#newsImg").val(res.data.src);
		      layer.msg('上传成功')
		    }
		    ,error: function(){
		      //失败状态，并实现重传
		      var uploadError = $('#uploadError');
		      uploadError.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
		      uploadError.find('.demo-reload').on('click', function(){
		        uploadInst.upload();
		      });
		    }
		  });
		
 	form.on("submit(addCarousel)",function(data){
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
 		var msg;
 		$.ajax({
    		type: "post",
            url: ctx+"/carousel/save",
            data:data.field,
			dataType:"json",
			success:function(d){
				if(d.code==0){
		        	msg="添加成功！";
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