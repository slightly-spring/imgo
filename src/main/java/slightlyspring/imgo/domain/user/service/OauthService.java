package slightlyspring.imgo.domain.user.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.user.helper.AuthType;
import slightlyspring.imgo.domain.user.service.social.GoogleOauth;

@Service
@RequiredArgsConstructor // 뭐더라
public class OauthService {

  // 여러 소셜 로그인으로 확장 시 FacebookOauth, KakaoOauth 등의 클래스를 추가하여 구현
  private final GoogleOauth googleOauth;

  private final HttpServletResponse response;

  /**
   * Social login API 서버에 리퀘스트를 보내는 주체
   * @param authType Social Service의 종류 (google, facebook, kakao ...)
   */
  public void request(AuthType authType) {
    String redirectURL;
    // facebook, kakao 등 추가 가능
    switch (authType) {
      case GOOGLE: {
        redirectURL = googleOauth.getOauthRedirectURL();
      } break;
      default: {
        throw new IllegalArgumentException("등록되지 않은 소셜 로그인 형식입니다");
      }
    }

    try {
      response.sendRedirect(redirectURL);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 받은 code로 AccessToken을 받아오는 request
   * @param authType
   * @param code
   * @return accessToken을 String형태로 반환
   */
  public String requestAccessToken(AuthType authType, String code) {
    switch (authType) {
      case GOOGLE: {
        return googleOauth.requestAccessToken(code);
      }
      default: {
        throw new IllegalArgumentException("등록되지 않은 소셜 로그인 형식입니다");
      }
    }
  }
}
