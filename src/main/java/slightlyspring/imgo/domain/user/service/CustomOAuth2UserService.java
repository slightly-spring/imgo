package slightlyspring.imgo.domain.user.service;

import java.util.Collections;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.user.domain.Role;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserAccount;
import slightlyspring.imgo.domain.user.dto.OAuthAttributes;
import slightlyspring.imgo.domain.user.dto.SessionUser;
import slightlyspring.imgo.domain.user.repository.UserAccountRepository;
import slightlyspring.imgo.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //1
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //2

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //3

        Long userId;
        String currentAuthId = attributes.getAuthId();
        if (isDuplicatedUserAccount(currentAuthId)) {
            List<UserAccount> findUserAccounts = userAccountRepository.findByAuthId(currentAuthId);
            if (findUserAccounts.size() > 1) {
                throw new IllegalStateException("같은 user 계정이 두 개 이상 등록되어있습니다");
            }
            UserAccount findUserAccount = findUserAccounts.get(0);
            User findUser = findUserAccount.getUser();

            findUser.updateNickname(attributes.getNickname())
                    .updateProfileImg(attributes.getPicture());
            findUserAccount.updateRole(currentUserRole(attributes));

            userId = userRepository.save(findUser).getId();
            userAccountRepository.save(findUserAccount);
        } else {
            User user = attributes.toUserEntity();
            UserAccount userAccount = attributes.toUserAccountEntityWith(user);
            userId = userRepository.save(user).getId();
            userAccountRepository.save(userAccount);
        }

//        httpSession.setAttribute("user", new SessionUser(attributes));
        httpSession.setAttribute("userId", userId);

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
