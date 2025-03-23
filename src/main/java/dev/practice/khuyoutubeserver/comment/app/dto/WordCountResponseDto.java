package dev.practice.khuyoutubeserver.comment.app.dto;

import java.util.List;

public record WordCountResponseDto(List<WordCount> wordCounts, String nextPageToken) {

}
