package slightlyspring.imgo.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.user.domain.Role;
import slightlyspring.imgo.domain.user.domain.UserAccount;

@SpringBootTest
@Transactional
class UserAccountRepositoryTest {

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Test
  void TimeEntity_등록() {
    //given
//    LocalDateTime now = LocalDateTime.of(2100,2,15,0,0,0);
    LocalDateTime now = LocalDateTime.now();
    userAccountRepository.save(UserAccount.builder().name("userA").email("tmpEmail").picture("tmpPicture").role(
        Role.USER).build());

    //when
    List<UserAccount> postsLists = userAccountRepository.findAll();

    //then
    UserAccount ua = postsLists.get(0);

    System.out.println(">>>>>> createDate=" + ua.getCreatedDate() + ", modifiedDate=" + ua.getModifiedDate());

    assertThat(ua.getCreatedDate()).isAfterOrEqualTo(now);
    assertThat(ua.getModifiedDate()).isAfterOrEqualTo(now);
  }


}