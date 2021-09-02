package slightlyspring.imgo.domain.user.service.social;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 왜 안 @Service
public class GoogleOauth implements SocialOauth{
  // application.yml에 포함된 정보
  @Value("${sns.google.url}")
  private String GOOGLE_SNS_BASE_URL;
  @Value("${sns.google.client.id}")
  private String GOOGLE_SNS_CLIENT_ID;
  @Value("${sns.google.callback.url}")
  private String GOOGLE_SNS_CALLBACK_URL;
  @Value("${sns.google.client.secret}")
  private String GOOGLE_SNS_CLIENT_SECRET;

  @Override
  public String getOauthRedirectURL() {
    /**
     * Google login API Request
     * scope = profile
     * response_type = code
     * client_id = 발급받은 client ID
     * redirect_uri = 등록한 callback url
     */
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
}
