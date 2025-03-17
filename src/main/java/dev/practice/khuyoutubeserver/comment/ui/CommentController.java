package dev.practice.khuyoutubeserver.comment.ui;

import dev.practice.khuyoutubeserver.comment.app.CommentFacade;
import dev.practice.khuyoutubeserver.comment.app.dto.WordCountResponseDto;
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

    private final CommentFacade wordService;

    @GetMapping
    public ResponseDto<WordCountResponseDto> getWordCounts(
        @RequestParam String url,
        @RequestParam(required = false) CommentOrder order,
        @RequestParam(required = false) String nextToken
    ) {
        return ResponseDto.ok(wordService.getWordCounts(url, order, nextToken));
    }
}
