package com.cadiducho.zincite.modules.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonCommandTests {

    static Moshi moshi = JsonModule.getMoshi();

    @Test
    void testEmptyTypeThrowsError() {
        JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
        String json = "{}";
        assertThrows(JsonDataException.class, () -> adapter.fromJson(json), "Missing label for tye");
    }

    @Test
    void parseJsonCommand() throws IOException {
        JsonAdapter<JsonCommand> adapter = moshi.adapter(JsonCommand.class);
        String json = ""
                + "{" +
                "   \"aliases\": " +
                "       [" +
                "           \"ping\"" +
                "       ]," +
                "   \"description\": \"ICMP\"," +
                "   \"functionalities\": " +
                "       [" +
                "           {" +
                "               \"type\": \"text\"," +
                "               \"text\": \"pong\"," +
                "               \"reply_to\": \"none\"" +
                "           }," +
                "           {" +
                "               \"type\": \"text\"," +
                "               \"text\": \"pung\"," +
                "               \"reply_to\": \"none\"" +
                "           }," +
                "           {" +
                "               \"type\": \"image\"," +
                "               \"image_id\": \"5684\"," +
                "               \"reply_to\": \"none\"" +
                "           }" +
                "       ]" +
                "   }";
        JsonCommand parsedCommand = adapter.fromJson(json);
        assertEquals(parsedCommand, JsonCommand.builder()
                .aliases(Collections.singletonList("ping"))
                .description("ICMP")
                .functionalities(Arrays.asList(
                        TextFunctionality.builder().text("pong").build(),
                        TextFunctionality.builder().text("pung").build(),
                        ImageFunctionality.builder().imageId("5684").build()))
                .build());

        assertEquals("<code>ping</code>: ICMP", parsedCommand.getUsage());
    }

    @Test
    @DisplayName("Commands -> Check reusables")
    void testFuncionalitiesWithName() throws IOException {
        JsonModule module = new JsonModule();

        JsonAdapter<CommandFunctionality> functionalityAdapter = moshi.adapter(CommandFunctionality.class);
        String jsonFunctionality = ""
                + "{"
                + "   \"name\": \"reusableName\", "
                + "   \"type\": \"text\", "
                + "   \"text\": \"Hola!\","
                + "   \"reply_to\": \"none\""
                + "}";
        CommandFunctionality funcionalityText = functionalityAdapter.fromJson(jsonFunctionality);
        assertNotNull(funcionalityText);
        module.registerReusableFunctionality(funcionalityText);

        JsonAdapter<JsonCommand> commandAdapter = moshi.adapter(JsonCommand.class);
        String jsonCommand = ""
                + "{" +
                "   \"aliases\": " +
                "       [" +
                "           \"ping\"" +
                "       ]," +
                "   \"description\": \"ICMP\"," +
                "   \"functionalities\": " +
                "       [" +
                "           {" +
                "               \"type\": \"image\"," +
                "               \"image_id\": \"5684\"," +
                "               \"reply_to\": \"none\"" +
                "           }," +
                "           {" +
                "               \"type\": \"reusable\"," +
                "               \"name\": \"reusableName\"" +
                "           }" +
                "       ]" +
                "   }";
        JsonCommand parsedCommand = commandAdapter.fromJson(jsonCommand);
        assertNotNull(parsedCommand);
        module.replaceReusableFunctionalities(parsedCommand);

        assertTrue(parsedCommand.getFunctionalities().contains(funcionalityText));
    }
}