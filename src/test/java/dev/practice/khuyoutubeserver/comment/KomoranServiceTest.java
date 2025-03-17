package dev.practice.khuyoutubeserver.comment;

import dev.practice.khuyoutubeserver.comment.app.KomoranService;
import dev.practice.khuyoutubeserver.comment.domain.Words;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KomoranServiceTest {

    final String text = "대한민국은 민주공화국이다.";

    @Autowired
    private KomoranService komoranService;

    @Test
    void analyzeKorean() {
        Words words = komoranService.getWordAndTag(text);
        words.words().forEach(System.out::println);
    }

    @Test
    void givenString_whenGetNouns_thenReturnOnlyNounsWordList() {
        Words words = komoranService.getNouns(text);
        words.words().forEach(System.out::println);
    }
}