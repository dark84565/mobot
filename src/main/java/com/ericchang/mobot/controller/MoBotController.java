package com.ericchang.mobot.controller;

import static com.ericchang.mobot.constant.ChatGptConstant.OPENAI_URI;
import static com.ericchang.mobot.exception.ChatGptError.CHATGPT_NULL_RESPONSE_EXCEPTION;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ericchang.mobot.dto.ChatRequest;
import com.ericchang.mobot.dto.ChatResponse;
import com.ericchang.mobot.exception.MoBotException;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@LineMessageHandler
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class MoBotController {
  private final RestTemplate restTemplate;

  @EventMapping()
  public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    log.info("Successfully Get The Message!!!");
    return handleTextContent(event);
  }

  @EventMapping
  public void handleDefaultMessageEvent(Event event) {
    log.info("event: {}", event);
  }

  private TextMessage handleTextContent(MessageEvent<TextMessageContent> event) {
    ChatResponse response =
        restTemplate.postForObject(
            OPENAI_URI, new ChatRequest(event.getMessage().getText()), ChatResponse.class);

    if (isNull(response) || isEmpty(response.getChoices())) {
      throw new MoBotException(CHATGPT_NULL_RESPONSE_EXCEPTION);
    }

    return new TextMessage(response.getChoices().get(0).getMessage().getContent());
  }
}
