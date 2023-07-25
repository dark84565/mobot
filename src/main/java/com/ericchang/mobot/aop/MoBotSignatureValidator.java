package com.ericchang.mobot.aop;

import static com.ericchang.mobot.exception.LineServiceError.LINE_SIGNATURE_INVALID;

import com.ericchang.mobot.cachedreqeust.CachedBodyHttpServletRequest;
import com.ericchang.mobot.exception.MoBotException;
import com.google.common.io.ByteSource;
import com.linecorp.bot.spring.boot.LineBotProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoBotSignatureValidator extends OncePerRequestFilter {
  private final LineBotProperties lineBotProperties;

  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String channelSecret = lineBotProperties.getChannelSecret(); // Channel secret string
      SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(key);
      CachedBodyHttpServletRequest cacheRequest = new CachedBodyHttpServletRequest(request);
      String requestBody =
          ByteSource.wrap(cacheRequest.getInputStream().readAllBytes())
              .asCharSource(StandardCharsets.UTF_8)
              .read();
      byte[] source = requestBody.getBytes(StandardCharsets.UTF_8);
      String signature = Base64.encodeBase64String(mac.doFinal(source));

      String lineSignature = request.getHeader("x-line-signature");

      if (signature.equals(lineSignature)) {
        log.info("Validation success.");
        filterChain.doFilter(cacheRequest, response);
      }
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new MoBotException(LINE_SIGNATURE_INVALID, e);
    }
  }
}
