package dev.practice.khuyoutubeserver.comment.domain;

import lombok.Getter;

@Getter
public enum CommentOrder {
    TIME("time"),
    DEFAULT("time"),
    RELEVANCE("relevance");

    private final String order;

    CommentOrder(String order) {
       this.order = order;
    }
}
