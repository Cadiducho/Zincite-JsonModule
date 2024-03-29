package com.cadiducho.zincite.modules.json;

import com.cadiducho.telegrambotapi.Chat;
import com.cadiducho.telegrambotapi.Message;
import com.cadiducho.telegrambotapi.TelegramBot;
import com.cadiducho.telegrambotapi.User;
import com.cadiducho.telegrambotapi.exception.TelegramException;
import com.cadiducho.zincite.api.command.CommandContext;
import com.squareup.moshi.Json;
import com.vdurmont.emoji.EmojiParser;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@Builder
@EqualsAndHashCode
public class TextFunctionality implements CommandFunctionality {

    /**
     * Name of the functionality
     */
    @Getter private final String name;

    /**
     * El patrón de respuesta que tomará la funcionalidad. Ver {@link ReplyPattern}
     */
    @Json(name = "reply_to") @Builder.Default private final ReplyPattern replyPattern = ReplyPattern.TO_NONE;

    /**
     * Texto que escribirá la funcionalidad
     */
    private final String text;

    public void execute(TelegramBot bot, Chat chat, User from, CommandContext context, Integer messageId, Message replyingTo, Instant instant) throws TelegramException {
        String reply = EmojiParser.parseToUnicode(text);

        bot.sendMessage(chat.getId(), reply, null, null, null, null, replyTheCommandTo(replyPattern, messageId, replyingTo), null);
    }
}
