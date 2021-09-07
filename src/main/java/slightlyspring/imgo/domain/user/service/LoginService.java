package slightlyspring.imgo.domain.user.service;

import java.util.Collections;
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
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.dto.SessionUser;
import slightlyspring.imgo.domain.user.repository.UserAccountRepository;
import slightlyspring.imgo.domain.user.dto.OAuthAttributes;

//@NoArgsConstructor
@RequiredArgsConstructor
@Service
public class LoginService {

  private final UserAccountRepository userAccountRepository;
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

//    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//
//    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
//    HttpEntity entity = new HttpEntity("", headers);
//
//    System.out.println("Test: " + entity);
//
//
//    ResponseEntity<Map> response = restTemplate
//        .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//    System.out.println("Response: " + response);

    UserAccount userAccount = saveOrUpdate(attributes);
    httpSession.setAttribute("user", new SessionUser(userAccount));
//    return response.getBody();

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(userAccount.getRoleKey()))
        , attributes.getAttributes()
        , attributes.getNameAttributeKey()
    );
  }

//  public OAuth2User toOAuth2User(Map rBody) {
//    return
//  }

  private UserAccount saveOrUpdate(OAuthAttributes attributes) {
    UserAccount userAccount = userAccountRepository.findByEmail(attributes.getEmail())
        .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
        .orElse(attributes.toEntity());
    return userAccountRepository.save(userAccount);
  }
}
