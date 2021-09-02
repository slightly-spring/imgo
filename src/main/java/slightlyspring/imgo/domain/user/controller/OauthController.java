package slightlyspring.imgo.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
