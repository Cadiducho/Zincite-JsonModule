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
public class VenueFunctionality implements CommandFunctionality {

    /**
     * Name of the functionality
     */
    @Getter private final String name;

    @Json(name = "reply_to") @Builder.Default private final ReplyPattern replyPattern = ReplyPattern.TO_NONE;
    @Json(name = "latitude") private final Float latitude;
    @Json(name = "longitude") private final Float longitude;
    @Json(name = "title") private final String title;

    public void execute(TelegramBot bot, Chat chat, User from, CommandContext context, Integer messageId, Message replyingTo, Instant instant) throws TelegramException {
        bot.sendVenue(chat.getId(), latitude, longitude, title, null, null, null, null, null, replyTheCommandTo(replyPattern, messageId, replyingTo), null);
    }
}

