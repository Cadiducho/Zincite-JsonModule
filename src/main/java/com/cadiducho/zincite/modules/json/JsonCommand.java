package com.cadiducho.zincite.modules.json;

import com.cadiducho.telegrambotapi.Chat;
import com.cadiducho.telegrambotapi.Message;
import com.cadiducho.telegrambotapi.User;
import com.cadiducho.telegrambotapi.exception.TelegramException;
import com.cadiducho.zincite.api.command.BotCommand;
import com.cadiducho.zincite.api.command.CommandContext;
import com.cadiducho.zincite.api.module.ZinciteModule;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Random;

/**
 * Comandos simples para ser cargados desde texto mediante formato json.
 * Estos comandos responderán a una serie de alias mediante acciones directas y sencillas, como enviar un mensaje de texto, una foto o un gif.
 * Este tipo de respuestas sencillas son del tipo {@link CommandFunctionality}.
 */
@Getter
@Builder
@EqualsAndHashCode
public class JsonCommand implements BotCommand {

    private List<String> aliases;
    private String module;
    private List<CommandFunctionality> functionalities;
    private String description;

    @Override
    public String getName() {
        if (aliases == null) return null; // No aliases registered
        if (aliases.isEmpty()) return null; // Object created but any registered
        return aliases.get(0); // ok, return the first param
    }

    @Override
    public ZinciteModule getModule() {
        if (FRAMEWORK_BOT == null) {
            return null; //FixMe: Solución temporal a usar JsonCommand.getModule() en JUnit 5, donde FRAMEWORK_BOT va a ser null
        }
        return FRAMEWORK_BOT.getModuleManager().getModule(module)
                .orElse(FRAMEWORK_BOT.getModuleManager().getModule(JsonModule.MODULE_NAME)
                .orElse(null));
    }

    @Override
    public void execute(Chat chat, User from, CommandContext context, Integer messageId, Message replyingTo, Instant instant) throws TelegramException {
        Random random = new Random(instant.toEpochMilli());
        functionalities.get(random.nextInt(functionalities.size())).execute(getBot(), chat, from, context, messageId, replyingTo, instant);
    }
}