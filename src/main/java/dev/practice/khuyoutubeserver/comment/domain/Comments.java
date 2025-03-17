package dev.practice.khuyoutubeserver.comment.domain;

import java.util.Collections;
import java.util.List;

public record Comments(List<Comment> comments) {

    public Comments(List<Comment> comments) {
        this.comments = Collections.unmodifiableList(comments);
    }

    public List<String> getOnlyComments() {
        return comments.stream()
            .map(Comment::getText)
            .toList();
    }

    public List<Comment> getPreprocessedComments() {
        return comments.stream()
            .map(Comment::getProcessedComment)
            .toList();
    }

    public List<String> getPreprocessedCommentTexts() {
        return comments.stream()
            .map(Comment::getProcessedComment)
            .map(Comment::getText)
            .toList();
    }
}
