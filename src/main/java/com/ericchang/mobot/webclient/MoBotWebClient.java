package com.ericchang.mobot.webclient;

import static com.ericchang.mobot.constant.OpenAiConstant.OPENAI_URI;
import static com.ericchang.mobot.exception.ExternalServiceError.GET_FINANCIAL_NEWS_ERROR;

import com.ericchang.mobot.dto.ChatRequest;
import com.ericchang.mobot.dto.ChatResponse;
import com.ericchang.mobot.dto.FinancialNewsResponse;
import com.ericchang.mobot.exception.MoBotException;
import com.ericchang.mobot.handler.FinancialNewsResponseHandler;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MoBotWebClient {
  private final RestTemplate openAiRestTemplate;
  private final HttpClient httpClient;

  public ChatResponse postRequestToChatGpt(ChatRequest chatRequest) {
    return openAiRestTemplate.postForObject(OPENAI_URI, chatRequest, ChatResponse.class);
  }

  public FinancialNewsResponse getFinancialNews() {
    try {
      HttpGet request =
          new HttpGet(
              "https://fund.megabank.com.tw/ETFData/djjson/ETNEWSjson.djjson?a=1&P1=mega&P2=&P3=true&P4=false&P5=false");
      return httpClient.execute(request, new FinancialNewsResponseHandler());
    } catch (IOException e) {
      throw new MoBotException(GET_FINANCIAL_NEWS_ERROR);
    }
  }
}
