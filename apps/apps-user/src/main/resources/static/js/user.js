//点击修改资料
$(function () {
    $(".mod").on('click', function () {
        layui.use(['laypage', 'layer'], function () {
            var url = "/user/" + $("#username").val() + "/modify";
            layer.open({
                offset: ['30%', '40%'],
                type: 2,
                title: '修改资料',
                shadeClose: true,
                shade: false,
                content: url,
                area: ['502px', '404px'],
                end: function () {
                    //刷新一下页面
                    location.reload();
                }
            })
        });
    })
});
//点击修改头像
$(function () {
    layui.use('upload', function () {
        var upload = layui.upload;
        var url = $("#path").val() + "/" + $("#username").val() + "/head";
        upload.render({
            elem: '#modify',
            url: url,
            done: function (res) {
                alert("修改头像成功");
                location.reload();
            },
            auto: true
        });
    });

});