package com.ericchang.mobot;

import com.ericchang.mobot.dto.FinancialNewsResponse;
import com.ericchang.mobot.mapper.FinancialNewsDtoMapper;
import com.ericchang.mobot.webclient.MoBotWebClient;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MobotApplicationTests {
  @Autowired MoBotWebClient moBotWebClient;
  @Autowired FinancialNewsDtoMapper mapper;

  @Test
  void contextLoads() throws IOException {
    FinancialNewsResponse f = moBotWebClient.getFinancialNews();
    System.out.println(mapper.fromResponse(f));
    System.out.println("Hello,\r\nWorld!");
  }
}
