package slightlyspring.imgo.global.config.auth;

import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.repository.UserAccountRepository;
import slightlyspring.imgo.global.config.auth.dto.OAuthAttributes;
import slightlyspring.imgo.global.config.auth.dto.SessionUser;

/**
 * OAuth2UserService<R, U>
 *   사용자의 요청(R)을 받아 사용자정보(U)를 반환하는 loadUser() 구현 필요
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  // UserAccountRepository 는 인터페이스 라서 findByEmail() 이 구현이 안돼있는데, 어케 씀?
  // -> JpaRepository 임
  private final UserAccountRepository userAccountRepository; //interface
  // HttpSession - session 정보를 관리/저장
  // 따로 정리하자
  private final HttpSession httpSession;

  /**
   * loadUser: 사용자 정보를 불러와 저장/업데이트 하고, 사용자 인증정보를 리턴해줌
   * @param userRequest
   * @return OAuth2User 타입으로 사용자 인정정보 반환
   * @throws OAuth2AuthenticationException
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    // OAuth2UserService 의 인스턴스로 delegate[대리자]를 만듦
    // 이때, 기본적으로 구현되어 있는 'DefaultOAuth2UserService' 를 사용
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    // delegate 가 loadUser 를 사용해서 사용자 정보를 불러옴
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    // registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드 (구글, 네이버 ...) - 지금은 필요 x
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    // userNameAttributeName : OAuth2 로그인 진행시 key 가 되는 필드 값 (primary key)
    // 구글의 경우 기본적으로 코드를 지원해요 (sub)
    // userRequest 에 담긴 ClientRegistration 정보에서 Provider 정보를 꺼내, 사용자 정보 EndPoint 를 불러와서 UserNameAttributeName 꺼내오기
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();

    // OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스
    // (attributes, nameAttributeKey, name, email, picture)
    OAuthAttributes attributes = OAuthAttributes
        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    UserAccount userAccount = saveOrUpdate(attributes);

    // HttpSession 인스턴스에 setAttribute 저장
    // key-value (user - new SessionUser(userAccount))
    httpSession.setAttribute("user", new SessionUser(userAccount));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(userAccount.getRoleKey()))
        , attributes.getAttributes()
        , attributes.getNameAttributeKey()
    );
  }

  /**
   * saveOrUpdate: OAuthAttribute 를 받아와서 원래 있던거랑 비교해서 새로저장/업데이트
   * @param attributes 사용자 정보
   * @return 저장 완료된 userAccount 반환
   */
  private UserAccount saveOrUpdate(OAuthAttributes attributes) {
    UserAccount userAccount = userAccountRepository.findByEmail(attributes.getEmail())
        .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
        .orElse(attributes.toEntity());
    return userAccountRepository.save(userAccount);
  }
}
