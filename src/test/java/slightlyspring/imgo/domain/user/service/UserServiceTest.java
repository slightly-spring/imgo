package slightlyspring.imgo.domain.user.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;

    @BeforeEach
    void setUp() {
        String nickname = "test01";
        String profileImg = "sample_image_url";
        String profileDescription = "hi it is description";

        user = User.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .profileDescription(profileDescription)
                .build();

        userRepository.save(user);
    }

    @Test
    void 유저_프로필_출력() {
        UserProfile userProfile = userService.getUserProfile(user.getId());

        assertThat(userProfile.getNickname(), is(equalTo(user.getNickname())));
        assertThat(userProfile.getProfileImg(), is(equalTo(user.getProfileImg())));
        assertThat(userProfile.getProfileDescription(), is(equalTo(user.getProfileDescription())));
    }



}