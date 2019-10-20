package com.vicyor.blog.apps.blog.service.iml;

import com.vicyor.blog.apps.blog.domain.Tag;
import com.vicyor.blog.apps.blog.repository.TagRepository;
import com.vicyor.blog.apps.blog.service.TagService.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 作者:姚克威
 * 时间:2019/10/20 16:39
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository repository;

    @Override
    public List<Tag> findAllTags() {
        Iterable<Tag> all = repository.findAll();
        List<Tag> result = new ArrayList<>();
        all.forEach(result::add);
        return result;
    }
}
