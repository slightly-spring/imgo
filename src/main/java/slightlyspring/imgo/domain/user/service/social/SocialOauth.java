package slightlyspring.imgo.domain.user.service.social;

public interface SocialOauth {
  /**
   * 각 Social login 페이지로 Redirect 처리할 URL Build
   * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
   */
  public String getOauthRedirectURL();
}
