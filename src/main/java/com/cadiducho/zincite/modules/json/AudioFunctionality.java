package com.cadiducho.zincite.modules.json;

import com.cadiducho.telegrambotapi.Chat;
import com.cadiducho.telegrambotapi.Message;
import com.cadiducho.telegrambotapi.TelegramBot;
import com.cadiducho.telegrambotapi.User;
import com.cadiducho.telegrambotapi.exception.TelegramException;
import com.cadiducho.zincite.api.command.CommandContext;
import com.squareup.moshi.Json;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@ToString
@EqualsAndHashCode
public class AudioFunctionality implements CommandFunctionality {

    /**
     * Name of the functionality
     */
    @Getter private final String name;

    @Json(name = "reply_to") @Builder.Default private final ReplyPattern replyPattern = ReplyPattern.TO_NONE;
    @Json(name = "audio_id") private final String audioId;
    @Json(name = "caption") private final String caption;
    @Json(name = "performer") private final String performer;
    @Json(name = "title") private final String title;

    public void execute(TelegramBot bot, Chat chat, User from, CommandContext context, Integer messageId, Message replyingTo, Instant instant) throws TelegramException {
        bot.sendAudio(chat.getId(), audioId, caption, null, performer, title, null, replyTheCommandTo(replyPattern, messageId, replyingTo), null);
    }
}
