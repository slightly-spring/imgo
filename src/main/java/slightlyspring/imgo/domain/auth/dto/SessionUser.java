package slightlyspring.imgo.domain.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import slightlyspring.imgo.domain.auth.domain.AuthType;
import slightlyspring.imgo.domain.auth.dto.OAuthAttributes;

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
