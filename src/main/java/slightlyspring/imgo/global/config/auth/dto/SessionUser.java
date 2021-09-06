package slightlyspring.imgo.global.config.auth.dto;

import java.io.Serializable;
import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.UserAccount;

/**
 * class SessionUser implements Serializable
 * 인증된 사용자의 세션정보를 옮기는 dto 클래스
 */
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
