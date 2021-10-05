package slightlyspring.imgo.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {


  /**
   * login
   * HttpSession 에 저장되어있는 'user' 세션을 불러와서
   * user 가 존재하면, model 에 이름 추가 / 없으면 패스
   * @param model
   * @return "login" template 불러오기
   */
  @GetMapping("/oAuthLogin")
  public String oAuthLogin(Model model) {
    return "/auth/oAuthLogin";
  }


}
