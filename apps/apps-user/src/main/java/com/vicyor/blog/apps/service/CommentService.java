package com.vicyor.blog.apps.service;

import java.util.List;

public interface CommentService {
    List<Object> ownComments(String username);
}
