package dev.practice.khuyoutubeserver.comment.app.dto;

import java.util.List;

public record WordDetailsResponseDto(List<WordDetailResponseDto> comments, String nexToken) {
}
