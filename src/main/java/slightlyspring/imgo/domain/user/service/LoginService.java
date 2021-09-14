package slightlyspring.imgo.domain.user.service;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.user.domain.Role;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.dto.SessionUser;
import slightlyspring.imgo.domain.user.repository.UserAccountRepository;
import slightlyspring.imgo.domain.user.dto.OAuthAttributes;
import slightlyspring.imgo.domain.user.repository.UserRepository;

//@NoArgsConstructor
@RequiredArgsConstructor
@Service
/**
 * 수정 사항
 *
 * 사용자 구분에 따라 ADMIN, USER, GUEST 를 나눠야 하나, 지금은 USER 만 사용한다 - currentUserRole()
 */
public class LoginService {

  private final UserAccountRepository userAccountRepository;
  private final UserRepository userRepository;
  private final HttpSession httpSession;

  public String getUserInfoEndpointUri(OAuth2AuthorizedClient client) {
    return client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
  }

  public OAuth2User httpRequestToUserInformationEndpoint(OAuth2AuthorizedClient client, String userInfoEndpointUri) {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    ClientRegistration clientRegistration = client.getClientRegistration();
    OAuth2AccessToken accessToken = client.getAccessToken();
    OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    String currentAuthId = attributes.getAuthId();
    if (isDuplicatedUserAccount(currentAuthId)) {
      List<UserAccount> findUserAccounts = userAccountRepository.findByAuthId(currentAuthId);
      if (findUserAccounts.size() > 1) {
        throw new IllegalStateException("같은 user 계정이 두 개 이상 등록되어있습니다");
      }
      UserAccount findUserAccount = findUserAccounts.get(0);
      User findUser = findUserAccount.getUser();
      findUser.updateNickname(attributes.getNickname());
      findUser.updateProfileImg(attributes.getPicture());
      findUserAccount.updateRole(currentUserRole(attributes));
    } else {
      User user = attributes.toUserEntity();
      UserAccount userAccount = attributes.toUserAccountEntityWith(user);
      userRepository.save(user);
      userAccountRepository.save(userAccount);
    }

    httpSession.setAttribute("user", new SessionUser(attributes));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(currentUserRole(attributes).getKey()))
        , attributes.getAttributes()
        , attributes.getNameAttributeKey()
    );
  }

  /*---helper 메서드---*/
  private boolean isDuplicatedUserAccount(String authId) {
    List<UserAccount> findAccount = userAccountRepository.findByAuthId(authId);
    return !findAccount.isEmpty();
  }

  private Role currentUserRole(OAuthAttributes attributes) {
    // 수정 필요
    return Role.USER;
  }
}
