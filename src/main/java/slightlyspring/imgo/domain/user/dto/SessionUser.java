package slightlyspring.imgo.domain.user.dto;

import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.UserAccount;

@Getter
public class SessionUser {
  private String name;
  private String email;
  private String picture;


  public SessionUser(UserAccount userAccount) {
    this.name = userAccount.getName();
    this.email = userAccount.getEmail();
    this.picture = userAccount.getPicture();
  }
}
