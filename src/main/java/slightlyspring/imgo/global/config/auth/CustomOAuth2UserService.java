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

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final UserAccountRepository userAccountRepository; //interface
  private final HttpSession httpSession;

  /**
   * loadUser:
   * @param userRequest
   * @return
   * @throws OAuth2AuthenticationException
   */
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();

    OAuthAttributes attributes = OAuthAttributes
        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    UserAccount userAccount = saveOrUpdate(attributes);
    httpSession.setAttribute("user", new SessionUser(userAccount));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(userAccount.getRoleKey()))
        , attributes.getAttributes()
        , attributes.getNameAttributeKey()
    );
  }
  private UserAccount saveOrUpdate(OAuthAttributes attributes) {
    UserAccount userAccount = userAccountRepository.findByEmail(attributes.getEmail())
        .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
        .orElse(attributes.toEntity());
    return userAccountRepository.save(userAccount);
  }
}
