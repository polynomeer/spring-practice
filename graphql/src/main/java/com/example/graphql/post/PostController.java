package com.example.graphql.post;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class PostController {

    private final PostDao postDao;
    private final WriterDao writerDao;

    public PostController(PostDao postDao, WriterDao writerDao) {
        this.postDao = postDao;
        this.writerDao = writerDao;
    }

    @QueryMapping
    public List<Post> recentPosts(@Argument int count, @Argument int offset) {
        return postDao.getRecentPosts(count, offset);
    }

    @QueryMapping
    public Post getById(@Argument String id) {
        return postDao.getById(id);
    }

    @QueryMapping
    public Post getByTitle(@Argument String title) {
        return postDao.getByTitle(title);
    }

    @SchemaMapping
    public Writer writer(Post post) {
        return writerDao.getWriter(post.getWriterId());
    }

    @SchemaMapping(typeName = "Post", field = "first_writer")
    public Writer getFirstWriter(Post post) {
        return writerDao.getWriter(post.getWriterId());
    }

    @MutationMapping
    public Post createPost(@Argument String title, @Argument String text,
                           @Argument String category, @Argument String writerId) {
        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setWriterId(writerId);
        postDao.savePost(post);

        return post;
    }

}