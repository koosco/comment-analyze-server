package dev.practice.khuyoutubeserver.comment.app;

import dev.practice.khuyoutubeserver.comment.app.dto.CommentsDto;
import dev.practice.khuyoutubeserver.comment.app.dto.WordCountResponseDto;
import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import dev.practice.khuyoutubeserver.comment.domain.Words;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final YoutubeService youtubeService;
    private final KomoranService komoranService;

    public WordCountResponseDto getWordCounts(String url, CommentOrder order, String nextToken) {
        CommentsDto commentsDto = youtubeService.getAllComments(url, order, nextToken);
        List<String> preprocessedCommentTexts = commentsDto.comments().getPreprocessedCommentTexts();
        List<Words> words = komoranService.getWordAndTag(preprocessedCommentTexts);
        Map<String, Integer> wordCounts = words.stream()
            .map(Words::getWordFrequency)
            .flatMap(m -> m.entrySet().stream())
            .collect(Collectors.toMap(
                Entry::getKey,
                Entry::getValue,
                Integer::sum
            ));

        return new WordCountResponseDto(wordCounts, commentsDto.nextPageToken());
    }
}
