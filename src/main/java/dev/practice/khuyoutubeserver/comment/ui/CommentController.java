package dev.practice.khuyoutubeserver.comment.ui;

import dev.practice.khuyoutubeserver.comment.app.CommentFacade;
import dev.practice.khuyoutubeserver.comment.app.dto.WordCountResponseDto;
import dev.practice.khuyoutubeserver.comment.app.dto.WordDetailsResponseDto;
import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import dev.practice.khuyoutubeserver.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/words")
public class CommentController {

    private final CommentFacade commentFacade;

    @GetMapping
    public ResponseDto<WordCountResponseDto> getWordCounts(
        @RequestParam("url") String url,
        @RequestParam(value = "order", required = false) CommentOrder order,
        @RequestParam(value = "size", defaultValue = "20") int size,
        @RequestParam(value = "nextToken", required = false) String nextToken
    ) {
        return ResponseDto.ok(commentFacade.getWordCounts(url, order, size, nextToken));
    }

    @GetMapping("/detail")
    public ResponseDto<WordDetailsResponseDto> getDetailSentences(
            @RequestParam("url") String url,
            @RequestParam(value = "order", required = false) CommentOrder order,
            @RequestParam(value = "nextToken", required = false) String nextToken,
            @RequestParam("word") String word) {
        return ResponseDto.ok(commentFacade.getDetailComments(url, order, nextToken, word));
    }
}
