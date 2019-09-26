package com.cadiducho.zincite.modules.json;

import com.cadiducho.telegrambotapi.Chat;
import com.cadiducho.telegrambotapi.Message;
import com.cadiducho.telegrambotapi.TelegramBot;
import com.cadiducho.telegrambotapi.User;
import com.cadiducho.telegrambotapi.exception.TelegramException;
import com.cadiducho.zincite.api.command.CommandContext;

import java.time.Instant;

/**
 * Funcionalidad (o una de las funcionalidades) de un comando creado por Json
 */
public interface CommandFunctionality {

    /**
     * Nombre de la funcionalidad por si se desea reutilizar
     * @return El nombre de la funcionalidad
     */
    default String getName() {
        return "";
    }

    void execute(TelegramBot bot, Chat chat, User from, CommandContext context, Integer messageId, Message replyingTo, Instant instant) throws TelegramException;

    default Integer replyTheCommandTo(ReplyPattern pattern, Integer originalMessageId, Message replyTo) {
        Integer replyTheCommandTo = null;
        switch (pattern) {
            case TO_ANSWERED:
                if (replyTo != null) replyTheCommandTo = replyTo.getMessageId();
                break;
            case TO_ORIGINAL:
                replyTheCommandTo = originalMessageId;
                break;
        }
        return replyTheCommandTo;
    }

}