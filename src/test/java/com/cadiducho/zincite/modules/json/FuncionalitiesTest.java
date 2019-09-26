package com.cadiducho.zincite.modules.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncionalitiesTest {

    static Moshi moshi = JsonModule.getMoshi();

    @Test
    @DisplayName("Funcionality -> Check name")
    void testFuncionalitiesWithName() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonText = ""
                + "{"
                + "   \"name\": \"nameOne\", "
                + "   \"type\": \"text\", "
                + "   \"text\": \"Hola!\","
                + "   \"reply_to\": \"none\""
                + "}";
        CommandFunctionality funcionalityText = adapter.fromJson(jsonText);
        assertEquals(funcionalityText.getName(), "nameOne");
    }

    @Test
    @DisplayName("Funcionality -> Text")
    void readCommandFucionalityText() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonText = ""
                + "{"
                + "   \"type\": \"text\", "
                + "   \"text\": \"Hola!\","
                + "   \"reply_to\": \"none\""
                + "}";
        CommandFunctionality funcionalityText = adapter.fromJson(jsonText);
        assertEquals(funcionalityText, TextFunctionality.builder().text("Hola!").build());
    }

    @Test
    @DisplayName("Funcionality -> Image")
    void readCommandFucionalityImage() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"image\","
                + "   \"image_id\": \"5815687\","
                + "   \"reply_to\": \"answered\""
                + "}";
        CommandFunctionality funcionalityImage = adapter.fromJson(jsonImage);
        assertEquals(funcionalityImage, ImageFunctionality.builder().imageId("5815687").replyPattern(ReplyPattern.TO_ANSWERED).build());
    }

    @Test
    @DisplayName("Funcionality -> Gif")
    void readCommandFucionalityGif() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"gif\","
                + "   \"gif_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality funcionalityImage = adapter.fromJson(jsonImage);
        assertEquals(funcionalityImage, GifFunctionality.builder().gifId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("Funcionality -> Video")
    void readCommandFucionalityVideo() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"video\","
                + "   \"video_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality funcionalityImage = adapter.fromJson(jsonImage);
        assertEquals(funcionalityImage, VideoFunctionality.builder().videoId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }

    @Test
    @DisplayName("Funcionality -> Voice")
    void readCommandFucionalityVoice() throws IOException {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String jsonImage = ""
                + "{"
                + "   \"type\": \"voice\","
                + "   \"voice_id\": \"5815687\","
                + "   \"reply_to\": \"original\""
                + "}";
        CommandFunctionality funcionalityImage = adapter.fromJson(jsonImage);
        assertEquals(funcionalityImage, VoiceFunctionality.builder().voiceId("5815687").replyPattern(ReplyPattern.TO_ORIGINAL).build());
    }
}
