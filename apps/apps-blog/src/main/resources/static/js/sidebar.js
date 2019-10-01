// layui分页参数配置
$(function () {
    if (!window.pageConf) {
        window.pageConf = {};
        pageConf.pageSize = 10;
        pageConf.currentPage = 1;
    }
});
//加载触发提交点击事件
$(function () {
    //关键词全文搜索
    $("input[name='Submit']").on('click', function () {
        pageConf.pageSize = 10;
        pageConf.currentPage = 1;
        getBlog();
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
            data=data.params;
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
        $a.prop("href", "/blog/blogs/"+blog.author+"/article/" + blog.id);
        $a.text(blog.title);
        var $p = $("<p></p>");
        var $ai=$("<a></a>");
        $ai.prop("href", "/blog/blogs/"+blog.author+"/article/" + blog.id);
        var $i = $("<i></i>");
        var $img = $("<img></img>");
        $img.prop("src", "/blog/" + blog.uri);
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
            data=data.params;

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
            } else {
                window.location.href = "/blog/"
            }
        })
    })
}
//执行初始化操作
$(function () {
    initclick();
});
