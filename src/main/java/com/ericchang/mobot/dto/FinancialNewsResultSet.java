package com.ericchang.mobot.dto;

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
public class FinancialNewsResultSet {
  @JsonProperty("ExpireTime")
  private String expireTime;

  @JsonProperty("Result")
  private List<FinancialNews> financialNewsResult;
}
