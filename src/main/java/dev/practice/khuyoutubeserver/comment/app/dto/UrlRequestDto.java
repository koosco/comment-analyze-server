package dev.practice.khuyoutubeserver.comment.app.dto;

import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import jakarta.annotation.Nullable;

public record UrlRequestDto(
    String url,

    @Nullable
    CommentOrder order,

    @Nullable
    String nextToken) { }
