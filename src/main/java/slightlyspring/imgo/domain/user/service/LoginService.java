package slightlyspring.imgo.domain.user.service;

import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

//@NoArgsConstructor
@Service
public class LoginService {

  public String getUserInfoEndpointUri(OAuth2AuthorizedClient client) {
    return client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
  }

  public Map httpRequestToUserInformationEndpoint(OAuth2AuthorizedClient client, String userInfoEndpointUri) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
    HttpEntity entity = new HttpEntity("", headers);

    System.out.println("Test: " + entity);


    ResponseEntity<Map> response = restTemplate
        .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);

    System.out.println("Response: " + response);

    return response.getBody();
  }
}
