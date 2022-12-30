package com.example.graphql.post;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GraphqlConfiguration {

    @Bean
    public PostDao postDao() {
        List<Post> posts = new ArrayList<>();
        for (int postId = 0; postId < 10; ++postId) {
            for (int writerId = 0; writerId < 10; ++writerId) {
                Post post = new Post();
                post.setId("Post" + writerId + postId);
                post.setTitle("Post " + writerId + ":" + postId);
                post.setCategory("Post category");
                post.setText("Post " + postId + " + by writer " + writerId);
                post.setWriterId("Writer" + writerId);
                posts.add(post);
            }
        }
        return new PostDao(posts);
    }

    @Bean
    public WriterDao writerDao() {
        List<Writer> writers = new ArrayList<>();
        for (int writerId = 0; writerId < 10; ++writerId) {
            Writer writer = new Writer();
            writer.setId("Writer" + writerId);
            writer.setName("Writer " + writerId);
            writer.setThumbnail("http://example.com/writers/" + writerId);
            writers.add(writer);
        }
        return new WriterDao(writers);
    }
}