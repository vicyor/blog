function parseBlog(blogs) {
    var $div=$("article div.blogs");
    $.each(blogs,function (index,blog) {
        var $li=$("<li></li>");
        var $span=$("<span class='blogpic'></span>");
        var $a1=$("<a></a>");
        $a1.prop("href","/blog/blogs/article?id="+blog.id);
        var $img=$("<img>");
        $img.prop("src",blog.uri);
        var $h3=$("<h3 class='blogtitle'></h3>");
        var $a2=$("<a></a>");
        $a2.prop("href","/blog/blogs/article?id="+blog.id);
        $a2.text(blog.title);
        var $div2=$("<div class='bloginfo'></div>");
        var $p=$("<p></p>");
        $p.text(blog.summary);
        var $div3=$("<div class='autor'></div>");
        var $sp1=$("<span class='lm'></span>");
        $sp1.text(blog.tag);
        var $sp2=$("<span class='dtime'></span>");
        $sp2.text(blog.udate);
        var $sp3=$("<span class='viewnum'></span>");
        $sp3.text("浏览("+blog.count+")");
        var $sp4=$("<span class='readmore'></span>");
        var $a3=$("<a></a>");
        $a3.text("阅读全文");
        $a3.prop("href","/blog/blogs/article?id="+blog.id);
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