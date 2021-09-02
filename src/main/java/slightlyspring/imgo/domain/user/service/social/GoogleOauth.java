package slightlyspring.imgo.domain.user.service.social;

import org.springframework.stereotype.Component;

@Component // 왜 안 @Service
public class GoogleOauth implements SocialOauth{
  @Override
  public String getOauthRedirectURL() {
    return "";
  }
}
