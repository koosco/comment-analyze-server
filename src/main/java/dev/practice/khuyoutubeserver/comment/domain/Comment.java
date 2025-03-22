package dev.practice.khuyoutubeserver.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        String processedText = this.text;
        if (text != null) {
            // 1. HTML 태그 제거
            processedText = processedText.replaceAll("<[^>]*>", "");

            // 2. 시간 표현 제거 (00:00 or 00:00:00)
            processedText = processedText.replaceAll("\\b\\d{2}:\\d{2}(?::\\d{2})?\\b", "");

            // 3. 영단어 제거 (영어 단어)
            processedText = processedText.replaceAll("\\b[a-zA-Z]+\\b", "");

            // 4. 특수문자 제거 (숫자, 한글, 공백 제외 모두 제거)
            processedText = processedText.replaceAll("[^\\p{IsHangul}\\p{IsDigit}\\s]", "");

            // 5. 자음/모음 제거 (ㄱ-ㅎ, ㅏ-ㅣ)
            processedText = processedText.replaceAll("[ㄱ-ㅎㅏ-ㅣ]", "");

            // 6. 앞뒤 공백 제거
            processedText = processedText.strip();

            // 7. 중복 공백 제거
            processedText = processedText.replaceAll("\\s+", " ");

        }
        log.info("commentId={}, processedText={}", commentId, processedText);
        return new Comment(commentId, authorName, processedText, likeCount, publishedAt, parentCommentId);
    }
}
