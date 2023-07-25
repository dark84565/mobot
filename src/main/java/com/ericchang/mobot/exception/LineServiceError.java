package com.ericchang.mobot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LineServiceError implements MoBotError {
  LINE_SIGNATURE_INVALID("The Line signature is invalid");

  private final String message;
}
