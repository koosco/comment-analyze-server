package dev.practice.khuyoutubeserver.comment.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Words(List<Word> words) {

    public Words(List<Word> words) {
        this.words = Collections.unmodifiableList(words);
    }

    public List<String> getOnlyWords() {
        return words.stream()
            .map(Word::getWord)
            .toList();
    }

    public List<Word> filterByTag(String tag) {
        return words.stream()
            .filter(word -> word.getTag().equals(tag))
            .toList();
    }

    public Map<String, Integer> getWordFrequency() {
        return words.stream()
            .collect(Collectors.toMap(Word::getWord, word -> 1, Integer::sum));
    }
}
