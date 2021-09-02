package slightlyspring.imgo.domain.user.service.social;

public interface SocialOauth {
  /**
   * 각 Social login 페이지로 Redirect 처리할 URL Build
   * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
   */
  public String getOauthRedirectURL();

  /**
   * API 서버로부터 받은 code를 활용해 사용자 인증정보 요청
   *
   * @param code API 서버에서 받아온 code
   * @return API 서버로부터 응답받은 Json형태의 결과를 string으로 반환
   */
  public String requestAccessToken(String code);
}
