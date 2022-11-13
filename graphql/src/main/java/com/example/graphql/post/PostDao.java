package com.example.graphql.post;

import java.util.List;
import java.util.stream.Collectors;

public class PostDao {

    private final List<Post> posts;

    public PostDao(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        return posts.stream()
                .skip(offset)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Post> getWriterPosts(String writer) {
        return posts.stream()
                .filter(post -> writer.equals(post.getWriterId()))
                .collect(Collectors.toList());
    }

    public void savePost(Post post) {
        posts.add(post);
    }
}