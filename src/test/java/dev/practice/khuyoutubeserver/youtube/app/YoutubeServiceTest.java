package dev.practice.khuyoutubeserver.youtube.app;

import dev.practice.khuyoutubeserver.comment.app.YoutubeService;
import dev.practice.khuyoutubeserver.comment.app.dto.CommentsDto;
import dev.practice.khuyoutubeserver.comment.domain.CommentOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YoutubeServiceTest {

    @Autowired
    YoutubeService youtubeService;

    static final String TEST_VIDEO_ID = "https://www.youtube.com/watch?v=w4A3BqBTO9A";

    @Test
    void youtubeApiTest() {
        CommentsDto dto = youtubeService.getAllComments(TEST_VIDEO_ID, CommentOrder.TIME, null);
        System.out.println(dto);
    }
}