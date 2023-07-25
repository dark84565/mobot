package com.ericchang.mobot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatGptError implements MoBotError {
  CHATGPT_NULL_RESPONSE_EXCEPTION("The response of chatgpt is null.");

  private final String message;
}
