package slightlyspring.imgo.domain.user.dto;

import java.io.Serializable;
import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.domain.AuthType;

@Getter
public class SessionUser implements Serializable {
  private String name;
  private String authId;
  private AuthType authType;


  public SessionUser(OAuthAttributes attributes) {
    this.name = attributes.getNickname();
    this.authId = attributes.getAuthId();
    this.authType = attributes.getAuthType();
  }
}
