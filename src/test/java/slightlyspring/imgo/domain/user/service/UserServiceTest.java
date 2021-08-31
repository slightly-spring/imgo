package slightlyspring.imgo.domain.user.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.repository.UserRepository;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void 유저_프로필_출력() {
        User user = new User();
        String nickname = "test01";
        user.setNickname(nickname);

        Long savedId = userRepository.save(user);
        UserProfile userProfile = userService.getUserProfile(savedId);

        assertThat(userProfile.getNickname(), is(equalTo(nickname)));
    }
}