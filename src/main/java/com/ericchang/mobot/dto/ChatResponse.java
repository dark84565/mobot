package com.ericchang.mobot.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
  private List<Choice> choices;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Choice {
    private int index;
    private ChatMessage message;
  }
}
