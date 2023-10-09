package com.ericchang.mobot.service.impl;

import static com.ericchang.mobot.exception.ExternalServiceError.CHATGPT_NULL_RESPONSE_EXCEPTION;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

import com.ericchang.mobot.domain.FinancialNewsData;
import com.ericchang.mobot.dto.ChatRequest;
import com.ericchang.mobot.dto.ChatResponse;
import com.ericchang.mobot.exception.MoBotException;
import com.ericchang.mobot.mapper.FinancialNewsDtoMapper;
import com.ericchang.mobot.service.MoBotService;
import com.ericchang.mobot.webclient.MoBotWebClient;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoBotServiceImpl implements MoBotService {
  private final MoBotWebClient moBotWebClient;
  private final FinancialNewsDtoMapper financialNewsDtoMapper;

  @Override
  public TextMessage handleChatGptRequest(MessageEvent<TextMessageContent> event) {
    log.info("Post request to OpenAI from id: [{}]", event.getMessage().getId());
    ChatResponse response =
        moBotWebClient.postRequestToChatGpt(new ChatRequest(event.getMessage().getText()));

    if (isNull(response) || isEmpty(response.getChoices())) {
      throw new MoBotException(CHATGPT_NULL_RESPONSE_EXCEPTION);
    }

    return new TextMessage(response.getChoices().get(0).getMessage().getContent());
  }

  @Override
  public TextMessage handleWebCrawlerRequest() {
    List<FinancialNewsData> financialNewsList =
        financialNewsDtoMapper.fromResponse(moBotWebClient.getFinancialNews());

    List<FinancialNewsData> responseNews =
        financialNewsList.get(0).getDate().equals(LocalDate.now())
            ? financialNewsList.stream()
                .filter(news -> news.getDate().equals(LocalDate.now()))
                .toList()
            : financialNewsList.stream().limit(20).toList();

    return new TextMessage(generateFinancialNewsMessage(responseNews));
  }

  private String generateFinancialNewsMessage(List<FinancialNewsData> financialNewsList) {
    StringBuilder textMessage = new StringBuilder();
    for (int i = 0; i < financialNewsList.size(); i++) {
      textMessage.append("•");
      textMessage.append(financialNewsList.get(i).getTitle());

      if (i != financialNewsList.size() - 1) {
        textMessage.append("\r\n\r\n");
      }
    }

    log.info("Financial news message size: [{}]", textMessage.length());

    return textMessage.toString();
  }
}
