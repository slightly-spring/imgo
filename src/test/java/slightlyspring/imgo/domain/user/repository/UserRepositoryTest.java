package slightlyspring.imgo.domain.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.user.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void User가_저장된다() {
        //given
        User user = User.builder()
            .nickname("nameA")
            .profileImg("imageA")
            .build();

        //when
        userRepository.save(user);

        //then
        List<User> findUsers = userRepository.findByNickname("nameA");
        assertThat(findUsers.size()).isEqualTo(1);

        User findUser = userRepository.findByNickname("nameA").get(0);
        assertThat(findUser.getNickname()).isEqualTo("nameA");
        assertThat(findUser.getProfileImg()).isEqualTo("imageA");
        assertThat(findUser.getLastWriteAt()).isEqualTo(LocalDateTime.of(0, 0, 0, 0, 0, 0, 0));
    }

}