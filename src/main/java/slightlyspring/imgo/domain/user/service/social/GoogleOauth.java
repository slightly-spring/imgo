package slightlyspring.imgo.domain.user.service.social;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Google login API Request
 * scope = profile
 * response_type = code
 * client_id = 발급받은 client ID
 * redirect_uri = 등록한 callback url
 */
@Component // 왜 안 @Service
public class GoogleOauth implements SocialOauth{
  // application.yml에 포함된 정보
  @Value("${sns.google.url.base}")
  private String GOOGLE_SNS_BASE_URL;
  @Value("${sns.google.client.id}")
  private String GOOGLE_SNS_CLIENT_ID;
  @Value("${sns.google.callback.url}")
  private String GOOGLE_SNS_CALLBACK_URL;
  @Value("${sns.google.client.secret}")
  private String GOOGLE_SNS_CLIENT_SECRET;
  @Value("${sns.google.url.token}")
  private String GOOGLE_SNS_TOKEN_BASH_URL;

  /**
   * Google Redirect URL 요청
   * @return Redirect URL을 String으로 반환
   */
  @Override
  public String getOauthRedirectURL() {
    Map<String, Object> params = new HashMap<>();
    params.put("scope", "profile");
    params.put("response_type", "code");
    params.put("client_id", GOOGLE_SNS_CLIENT_ID);
    params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

    String parameterString = params.entrySet().stream()
        .map(x -> x.getKey() + "=" + x.getValue())
        .collect(Collectors.joining("&"));
    return GOOGLE_SNS_BASE_URL + "?" + parameterString;
  }


  @Override
  public String requestAccessToken(String code) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> params = new HashMap<>();
    params.put("code", code);
    params.put("client_id", GOOGLE_SNS_CLIENT_ID);
    params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
    params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
    params.put("grant_type", "authorization_code");

    ResponseEntity<String> responseEntity =
        restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASH_URL, params, String.class);

    if (responseEntity.getStatusCode() == HttpStatus.OK) {
      return responseEntity.getBody();
    }
    return "구글 로그인 요청 처리 실패";
  }
}
