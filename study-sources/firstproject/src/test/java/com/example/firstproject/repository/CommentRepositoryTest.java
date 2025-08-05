package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 준비
            Long articleId = 4L;

            // 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            Article article = new Article(4L, "당신의 인생 영화는?", "content4");
            Comment comment1 = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment comment2 = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment comment3 = new Comment(3L, article, "Choi", "쇼생크의 탈출");
            List<Comment> expected = Arrays.asList(comment1, comment2, comment3);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 준비
            Long articleId = 1L;

            // 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 준비
            Long articleId = 9L;

            // 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음");
        }

        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            // 준비
            Long articleId = 9999L;

            // 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9999번 글은 댓글이 없음");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            // 준비
            Long articleId = -1L;

            // 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 글은 댓글이 없음");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "Park";

            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            Comment comment1 = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "content4"), nickname, "굳 윌 헌팅");
            Comment comment2 = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "content5"), nickname, "치킨");
            Comment comment3 = new Comment(7L, new Article(6L, "당신의 취미는?", "content6"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(comment1, comment2, comment3);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");
        }

        /* Case 2: "Kim"의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "Kim";

            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            Comment comment1 = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "content4"), nickname, "아이 엠 샘");
            Comment comment2 = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "content5"), nickname, "샤브샤브");
            Comment comment3 = new Comment(8L, new Article(6L, "당신의 취미는?", "content6"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList(comment1, comment2, comment3);

            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");
        }

        /* Case 3: null의 모든 댓글 조회 */
        {
            // 준비
            String nickname = null;

            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "null 모든 댓글을 출력");
        }

        /* Case 4: ""의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "";

            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "\"\"의 모든 댓글을 출력");
        }

        /* Case 5: "i"의 모든 댓글 조회 */
        {
            // 준비
            String nickname = "i";

            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "i의 모든 댓글을 출력");
        }
    }
}