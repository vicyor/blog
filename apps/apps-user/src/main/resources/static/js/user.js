//点击修改资料
$(function () {
    $(".mod").on('click', function () {
        layui.use(['laypage', 'layer'], function () {
            var url="/user/"+$("#username").val()+"/modify";
            layer.open({
                offset:['30%','40%'],
                type:2,
                title:'修改资料',
                shadeClose:true,
                shade:false,
                content:url,
                area:['502px','404px'],
                end:function () {
                    //刷新一下页面
                    location.reload();
                }
            })
        });
    })
});