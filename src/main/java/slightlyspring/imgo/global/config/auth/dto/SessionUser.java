package slightlyspring.imgo.global.config.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.UserAccount;

@Getter
public class SessionUser implements Serializable {
  private String name;
  private String email;
  private String picture;


  public SessionUser(UserAccount userAccount) {
    this.name = userAccount.getName();
    this.email = userAccount.getEmail();
    this.picture = userAccount.getPicture();
  }
}
