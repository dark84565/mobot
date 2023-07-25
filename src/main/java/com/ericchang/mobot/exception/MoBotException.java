package com.ericchang.mobot.exception;

public class MoBotException extends RuntimeException {
  private final MoBotError error;

  public MoBotException(String errorMessage, MoBotError error) {
    super(errorMessage);
    this.error = error;
  }

  public MoBotException(MoBotError error) {
    this.error = error;
  }

  public MoBotException(MoBotError error, Exception e) {
    super(e);
    this.error = error;
  }
}
