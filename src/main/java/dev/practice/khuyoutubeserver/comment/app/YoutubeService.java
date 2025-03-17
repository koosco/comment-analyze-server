package dev.practice.khuyoutubeserver.comment.app;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import dev.practice.khuyoutubeserver.comment.app.dto.CommentsDto;
import dev.practice.khuyoutubeserver.comment.app.util.YoutubeUrlParser;
import dev.practice.khuyoutubeserver.comment.domain.Comment;
import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import dev.practice.khuyoutubeserver.comment.domain.Comments;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final YouTube youTube;

    public CommentsDto getAllComments(String videoUrl, CommentOrder order, String nextPageToken) {
        String videoId = YoutubeUrlParser.extractVideoId(videoUrl);
        order = initOrder(order);
        List<Comment> comments = new ArrayList<>();
        String nextToken = null;

        try {
            YouTube.CommentThreads.List request = youTube.commentThreads()
                .list("snippet,replies")
                .setKey(apiKey)
                .setVideoId(videoId)
                .setMaxResults(100L)
                .setOrder(order.getOrder())
                .setPageToken(nextPageToken);

            CommentThreadListResponse response = request.execute();
            nextToken = response.getNextPageToken();

            response.getItems().forEach(thread -> {
                com.google.api.services.youtube.model.Comment topLevelComment = thread.getSnippet()
                    .getTopLevelComment();
                addTopLevelComment(thread, comments);
                List<Comment> childrenComments = getReplies(thread, topLevelComment.getId());
                comments.addAll(childrenComments);
            });
        } catch (IOException e) {
            // 예외 처리 로직 추가
            e.printStackTrace();
        }

        return new CommentsDto(new Comments(comments), nextToken);
    }

    private CommentOrder initOrder(CommentOrder commentOrder) {
        if (commentOrder != null) {
            return commentOrder;
        }
        return CommentOrder.DEFAULT;
    }

    private static void addTopLevelComment(CommentThread thread, List<Comment> comments) {
        com.google.api.services.youtube.model.Comment topLevelComment = thread.getSnippet().getTopLevelComment();
        comments.add(new Comment(
            topLevelComment.getId(),
            topLevelComment.getSnippet().getAuthorDisplayName(),
            topLevelComment.getSnippet().getTextDisplay(),
            topLevelComment.getSnippet().getLikeCount(),
            topLevelComment.getSnippet().getPublishedAt().toStringRfc3339(),
            null
        ));
    }

    private List<Comment> getReplies(CommentThread thread, String parentId) {
        List<Comment> commentList = new ArrayList<>();
        if (thread.getReplies() == null) {
            return new ArrayList<>();
        }

        thread.getReplies().getComments().forEach(reply -> commentList.add(new Comment(
            reply.getId(),
            reply.getSnippet().getAuthorDisplayName(),
            reply.getSnippet().getTextDisplay(),
            reply.getSnippet().getLikeCount(),
            reply.getSnippet().getPublishedAt().toStringRfc3339(),
            parentId
        )));

        return commentList;
    }
}
