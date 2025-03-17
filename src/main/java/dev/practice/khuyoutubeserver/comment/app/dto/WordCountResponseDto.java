package dev.practice.khuyoutubeserver.comment.app.dto;

import java.util.Map;

public record WordCountResponseDto(Map<String, Integer> wordCounts, String nextPageToken) {

}
