function parseBlog(blogs, page, pagesize) {
    var $div = $("article div.blogs>div#blog");
    $div.find("li").remove();
    $.each(blogs, function (index, blog) {
        var $li = $("<li></li>");
        var $span = $("<span class='blogpic'></span>");
        var $a1 = $("<a></a>");
        $a1.prop("href", "/blog/blogs/article/" + blog.id);
        var $img = $("<img>");
        $img.prop("src", blog.uri);
        var $h3 = $("<h3 class='blogtitle'></h3>");
        var $a2 = $("<a></a>");
        $a2.prop("href", "/blog/blogs/article/" + blog.id);
        $a2.text(blog.title);
        var $div2 = $("<div class='bloginfo'></div>");
        var $p = $("<p></p>");
        $p.text(blog.summary);
        var $div3 = $("<div class='autor'></div>");
        var $sp1 = $("<span class='lm'></span>");
        $sp1.text(blog.tag);
        var $sp2 = $("<span class='dtime'></span>");
        $sp2.text(blog.udate);
        var $sp3 = $("<span class='viewnum'></span>");
        $sp3.text("浏览(" + blog.count + ")");
        var $sp4 = $("<span class='readmore'></span>");
        var $a3 = $("<a></a>");
        $a3.text("阅读全文");
        $a3.prop("href", "/blog/blogs/article/" + blog.id);
        $li.append($span);
        $span.append($a1);
        $a1.append($img);
        $li.append($h3);
        $h3.append($a2);
        $li.append($div2);
        $div2.append($p);
        $li.append($div3);
        $div3.append($sp1);
        $div3.append($sp2);
        $div3.append($sp3);
        $div3.append($sp4);
        $sp4.append($a3);
        $div.append($li);
    })
}

function parseCountRank(blogs) {
    var $ul = $("#clickrank ul");
    $ul.empty();
    $.each(blogs, function (index, blog) {
        var $li = $("<li></li>");
        var $b = $("<b></b>");
        var $a = $("<a></a>");
        $a.prop("href", "/blog/blogs/article/" + blog.id);
        $a.text(blog.title);
        var $p = $("<p></p>");
        var $ai=$("<a></a>");
        $ai.prop("href", "/blog/blogs/article/" + blog.id);
        var $i = $("<i></i>");
        var $img = $("<img></img>");
        $img.prop("src", "/blog/" + blog.uri);
        $p.text(blog.summary);
        $li.append($b);
        $b.append($a);
        $li.append($p);
        $p.append($ai);
        $ai.append($i)
        $i.append($img);
        $ul.append($li);
    })
}

function getBlog() {
    var $input = $("input[name='Submit']");
    var keyword = $("input[name='keyboard']").val();
    keyword = keyword == "请输入关键字" ? "" : keyword;

    $.ajax({
        url: "/blog/blogs?keyword=" + keyword + "&page=" + (pageConf.currentPage - 1) + "&pagesize=" + pageConf.pageSize,
        method: "get",
        success: function (data) {
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage;
                var layer = layui.layer;
                laypage.render({
                    elem: 'pg',
                    pages: 10,
                    first: false,
                    last: false,
                    count: data[0],
                    limit: pageConf.pageSize,
                    curr: pageConf.currentPage,
                    jump: function (obj, first) {
                        if (!first) {
                            pageConf.currentPage = obj.curr;
                            pageConf.pageSize = obj.limit;
                            //再次发送ajax请求
                            getBlog();
                        } else {
                            parseBlog(data[1], pageConf.currentPage, pageConf.pageSize);
                        }
                    }
                });
            })
        }
    })
}

function getBlogsByTag(val) {
    $.ajax({
        'url': "/blog/blogs/tag?tag=" + val + "&page=" + (pageConf.currentPage - 1) + "&pagesize=" + pageConf.pageSize,
        'method': 'get',
        'success': function (data) {
            console.log(data);
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage;
                var layer = layui.layer;
                laypage.render({
                    elem: 'pg',
                    pages: 10,
                    first: false,
                    last: false,
                    count: data[0],
                    limit: pageConf.pageSize,
                    curr: pageConf.currentPage,
                    jump: function (obj, first) {
                        if (!first) {
                            pageConf.currentPage = obj.curr;
                            pageConf.pageSize = obj.limit;
                            getBlogsByTag(val);
                        } else {
                            parseBlog(data[1], pageConf.currentPage, pageConf.pageSize);
                        }
                    }
                });
            })
        }
    })
}

function initclick() {
    var href = window.location.href.toString();
    var arr = $("#cloud").find("a");
    $.each(arr, function (index, a) {
        var $a = $(a);
        $a.prop("href", "javascript:;");
        $a.on('click', function () {
            if (href.indexOf("/blog/index") > 0) {
                var val = $a.text();
                pageConf.pageSize = 10;
                pageConf.currentPage = 1;
                getBlogsByTag(val);
            }else{
                window.location.href="/blog/"
            }
        })
    })

}