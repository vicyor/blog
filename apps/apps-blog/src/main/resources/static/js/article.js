$(function () {
    window.blogId = $("#blog-id").val();
});
//添加评论
$(function () {
    $("#comment").on('click', function () {
        var $textarea = $(".comment-box textarea");
        var comment = $textarea.val();
        $textarea.val("");
        var placeholder = $textarea.prop('placeholder');
        if (comment && placeholder == '想对作者说点什么') {
            $.ajax({
                url: $("#path").val() + '/comment/' + $("#username").val() + '/' + $("#blog-id").val() + '/save',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({
                    content: comment
                }), success(comment) {
                    generateCommentLi(comment);
                }
            });
        } else if (comment) {
            //对其它人的评论的回复
            var toRegex = /回复(\w+):/;
            //第一个捕获组()
            var to = placeholder.match(toRegex)[1];
            var from =$("#username").val();
            /**
             * TODO
             * 1.es的replayComment index和type没创建,mapping页没创建
             * 2.前端的动态效果要添加查看回复的功能
              */
        }
    })
});

//渲染评论
function generateCommentLi(comment) {
    var $li = $('<li class="comment-line">');
    var $div = $('<div style="display: inline-block" class="left-box">');
    var $a = $('<a href="javascript:;"></a>');
    var $img = $('<img class="small-image"/>');
    $img.prop('src', '/blog/' + comment.image);
    var $div2 = $('<div class="info-box left-box">');
    var $a2 = $('<a href="javascript:;">');
    var $span1 = $('<span class="name" ></span>');
    $span1.text(comment.username + ":");
    var $span2 = $('<span class="comment"></span>');
    var $div3 = $('<div class="right-box"></div>');
    var $span3 = $('<span class="date"></span>');
    $span3.text(comment.cdate);
    var uname = $("#username").val();
    var $span4 = $('<span class="opt-box"></span>');
    var $a1 = $("<a class='del hid' href='javascript:;'>删除</a>");
    $a1.attr("commentid", comment.id);
    var $a3 = $("<a class='reply hid' href='javascript:;'>回复</a>");
    $a3.attr('commentid', comment.id);
    if (uname == comment.username)
        $span4.append($a1);
    $span4.append($a3);
    $div3.append($span3);
    $div3.append($span4);
    $span2.text(comment.content);
    $li.append($div);
    $div.append($a);
    $a.append($img);
    $li.append($div2);
    $div2.append($a2);
    $a2.append($span1);
    $div2.append($span2);
    $div2.append("<br/>");
    $div2.append("<br/>");
    $li.append($div3);
    var $ul = $("ul.comment-list");
    $ul.prepend($li);
}

//初始加载评论
$(function () {
    $.ajax({
        url: '/blog/comment/' + blogId + '/list',
        method: 'get',
        success: function (comments) {
            $.each(comments, function (index, comment) {
                generateCommentLi(comment);
            })
        }
    });
});
//删除评论
$(function () {
    $(document).delegate(".del", "click", function () {
        var commentId = $(this).attr("commentid");
        var $this = $(this);
        $.ajax({
            url: '/blog/comment/' + $("#username").val() + '/remove/' + $("#blog-id").val() + "/" + commentId,
            method: 'delete',
            success: function () {
                $this.parents(".comment-line").remove();
            }
        });

    });
});
//删除blog
$(function () {
    $("#delBlog").on('click', function () {
        var url = $("#path").val() + '/blogs/' + $("#author").val() + '/delete/' + blogId;
        var bool = confirm("确定删除?");

        if (bool) {
            $.ajax({
                url: url,
                success: function () {
                    window.location.href = $("#path").val();
                }
            });

        }
    })
});
//评论移入显示删除,回复按钮
$(function () {
    $(document).delegate('li.comment-line', 'mousemove', function () {
        $(this).find(".del").removeClass('hid');
        $(this).find(".reply").removeClass('hid');
    });
    $(document).delegate('li.comment-line', 'mouseout', function () {
        $(this).find('.del').addClass('hid');
        $(this).find('.reply').addClass('hid');
    });
});
//添加回复功能
$(function () {
    $(document).delegate('.reply', 'click', function () {
        var $textArea = $(".comment-box textarea");
        var username = $(this).parents("li").find('.name').text();
        username = username.substring(0, username.indexOf(':'));
        $textArea.prop('placeholder', '回复' + username + ":");
        $textArea.focus();
    });
});