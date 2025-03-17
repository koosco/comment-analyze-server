package dev.practice.khuyoutubeserver.comment.app.dto;

import dev.practice.khuyoutubeserver.comment.domain.Comments;

public record CommentsDto(Comments comments, String nextPageToken) {

}
