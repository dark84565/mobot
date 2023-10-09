package com.ericchang.mobot.service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

public interface MoBotService {
  TextMessage handleChatGptRequest(MessageEvent<TextMessageContent> event);

  TextMessage handleWebCrawlerRequest(MessageEvent<TextMessageContent> event);
}
