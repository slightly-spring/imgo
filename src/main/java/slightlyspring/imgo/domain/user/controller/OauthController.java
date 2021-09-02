package slightlyspring.imgo.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slightlyspring.imgo.domain.user.helper.AuthType;
import slightlyspring.imgo.domain.user.service.OauthService;

@RestController
@CrossOrigin //
@RequiredArgsConstructor
@RequestMapping(value = "/auth") //
@Slf4j //
public class OauthController {
  private final OauthService oauthService;
  /**
   * 사용자로부터 SNS로그인 요청을 Auth Type을 받아 처리함
   * @Param authType(GOOGLE, FACEBOOK, NAVER, KAKAO)
   */
  @GetMapping(value = "/{authType}")
  public void authType(
      @PathVariable(name = "authType") AuthType authType) {
    log.info(">>사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", authType);
    oauthService.request(authType);
  }

  /**
   * Social Login API Server 요청에 의한 callback 을 처리
   * @param authType (GOOGLE, FACEBOOK, NAVER, KAKAO)
   * @param code API Server 로부터 넘어오는 code
   * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
   */
  @GetMapping(value = "/{authType}/callback")
  public String callback(
      @PathVariable(name = "authType") AuthType authType,
      @RequestParam(name = "code") String code) {
    log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
    return oauthService.requestAccessToken(authType, code);
  }
}
