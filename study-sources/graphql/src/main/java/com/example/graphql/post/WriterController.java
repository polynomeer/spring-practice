package com.example.graphql.post;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WriterController {

    private final PostDao postDao;

    public WriterController(PostDao postDao) {
        this.postDao = postDao;
    }

    @SchemaMapping
    public List<Post> posts(Writer writer) {
        return postDao.getWriterPosts(writer.getId());
    }
}