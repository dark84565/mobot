package com.ericchang.mobot.handler;

import com.ericchang.mobot.dto.FinancialNewsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class FinancialNewsResponseHandler
    implements HttpClientResponseHandler<FinancialNewsResponse> {
  @Override
  public FinancialNewsResponse handleResponse(ClassicHttpResponse classicHttpResponse)
      throws HttpException, IOException {
    String response = EntityUtils.toString(classicHttpResponse.getEntity(), "UTF-8");
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(response, FinancialNewsResponse.class);
  }
}
