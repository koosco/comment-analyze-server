package dev.practice.khuyoutubeserver.comment.app;

import dev.practice.khuyoutubeserver.comment.domain.Word;
import dev.practice.khuyoutubeserver.comment.domain.Words;
import java.util.List;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KomoranService {

    private final Komoran komoran;

    public List<Words> getNouns(List<String> texts) {
        return texts.stream()
            .map(this::getNouns)
            .toList();
    }

    public Words getNouns(String text) {
        KomoranResult result = komoran.analyze(text);
        return new Words(result.getNouns().stream()
            .map(noun -> new Word(noun, "Noun"))
            .toList());
    }

    public List<Words> getWordAndTag(List<String> texts) {
        return texts.stream()
            .map(this::getWordAndTag)
            .toList();
    }

    public Words getWordAndTag(String text) {
        KomoranResult result = komoran.analyze(text);
        return new Words(result.getList().stream()
            .map(pair -> new Word(pair.getFirst(), pair.getSecond()))
            .toList());
    }
}
