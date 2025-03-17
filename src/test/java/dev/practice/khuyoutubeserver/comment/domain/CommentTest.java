package dev.practice.khuyoutubeserver.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void givenHtmlContainingString_whenProcessText_thenRemoveHtmlTag() {
        // given
        String html = "<p>안녕하세요</p>";
        Comment comment = new Comment(null, null, html, 0, null, null);

        // when
        comment = comment.getProcessedComment();

        // then
        assertThat(comment.getText()).isEqualTo("안녕하세요");
    }

    @Test
    void givenHalfHtmlContainingString_whenProcessText_thenRemoveHtmlTag() {
        // given
        String html = "<p>안녕하세요";
        Comment comment = new Comment(null, null, html, 0, null, null);

        // when
        comment = comment.getProcessedComment();

        // then
        assertThat(comment.getText()).isEqualTo("안녕하세요");
    }

    @Test
    void givenHourMinuteSecondContainingString_whenProcessText_thenRemoveTime() {
        // given
        String html = "안녕하세요00:00:00";
        Comment comment = new Comment(null, null, html, 0, null, null);

        // when
        comment = comment.getProcessedComment();

        // then
        assertThat(comment.getText()).isEqualTo("안녕하세요");
    }

    @Test
    void givenMinuteSecondContainingString_whenProcessText_thenRemoveTime() {
        // given
        String html = "안녕하세요12:00";
        Comment comment = new Comment(null, null, html, 0, null, null);

        // when
        comment = comment.getProcessedComment();

        // then
        assertThat(comment.getText()).isEqualTo("안녕하세요");
    }

    @Test
    void givenSpaceContainingString_whenProcessText_thenRemoveSpace() {
        // given
        String html = "안녕하세요  ";
        Comment comment = new Comment(null, null, html, 0, null, null);

        // when
        comment = comment.getProcessedComment();

        // then
        assertThat(comment.getText()).isEqualTo("안녕하세요");
    }
}