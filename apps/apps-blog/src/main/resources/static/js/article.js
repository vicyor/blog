$(function () {
    window.blogId = $("#blog-id").val();
});
//添加评论
$(function () {
    $("#comment").on('click', function () {
        var comment = $(".comment-box textarea").val();
        $(".comment-box textarea").val("");
        if (comment) {
            $.ajax({
                url: '/blog/comment/'+$("#blog-id").val()+'/save',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({
                    content: comment
                }), success(comment) {
                    generateCommentLi(comment);
                }
            });
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
    var $a1 = $("<a class='del' href='javascript:;' >删除</a>");
    $a1.attr("commentid", comment.id);
    if (uname == comment.username)
        $span4.append($a1);
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
        url: '/blog/comment/'+blogId+'/list',
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
            url: '/blog/comment/remove/' + commentId,
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
        var bool = confirm("确定删除?");
        var url=$("#path").val()+'/blogs/'+$("#author").val()+'/delete/'+blogId;
        console.log(url);
        if (bool) {
            $.ajax({
                url: url,
                method: 'delete'
            });
            window.location.href = $("#path").val();
        }
    })
});