package dev.practice.khuyoutubeserver.comment.app;

import dev.practice.khuyoutubeserver.comment.app.dto.CommentsDto;
import dev.practice.khuyoutubeserver.comment.app.dto.WordCount;
import dev.practice.khuyoutubeserver.comment.app.dto.WordCountResponseDto;
import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import dev.practice.khuyoutubeserver.comment.domain.Words;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final YoutubeService youtubeService;
    private final KomoranService komoranService;
    private final PostProcessService postProcessService;

    public WordCountResponseDto getWordCounts(String url, CommentOrder order, String nextToken) {
        CommentsDto commentsDto = youtubeService.getAllComments(url, order, nextToken);

        // 댓글 텍스트 전처리
        List<String> preprocessedCommentTexts = commentsDto.comments().getPreprocessedCommentTexts();

        // 댓글별 불변객체 리스트 반환 (word, tag)
        List<Words> wordTagList = komoranService.getFilteredAndNormalizedWords(preprocessedCommentTexts);


        Map<String, Integer> wordCounts = wordTagList.stream()
                .map(Words::getWordFrequency)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Entry::getKey,
                        Entry::getValue,
                        Integer::sum
                ));

        // Map -> List<WordCount> 로 변환
        List<WordCount> wordCountList = wordCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .filter(entry -> entry.getValue() > 2)
                .map(entry -> new WordCount(entry.getKey(), entry.getValue()))
                .toList();

        return new WordCountResponseDto(wordCountList, commentsDto.nextPageToken());
    }
}
