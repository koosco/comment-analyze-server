package dev.practice.khuyoutubeserver.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordsTest {

    List<Word> wordList;

    @BeforeEach
    void init() {
        wordList = List.of(
            new Word("대한민국", "NNG"),
            new Word("은", "JX"),
            new Word("민주공화국", "NNG"),
            new Word("이", "VCP"),
            new Word("다", "EFN")
        );
    }

    @Test
    void givenWordList_whenGetWords_thenReturnWordList() {
        // given
        Words words = new Words(wordList);

        // when
        List<Word> result = words.words();

        // then
        assertThat(result).isEqualTo(wordList);
        System.out.println(result);
    }

    @Test
    void givenWordList_whenGetOnlyWords_thenReturnStringList() {
        // given
        Words words = new Words(wordList);

        // when
        List<String> result = words.getOnlyWords();

        // then
        assertThat(result).containsExactly("대한민국", "은", "민주공화국", "이", "다");
        System.out.println(result);
    }

    @Test
    void givenWordList_whenFilterByNouns_thenReturnOnlyNounsWords() {
        // given
        Words words = new Words(wordList);

        // when
        List<Word> result = words.filterByTag("NNG");

        result.forEach(System.out::println);

        // then
        assertThat(result).containsExactly(
            new Word("대한민국", "NNG"),
            new Word("민주공화국", "NNG")
        );
        System.out.println(result);
    }
}