package dev.practice.khuyoutubeserver.comment.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUrlParser {

    private static final String VIDEO_ID_REGEX = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]{11})(?:&.*)?$";
    private static final Pattern VIDEO_ID_PATTERN = Pattern.compile(VIDEO_ID_REGEX);

    public static String extractVideoId(String url) {
        Matcher matcher = VIDEO_ID_PATTERN.matcher(url);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Invalid YouTube URL: " + url);
        }
    }
}
