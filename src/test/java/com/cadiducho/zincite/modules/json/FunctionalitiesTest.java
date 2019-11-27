package com.cadiducho.zincite.modules.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalitiesTest {

    static Moshi moshi = JsonModule.getMoshi();

    @Test
    @DisplayName("functionality -> Check name")
    void testFunctionalitiesWithName() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonText = ""
                + "{"
                + "   \"name\": \"nameOne\", "
                + "   \"type\": \"text\", "
                + "   \"text\": \"Hola!\","
                + "   \"reply_to\": \"none\""
                + "}";
        CommandFunctionality functionalityText = adapter.fromJson(jsonText);
        assertEquals(functionalityText.getName(), "nameOne");
    }

    @Test
    @DisplayName("functionality -> Text")
    void readCommandFunctionalityText() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonText = ""
                + "{"
                + "   \"type\": \"text\", "
                + "   \"text\": \"Hola!\","
                + "   \"reply_to\": \"none\""
                + "}";
        CommandFunctionality functionalityText = adapter.fromJson(jsonText);
        assertEquals(functionalityText, TextFunctionality.builder().text("Hola!").build());
    }

    @Test
    @DisplayName("functionality -> Image")
    void readCommandFucntionalityImage() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"image\","
                + "   \"image_id\": \"5815687\","
                + "   \"reply_to\": \"answered\""
                + "}";
        CommandFunctionality functionalityImage = adapter.fromJson(jsonImage);
        assertEquals(functionalityImage, ImageFunctionality.builder().imageId("5815687").replyPattern(ReplyPattern.TO_ANSWERED).build());
    }

    @Test
    @DisplayName("functionality -> Gif")
    void readCommandFunctionalityGif() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"gif\","
                + "   \"gif_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality functionalityGif = adapter.fromJson(jsonImage);
        assertEquals(functionalityGif, GifFunctionality.builder().gifId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("functionality -> Video")
    void readCommandFunctionalityVideo() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"video\","
                + "   \"video_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality functionalityVideo = adapter.fromJson(jsonImage);
        assertEquals(functionalityVideo, VideoFunctionality.builder().videoId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("functionality -> Voice")
    void readCommandFunctionalityVoice() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"voice\","
                + "   \"voice_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality functionalityVoice = adapter.fromJson(jsonImage);
        assertEquals(functionalityVoice, VoiceFunctionality.builder().voiceId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("functionality -> Audio")
    void readCommandFunctionalityAudio() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"audio\","
                + "   \"audio_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality functionalityAudio = adapter.fromJson(jsonImage);
        assertEquals(functionalityAudio, AudioFunctionality.builder().audioId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("functionality -> Venue")
    void readCommandFunctionalityVenue() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"venue\","
                + "   \"latitude\": \"50.436891\","
                + "   \"longitude\": \"5.972211\","
                + "   \"title\": \"Spa\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality functionalityImage = adapter.fromJson(jsonImage);
        assertEquals(functionalityImage, VenueFunctionality.builder().latitude(50.436891F).longitude(5.972211F).title("Spa").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }
}
