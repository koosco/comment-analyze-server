package dev.practice.khuyoutubeserver.comment.app;

import dev.practice.khuyoutubeserver.comment.app.dto.*;
import dev.practice.khuyoutubeserver.comment.domain.Comment;
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

    public WordCountResponseDto getWordCounts(String url, CommentOrder order, int size, String nextToken) {
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
                .limit(size)
                .map(entry -> new WordCount(entry.getKey(), entry.getValue()))
                .toList();

        return new WordCountResponseDto(wordCountList, commentsDto.nextPageToken());
    }

    public WordDetailsResponseDto getDetailComments(String url, CommentOrder order, String nextToken, String word) {
        CommentsDto commentsDto = youtubeService.getAllComments(url, order, nextToken);

        List<WordDetailResponseDto> actualComments = commentsDto.comments().comments().stream()
                .map(Comment::getText)
                .filter(text -> {
                    // 댓글 텍스트 형태소 분석 후 필터된 단어 리스트 추출
                    Words filtered = komoranService.getFilteredAndNormalizedWords(text);
                    return filtered.contains(word); // 포함 여부 확인
                })
                .map(WordDetailResponseDto::new)
                .toList();
        return new WordDetailsResponseDto(actualComments, commentsDto.nextPageToken());
    }
}
