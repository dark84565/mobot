package com.ericchang.mobot.dto;

import static com.ericchang.mobot.constant.OpenAiConstant.OPENAI_MAX_TOKEN;
import static com.ericchang.mobot.constant.OpenAiConstant.OPENAI_MODEL;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
  private String model = OPENAI_MODEL;
  private List<ChatMessage> messages;
  private int n = 1;
  private double temperature;

  @JsonProperty("max_tokens")
  private Integer maxTokens = OPENAI_MAX_TOKEN;

  public ChatRequest(String message) {
    this.messages = List.of(ChatMessage.builder().role("user").content(message).build());
  }
}
