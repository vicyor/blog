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
        var $this = $(this);
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
            var $replyLi = $(".reply-li");
            //对其它人的评论的回复
            var toRegex = /回复(\w+):/;
            //第一个捕获组()
            var to = placeholder.match(toRegex)[1];
            var from = $("#username").val();
            var parentCommentId = $replyLi.find(".right-box .opt-box>a.reply").attr('commentid');
            $replyLi.removeClass("reply-li");
            var $bigLi = $(".reply-li").parents(".bigLi");
            $.ajax({
                url: $("#path").val() + '/replyComment/' + from + '/create',
                method: 'post',
                data: JSON.stringify({
                    content: comment,
                    from: from,
                    to: to,
                    parentCommentId: parentCommentId
                }),
                contentType: "application/json"
            });
            $textarea.prop("placeholder", "想对作者说点什么");
            $textarea.val("");
            if ($bigLi.length == 0) {
                //点最外层的回复
                $bigLi = $replyLi;
            }
            var count = $bigLi.attr("replycommentcount");
            count = parseInt(count) + 1;
            $bigLi.attr("replycommentcount", count);
            $bigLi.find(".count").text(count);
            //触发一下所有的收起回复按钮
            $(".retract").trigger('click');
        }

    })
});

//渲染评论
function generateCommentLi(comment) {
    var commentId = comment.id;
    var $li = $('<li class="comment-line">');
    var $div = $('<div style="display: inline-block" class="left-box">');
    var $a = $('<a href="javascript:;"></a>');
    var $img = $('<img class="small-image"/>');
    $img.prop('src', '/blog/' + comment.image);
    var $div2 = $('<div class="info-box left-box">');
    var $a2 = $('<a href="javascript:;">');
    var $span1 = $('<span class="name" ></span>');
    $span1.text(comment.username);
    var $colonSpan = $('<span>:</span>');
    var $span2 = $('<span class="comment"></span>');
    var $div3 = $('<div class="right-box"></div>');
    var $span3 = $('<span class="date"></span>');
    $span3.text(comment.cdate);
    var uname = $("#username").val();
    var $span4 = $('<span class="opt-box"></span>');
    var $a1 = $("<a class='del hid' href='javascript:;'>删除</a>");
    $a1.attr("commentid", commentId);
    var $a3 = $("<a class='reply hid' href='javascript:;'>回复</a>");
    $a3.attr('commentid', commentId);
    var $a4 = $("<a class='see-reply hid' href='javascript:;'>查看回复</a>");
    $a4.attr('commentid', commentId);
    if (uname == comment.username)
        $span4.append($a1);
    $span4.append($a3);
    $span4.append($a4);
    $div3.append($span3);
    $div3.append($span4);
    $span2.text(comment.content);
    $li.append($div);
    $div.append($a);
    $a.append($img);
    $li.append($div2);
    $div2.append($a2);
    $a2.append($span1);
    $div2.append($colonSpan);
    $div2.append($span2);
    $div2.append("<br/>");
    $div2.append("<br/>");
    $li.append($div3);
    var $ul = $("ul.comment-list");
    $ul.append($li);
    //获取回复数量
    $.ajax({
        url: $("#path").val() + "/replyComment/count/" + commentId,
        method: 'get',
        success: function (count) {
            $li.attr('replyCommentCount', count);
            $li.addClass('bigLi');
            var $span = $("<span class='hid count'></span>");
            $span.text('( ' + count + ' )');
            $span4.append($span);
        }
    });
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
        var $this = $(this);
        var commentId = $this.attr("commentid");
        var url = '/blog/comment/' + $("#username").val() + '/remove/' + $("#blog-id").val() + "/" + commentId;
        if ($this.hasClass("reply-del")) {
            var parentCommentId = $this.parent(".opt-box").find("a.reply").attr("commentid");
            url = $("#path").val() + "/replyComment/" + $("#username").val() + "/remove/" + parentCommentId + '/' + commentId;
            //修改计数
            var $bigLi=$this.parents(".bigLi");
            var count=$bigLi.attr('replycommentcount');
            count=parseInt(count)-1;
            $bigLi.attr('replycommentcount',count);
            $bigLi.find(".count").text(count);
        }
        $this.closest("li.comment-line").remove();
        //触发一下所有的收起回复按钮
        $(".retract").trigger('click');
        $.ajax({
            url: url,
            method: 'delete'
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
        $(this).find(">.right-box .del").removeClass('hid');
        $(this).find(">.right-box .reply").removeClass('hid');
        $(this).find(">.right-box .see-reply").removeClass('hid');
        $(this).find(">.right-box .count").removeClass("hid");
        return false;
    });
    $(document).delegate('li.comment-line', 'mouseout', function (event) {
        $(this).find('>.right-box .del').addClass('hid');
        $(this).find('>.right-box .reply').addClass('hid');
        $(this).find(">.right-box .see-reply").addClass('hid');
        $(this).find(">.right-box .count").addClass("hid");
        return false;
    });
});
//添加回复功能
$(function () {
    $(document).delegate('.reply', 'click', function () {
        var $textArea = $(".comment-box textarea");
        var username = $(this).closest("li").find('>.info-box .name').text();
        $(this).closest("li").addClass('reply-li');
        $textArea.prop('placeholder', '回复' + username + ":");
        $textArea.focus();
    });
});
//查看回复功能
$(function () {
    $(document).delegate(".see-reply", "click", function () {
        var commentId = $(this).attr("commentid");
        var $pLi = $(this).parents("li");
        $pLi.find(".reply-comment-div").remove();
        var $this = $(this);
        $.ajax({
            url: $("#path").val() + "/replyComment/" + commentId,
            method: 'get',
            success: function (data) {
                var $bDiv = $("<div class='reply-comment-div'></div>");
                var $ul = $("<ul class='reply-comment-ul'></ul>");
                $.each(data, function (index, value) {
                    var $li = $("<li class='reply-comment-li comment-line'></li>");
                    var $div = $("<div class='left-box'></div>");
                    var $a = $("<a href='javascript:;'></a>");
                    var $img = $("<img class='small-image'/>");
                    $img.prop('src', $("#path").val() + "/" + value.image);
                    $a.append($img);
                    $div.append($a);
                    $li.append($div);
                    $ul.append($li);
                    var $div1 = $("<div class='info-box left-box'></div>");
                    var $a1 = $("<a href='javascript:;'></a>");
                    var $span1 = $("<span class='name'></span>");
                    $span1.text(value.from);
                    var $colonSpan = $("<span style='color: lightblue'></span>");
                    $colonSpan.text(":回复" + value.to + ":");
                    var $span2 = $("<span class='comment'></span>");
                    $span2.text(value.content);
                    $a1.append($span1);
                    $div1.append($a1);
                    $div1.append($colonSpan);
                    $div1.append($span2);
                    $div1.append($("<br/>"));
                    $div1.append($("<br/>"));
                    $li.append($div1);
                    var $div2 = $("<div class='right-box'></div>");
                    var $dateSpan = $("<span class='date'></span>");
                    $dateSpan.text(value.cdate);
                    var $optSpan = $("<span class='opt-box'></span>");
                    var $aReply = $("<a class='reply hid' href='javascript:;'></a>");
                    $aReply.attr('commentid', value.parentCommentId);
                    $aReply.text("回复");
                    var $aDel = $("<a class='del hid reply-del' href='javascript:;'></a>");
                    $aDel.attr('commentid', value.id);
                    $aDel.text("删除");
                    $optSpan.append($aReply);
                    if (value.from == $("#username").val()) {
                        $optSpan.append($aDel);
                    }
                    $div2.append($dateSpan);
                    $div2.append($optSpan);
                    $li.append($div2);
                });
                $bDiv.append($ul);
                $bDiv.append($("<br/>"));
                $bDiv.append($("<br/>"));
                $pLi.find(".right-box").before($bDiv);
                //将查看回复变为收起回复
                $this.text("收起回复");
                $this.removeClass("see-reply");
                $this.addClass("retract");
            }
        })
    });
});
//收起回复功能
$(function () {
    $(document).delegate(".retract", "click", function () {
        var $this = $(this);
        $this.parents("li.comment-line").find("div.reply-comment-div").remove();
        $this.text("查看回复");
        $this.removeClass("retract");
        $this.addClass("see-reply");
    })
});