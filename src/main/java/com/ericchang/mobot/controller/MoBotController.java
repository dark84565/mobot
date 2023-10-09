package com.ericchang.mobot.controller;

import com.ericchang.mobot.service.MoBotService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LineMessageHandler
@RequiredArgsConstructor
public class MoBotController {
  private final MoBotService moBotService;

  @EventMapping()
  public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    return switch (event.getMessage().getText()) {
      case "Financial News" -> moBotService.handleWebCrawlerRequest(event);
      case "Weather", "StockMarket" -> new TextMessage("Coming soon.");
      default -> moBotService.handleChatGptRequest(event);
    };
  }
}
