// layui分页参数配置
$(function () {
    if (!window.pageConf) {
        window.pageConf = {};
        pageConf.pageSize = 10;
        pageConf.currentPage = 1;
    }
});
$(function () {
    //关键词全文搜索
    $("input[name='Submit']").on('click', function () {
        pageConf.pageSize = 10;
        pageConf.currentPage = 1;
        getBlog();
        return false;
    })
});

//ajax根据keyword获取es的blog并渲染
function getBlog() {
    var $input = $("input[name='Submit']");
    var keyword = $("input[name='keyboard']").val();
    keyword = keyword == "请输入关键字" ? "" : keyword;
    $.ajax({
        url: "/blog/blogs?keyword=" + keyword + "&page=" + (pageConf.currentPage - 1) + "&pagesize=" + pageConf.pageSize,
        method: "get",
        success: function (data) {
            data = data.params;
            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage;
                var layer = layui.layer;
                laypage.render({
                    elem: 'pg',
                    pages: 10,
                    first: false,
                    last: false,
                    count: data.length,
                    limit: pageConf.pageSize,
                    curr: pageConf.currentPage,
                    jump: function (obj, first) {
                        if (!first) {
                            pageConf.currentPage = obj.curr;
                            pageConf.pageSize = obj.limit;
                            //再次发送ajax请求
                            getBlog();
                        } else {
                            parseBlog(data.blogs, pageConf.currentPage, pageConf.pageSize);
                        }
                    }
                });
            })
        }
    })
}

//渲染排行榜的blog到指定盒子
function parseCountRank(blogs) {
    var $ul = $("#clickrank ul");
    $ul.empty();
    $.each(blogs, function (index, blog) {
        var $li = $("<li></li>");
        var $b = $("<b></b>");
        var $a = $("<a></a>");
        $a.prop("href", "/blog/blogs/" + blog.author.author + "/article/" + blog.id);
        $a.text(blog.title);
        var $p = $("<p></p>");
        var $ai = $("<a></a>");
        $ai.prop("href", "/blog/blogs/" + blog.author.author + "/article/" + blog.id);
        var $i = $("<i></i>");
        var $img = $("<img></img>");
        $img.prop("src", "/blog/" + blog.author.uri);
        $p.text(blog.summary);
        $li.append($b);
        $b.append($a);
        $li.append($p);
        $p.append($ai);
        $ai.append($i);
        $i.append($img);
        $ul.append($li);
    })
}

/**
 * 获取点击排行
 */
$(function () {
    $.ajax({
        url: "/blog/blogs/rank/count",
        method: 'get',
        success: function (blogs) {
            parseCountRank(blogs);
        }
    })
});

//根据标签获取blog
function getBlogsByTag(val) {
    $.ajax({
        'url': "/blog/blogs/tag?tag=" + val + "&page=" + (pageConf.currentPage - 1) + "&pagesize=" + pageConf.pageSize,
        'method': 'get',
        'success': function (data) {
            data = data.params;

            layui.use(['laypage', 'layer'], function () {
                var laypage = layui.laypage;
                var layer = layui.layer;
                laypage.render({
                    elem: 'pg',
                    pages: 10,
                    first: false,
                    last: false,
                    count: data.length,
                    limit: pageConf.pageSize,
                    curr: pageConf.currentPage,
                    jump: function (obj, first) {
                        if (!first) {
                            pageConf.currentPage = obj.curr;
                            pageConf.pageSize = obj.limit;
                            getBlogsByTag(val);
                        } else {
                            parseBlog(data.blogs, pageConf.currentPage, pageConf.pageSize);
                        }
                    }
                });
            })
        }
    })
}

//标签云的点击事件
function initclick() {
    var href = window.location.href.toString();
    $(document).delegate('.tg', 'click', function () {
        var $a = $(this);
        $a.prop("href", "javascript:;");
        var val = $a.text();
        pageConf.pageSize = 10;
        pageConf.currentPage = 1;
        getBlogsByTag(val);
    });
}

//执行初始化操作
$(function () {
    initclick();
});
//从 es中查找所有的标签
$(function () {
    var $ul = $("#cloud");
    $.ajax({
        url: $("#path").val() + "/tag/listAllTags",
        method: "get",
        success: function (tags) {
            $.each(tags, function (index, tag) {
                var $a = $("<a class='tg'></a>");
                $a.prop('href', "javascript:;");
                $a.text(tag.tag);
                $ul.append($a);
            })
        }
    })
});
