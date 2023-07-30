package com.ericchang.mobot.controller;

import static com.ericchang.mobot.constant.OpenAIConstant.OPENAI_URI;
import static com.ericchang.mobot.exception.ChatGptError.CHATGPT_NULL_RESPONSE_EXCEPTION;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ericchang.mobot.dto.ChatRequest;
import com.ericchang.mobot.dto.ChatResponse;
import com.ericchang.mobot.exception.MoBotException;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@LineMessageHandler
@RequiredArgsConstructor
public class MoBotController {
  private final RestTemplate restTemplate;

  @EventMapping()
  public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    return handleTextContent(event);
  }

  private TextMessage handleTextContent(MessageEvent<TextMessageContent> event) {
    log.info("Post request to OpenAI from id: [{}]", event.getMessage().getId());
    ChatResponse response =
        restTemplate.postForObject(
            OPENAI_URI, new ChatRequest(event.getMessage().getText()), ChatResponse.class);

    if (isNull(response) || isEmpty(response.getChoices())) {
      throw new MoBotException(CHATGPT_NULL_RESPONSE_EXCEPTION);
    }

    return new TextMessage(response.getChoices().get(0).getMessage().getContent());
  }
}
