package slightlyspring.imgo.domain.user.dto;

import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.helper.AuthType;

@Getter
public class SessionUser {
  private String name;
  private String authId;
  private AuthType authType;


  public SessionUser(UserAccount userAccount, User user) {
    this.name = user.getNickname();
    this.authId = userAccount.getAuthId();
    this.authType = userAccount.getAuthType();
  }
}
