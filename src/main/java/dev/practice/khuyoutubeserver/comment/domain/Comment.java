package dev.practice.khuyoutubeserver.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Comment {

    private String commentId;
    private String authorName;
    private String text;
    private long likeCount;
    private String publishedAt;
    private String parentCommentId;

    public Comment getProcessedComment() {
        if (text != null) {
            text = text.replaceAll("<[^>]*>", "");
            text = text.replaceAll("\\b\\d{2}:\\d{2}(?::\\d{2})?\\b", "");
            text = text.replaceAll("\\b[a-zA-Z]+\\b", "");
            text = text.strip();

        }
        return new Comment(commentId, authorName, text, likeCount, publishedAt, parentCommentId);
    }
}
