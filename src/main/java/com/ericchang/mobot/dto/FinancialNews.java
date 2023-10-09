package com.ericchang.mobot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialNews {
  @JsonProperty("V1")
  private String date;

  @JsonProperty("V2")
  private String title;

  @JsonProperty("V3")
  private String path;
}
