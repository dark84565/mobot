package com.ericchang.mobot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExternalServiceError implements MoBotError {
  CHATGPT_NULL_RESPONSE_EXCEPTION("The response of chatgpt is null."),
  GET_FINANCIAL_NEWS_ERROR("Failed to get financial news.");

  private final String message;
}
