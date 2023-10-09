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
public class FinancialNewsResponse {
  @JsonProperty("ResultSet")
  FinancialNewsResultSet financialNewsResultSet;
}
