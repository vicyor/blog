package com.vicyor.blog.apps.blog.repository;


import com.vicyor.blog.api.blog.domain.EsBlog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository repository;

    @Before
    public void before() {
        repository.deleteAll();
        repository.save(new EsBlog("静夜思", "李白写的", "床前明月光，疑是地上霜" +
                "举头望明月，低头思故乡"));
        repository.save(new EsBlog("行路难", "李白写的", "金樽清酒斗十千，玉盘珍羞直万钱。(羞 通：馐；直 通：值)" +
                "停杯投箸不能食，拔剑四顾心茫然。" +
                "欲渡黄河冰塞川，将登太行雪满山。(雪满山 一作：雪暗天)" +
                "闲来垂钓碧溪上，忽复乘舟梦日边。(碧 一作：坐)" +
                "行路难！行路难！多歧路，今安在？" +
                "长风破浪会有时，直挂云帆济沧海。"));
    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {
        Pageable pageable=new PageRequest(0,20);
        Page<EsBlog> poems = repository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining("", "李白", "", pageable);
        poems.getContent().forEach(poem->{
            System.out.println(poem);
        });
    }
}
