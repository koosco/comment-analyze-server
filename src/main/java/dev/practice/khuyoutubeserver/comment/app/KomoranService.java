package dev.practice.khuyoutubeserver.comment.app;

import dev.practice.khuyoutubeserver.comment.domain.Word;
import dev.practice.khuyoutubeserver.comment.domain.Words;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // ✅ 여러 텍스트 처리
    public List<Words> getFilteredWords(List<String> texts) {
        return texts.stream()
                .map(this::getFilteredWords)
                .toList();
    }

    // ✅ 단일 텍스트에서 NNG, NNP, VV, VA 필터링
    public Words getFilteredWords(String text) {
        KomoranResult result = komoran.analyze(text);

        return new Words(result.getTokenList().stream()
                .filter(token -> {
                    String pos = token.getPos();
                    return pos.equals("NNG") || pos.equals("NNP") || pos.equals("VV") || pos.equals("VA");
                })
                .map(token -> new Word(token.getMorph(), token.getPos()))
                .toList());
    }

    public List<Words> getFilteredAndNormalizedWords(List<String> texts) {
        return texts.stream()
                .filter(s -> !s.isEmpty())
                .map(this::getFilteredAndNormalizedWords)
                .toList();
    }

    public Words getFilteredAndNormalizedWords(String text) {
        KomoranResult result = komoran.analyze(text);

        return new Words(result.getTokenList().stream()
                .filter(token -> {
                    String pos = token.getPos();
                    return pos.equals("NNG") || pos.equals("NNP") || pos.equals("VV") || pos.equals("VA");
                })
                .map(token -> {
                    String morph = token.getMorph();
                    if (token.getPos().equals("VV") || token.getPos().equals("VA")) {
                        morph += "다"; // 동사/형용사 원형화
                    }
                    return new Word(morph, token.getPos());
                })
                .toList());
    }
}
