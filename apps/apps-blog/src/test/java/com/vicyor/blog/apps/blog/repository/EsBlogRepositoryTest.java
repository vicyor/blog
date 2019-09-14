package com.vicyor.blog.apps.blog.repository;


import com.vicyor.blog.apps.blog.domain.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository repository;

    @Before
    public void before() {

        for (int i = 0; i < 0; i++) {
            repository.save(new EsBlog("静夜思", "李白", "床前明月光，疑是地上霜" +
                    "举头望明月，低头思故乡", new Date(), new Date(), 2, "images/text02.jpg", "千古名诗", "李白"));

            repository.save(new EsBlog("行路难", "李白", "金樽清酒斗十千，玉盘珍羞直万钱。(羞 通：馐；直 通：值)" +
                    "停杯投箸不能食，拔剑四顾心茫然。" +
                    "欲渡黄河冰塞川，将登太行雪满山。(雪满山 一作：雪暗天)" +
                    "闲来垂钓碧溪上，忽复乘舟梦日边。(碧 一作：坐)" +
                    "行路难！行路难！多歧路，今安在？" +
                    "长风破浪会有时，直挂云帆济沧海。", new Date(), new Date(), 4, "images/text02.jpg", "千古名诗", "李白"));
        }
    }

    public void before1() {
        repository.deleteAll();

    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {
        String keyword = "静夜思";

        Page<EsBlog> page = repository
                .findDistinctEsBlogByContentMatchesOrTitleMatchesOrTagMatchesOrderByUdateDesc(keyword, keyword, keyword, PageRequest.of(0, 10));
        System.out.println(page.getTotalElements());
    }

    @Test
    public void testFindDistinctEsBlog() {

        List<String> fields = new ArrayList<>();
        fields.add("udate");
        Iterable<EsBlog> esBlogs = repository.findAll(new Sort(Sort.Direction.DESC, fields));
        esBlogs.forEach(esBlog -> System.out.println(esBlog));
    }
}
