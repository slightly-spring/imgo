package slightlyspring.imgo.domain.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.user.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUser() throws Exception {
        User user = new User();
        user.setNickname("userA");

        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId()).get();

        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(findUser).isEqualTo(user);
        System.out.println("findUser == user" + (findUser == user));
    }
}