package slightlyspring.imgo.domain.user.controller;

import org.springframework.http.HttpHeaders;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import slightlyspring.imgo.domain.user.service.LoginService;
import slightlyspring.imgo.global.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class loginController {

//  private final HttpSession httpSession;
  private final OAuth2AuthorizedClientService authorizedClientService;
  private final LoginService loginService;

  /**
   * login
   * HttpSession 에 저장되어있는 'user' 세션을 불러와서
   * user 가 존재하면, model 에 이름 추가 / 없으면 패스
   * @param model
   * @return "login" template 불러오기
   */
  @GetMapping("/oauth_login")
  public String getLoginPage(Model model) {
    // HttpSession 에 저장되어있는 'user' 세션을 불러오기
//    SessionUser user = (SessionUser) httpSession.getAttribute("user");
//    if (user != null) {
//      //세션에 저장된 값이 있을때만, model 에 userName 으로 등록
//      //세션에 저장된 값이 없다면, 로그인 버튼만 보일 것
//      model.addAttribute("userName", user.getName());
//    }
    return "oauth_login";
  }

  @GetMapping("/loginSuccess")
  public String getLoginInfo(Model model, OAuth2AuthenticationToken authorizedToken) {
    // 현재 userToken 에 해당하는 사용자 불러오기
    System.out.println("test: " + authorizedToken);
    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
        authorizedToken.getAuthorizedClientRegistrationId(),
        authorizedToken.getName()
    );
    // user info. endpoint 에 request 날려서 정보 받아오기
    String userInfoEndpointUri = loginService.getUserInfoEndpointUri(client);
    System.out.println("userEndpoint: " + userInfoEndpointUri);
    if (StringUtils.hasLength(userInfoEndpointUri)) {
      Map userAttributes = loginService.httpRequestToUserInformationEndpoint(client, userInfoEndpointUri);
      model.addAttribute("name", userAttributes.get("name"));
    }
    return "loginSuccess";
  }


}
