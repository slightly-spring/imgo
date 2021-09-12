package slightlyspring.imgo.domain.til.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TilServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void TIL_게시물_작성() {
        // given
        // when
        // then
    }


}