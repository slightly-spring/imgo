package slightlyspring.imgo.domain.user.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import slightlyspring.imgo.domain.user.domain.Role;
import slightlyspring.imgo.domain.user.domain.UserAccount;

/**
 * class OAuthAttributes
 * 인증을 위한 OAuthUser 정보를 옮기는 dto 클래스
 * ( nameAttributeKey, name, email, picture )
 * ( attributes ) - 실제 사용시, attributes 만 가져오고, 여기에 있는 name, email, picture 정보를 불러와서 저장 하는 것
 */
@Getter
public class OAuthAttributes {
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
      String name, String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  /**
   * of
   * 사용자 정보가 Map이기 때문에, 값을 하나하나 변환해줘야함
   * 변환에 ofGoogle() 메서드 사용
   * @param registrationId
   * @param userNameAttributeName
   * @param attributes
   * @return
   */
  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {
    return ofGoogle(userNameAttributeName, attributes);
  }

  /**
   * ofGoogle
   * attributes 값을 하나하나 맞는 값으로 변환해줌
   * @param userNameAttributeName
   * @param attributes
   * @return
   */
  private static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  /**
   * toEntity
   * UserAccount 엔터티를 생성함.
   * 다만, 처음 가입할 때만 생성해야 함
   * @return
   */
  public UserAccount toEntity() {
    return UserAccount.builder()
        .name(name)
        .email(email)
        .picture(picture)
        .role(Role.USER)
        .build();
  }
}
