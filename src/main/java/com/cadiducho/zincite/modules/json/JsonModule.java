package com.cadiducho.zincite.modules.json;

import com.cadiducho.zincite.ZinciteBot;
import com.cadiducho.zincite.ZinciteException;
import com.cadiducho.zincite.api.command.CommandManager;
import com.cadiducho.zincite.api.module.ModuleInfo;
import com.cadiducho.zincite.api.module.ZinciteModule;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
@ModuleInfo(name = JsonModule.MODULE_NAME, description = "Modulo de utilidades para cargar los json commands")
public class JsonModule implements ZinciteModule {

    public static final String MODULE_NAME = "JsonModule";
    private CommandManager commandManager;

    @Setter @Getter private String funcionalitiesPath = "functionalities";
    @Setter @Getter private String commandsPath = "commands";

    @Getter private static final Moshi moshi = new Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(CommandFunctionality.class, "type")
                    .withSubtype(TextFunctionality.class, "text")
                    .withSubtype(GifFunctionality.class, "gif")
                    .withSubtype(ImageFunctionality.class, "image")
                    .withSubtype(VideoFunctionality.class, "video")
                    .withSubtype(VoiceFunctionality.class, "voice")
                    .withSubtype(AudioFunctionality.class, "audio")
                    .withSubtype(VenueFunctionality.class, "venue")
                    .withSubtype(ReusableFunctionality.class, "reusable")
            )
            .build();

    private Map<String, CommandFunctionality> reusableFunctionalities = new HashMap<>();

    private int totalCommands = 0;
    private int failedFunctionalities = 0;
    private int failedConmmands = 0;

    @Override
    public void onLoad() {
        this.commandManager = ZinciteBot.getInstance().getCommandManager();

        File funcionalitiesFolder = new File(funcionalitiesPath);
        if (!funcionalitiesFolder.exists()) {
            funcionalitiesFolder.mkdirs();
        }
        File commandsFolder = new File(commandsPath);
        if (!commandsFolder.exists()) {
            commandsFolder.mkdirs();
        }

        try {
            Files.newDirectoryStream(funcionalitiesFolder.toPath(),
                    path -> path.toString().endsWith(".json"))
                    .forEach(this::loadReusableFunctionality);

            Files.newDirectoryStream(commandsFolder.toPath(),
                    path -> path.toString().endsWith(".json"))
                    .forEach(this::loadCommand);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        log.info("Zincite Json module loaded");
        log.info(totalCommands + " commands where loaded (" + failedConmmands + " failed)");
        log.info(reusableFunctionalities.size() + " functionalities where loaded (" + failedFunctionalities + " failed)");
    }

    private void loadReusableFunctionality(Path path) {
        try {
            log.info("Loading functionality from " + path.getFileName());
            BufferedSource source = Okio.buffer(Okio.source(path));

            JsonAdapter<CommandFunctionality> adapter = moshi.adapter(CommandFunctionality.class);
            CommandFunctionality functionality = adapter.fromJson(source);
            if (functionality == null) {
                throw new ZinciteException("Functionality from " + path.getFileName() + " is null");
            }
            if (functionality.getName() == null) {
                throw new ZinciteException("Name of functionality from " + path.getFileName() + " is null");
            }

            if (!(functionality instanceof ReusableFunctionality)) {
                registerReusableFunctionality(functionality);
            }
        } catch (IOException | JsonDataException | ZinciteException e) {
            log.severe("Error creating independent functionality from " + path.getFileName());
            log.severe(e.getMessage());
            failedFunctionalities++;
        }
    }

    public void registerReusableFunctionality(CommandFunctionality functionality) {
        log.info("Registering " + functionality.getClass().getSimpleName() + " "+  functionality.getName());
        reusableFunctionalities.put(functionality.getName(), functionality);
    }

    private void loadCommand(Path path) {
        try {
            BufferedSource source = Okio.buffer(Okio.source(path));

            JsonAdapter<JsonCommand> adapter = moshi.adapter(JsonCommand.class);
            JsonCommand parsedCommand = adapter.fromJson(source);
            if (parsedCommand == null) {
                throw new ZinciteException("Command from " + path.getFileName() + " is null");
            }
            if (parsedCommand.getName() == null) {
                throw new ZinciteException("Name of command from " + path.getFileName() + " is null");
            }
            if (parsedCommand.getFunctionalities() == null) {
                throw new ZinciteException("Functionalities of " + parsedCommand.getName() + " are null");
            }

            registerCommand(parsedCommand);
        } catch (IOException | JsonDataException | ZinciteException e) {
            log.severe("Error creating the command from " + path.getFileName());
            log.severe(e.getMessage());
            failedConmmands++;
        }
    }

    /**
     * Registrar correctamente un JsonCommand al sistema
     * @param parsedCommand el JsonCommand
     */
    public void registerCommand(JsonCommand parsedCommand) {
        log.info("Registering commnand " + parsedCommand.getName());
        // Reemplazar las funcionalidades reusables por las ya registradas
        replaceReusableFunctionalities(parsedCommand);

        // Agregar el comando al sistema
        commandManager.register(parsedCommand);
        totalCommands++;
    }

    public void replaceReusableFunctionalities(JsonCommand command) {
        List<CommandFunctionality> functionalities = new ArrayList<>(command.getFunctionalities());
        for (CommandFunctionality functionality : functionalities) {
            if (functionality instanceof ReusableFunctionality) {
                String name = functionality.getName();
                CommandFunctionality newFunctionality = reusableFunctionalities.get(name);
                command.getFunctionalities().remove(functionality);
                if (newFunctionality != null) {
                    command.getFunctionalities().add(newFunctionality);
                }
            }
        }
    }
}
