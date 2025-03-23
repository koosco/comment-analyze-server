package dev.practice.khuyoutubeserver.comment.app.util;

import dev.practice.khuyoutubeserver.comment.domain.Word;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Stopwords {

    private static Set<String> words;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("static/stopwords.txt");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                words = reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .collect(Collectors.toSet());
            }
        } catch (Exception e) {
            e.printStackTrace();
            words = new HashSet<>();
        }
    }

    public static boolean isStopWord(Word word) {
        return words.contains(word.getWord());
    }

    public static boolean isNotStopWord(Word word) {
        return !isStopWord(word);
    }

    public static Optional<String> filter(String text) {
        if (words.contains(text)) {
            return Optional.empty();
        }
        return Optional.of(text);
    }

    private Stopwords() {
    }
}
