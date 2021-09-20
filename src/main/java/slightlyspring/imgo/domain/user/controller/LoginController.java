package slightlyspring.imgo.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {


  /**
   * login
   * HttpSession 에 저장되어있는 'user' 세션을 불러와서
   * user 가 존재하면, model 에 이름 추가 / 없으면 패스
   * @param model
   * @return "login" template 불러오기
   */
  @GetMapping("/oauth_login")
  public String getLoginPage(Model model) {
    return "auth/oauth_login";
  }

//
//  @GetMapping("/loginSuccess")
//  public String getLoginInfo(Model model, OAuth2AuthenticationToken authorizedToken) {
//    // 현재 userToken 에 해당하는 사용자 불러오기
//    System.out.println("test: " + authorizedToken);
//    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//        authorizedToken.getAuthorizedClientRegistrationId(),
//        authorizedToken.getName()
//    );
//    // user info. endpoint 에 request 날려서 정보 받아오기
//      OAuth2User userAttributes = loginService.httpRequestToUserInformationEndpoint(client);
//      System.out.println(userAttributes);
//      model.addAttribute("name", userAttributes.getAttributes().get("name"));
//    return "redirect:/"; //항상 메인페이지로 넘어가게 하기
//  }


}
