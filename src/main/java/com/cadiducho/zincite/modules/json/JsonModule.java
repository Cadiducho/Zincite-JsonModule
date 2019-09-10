package com.cadiducho.zincite.modules.json;

import com.cadiducho.zincite.ZinciteBot;
import com.cadiducho.zincite.api.command.CommandManager;
import com.cadiducho.zincite.api.module.ModuleInfo;
import com.cadiducho.zincite.api.module.ZinciteModule;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import lombok.Getter;
import lombok.extern.java.Log;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log
@ModuleInfo(name = "JsonModule", description = "Modulo de utilidades para cargar los json commands")
public class JsonModule implements ZinciteModule {

    private CommandManager commandManager;
    @Getter private static final Moshi moshi = new Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(CommandFuncionality.class, "type")
                    .withSubtype(TextFuncionality.class, "text")
                    .withSubtype(GifFuncionality.class, "gif")
                    .withSubtype(ImageFuncionality.class, "image")
                    .withSubtype(VideoFuncionality.class, "video")
                    .withSubtype(VoiceFuncionality.class, "voice")
            )
            .build();

    @Override
    public void onLoad() {
        commandManager = ZinciteBot.getInstance().getCommandManager();

        File commandsFolder = new File("commands");
        if (!commandsFolder.exists()) {
            commandsFolder.mkdirs();
        }

        try {
            Files.newDirectoryStream(commandsFolder.toPath(),
                    path -> path.toString().endsWith(".json"))
                    .forEach(this::registerJson);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        log.info("Zincite Json module loaded");
    }


    private void registerJson(Path path) {
        try {
            BufferedSource source = Okio.buffer(Okio.source(path));

            JsonAdapter<JsonCommand> adapter = moshi.adapter(JsonCommand.class);
            JsonCommand parsedComand = adapter.fromJson(source);
            commandManager.register(parsedComand);
            log.info("Registrando comando " + parsedComand.getName());
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }
}
