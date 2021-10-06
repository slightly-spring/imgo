package slightlyspring.imgo.domain.auth.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.auth.domain.AuthType;
import slightlyspring.imgo.domain.auth.domain.Role;
import slightlyspring.imgo.domain.auth.domain.UserAccount;
import slightlyspring.imgo.domain.auth.repository.UserAccountRepository;

@SpringBootTest
@Transactional
class UserAccountRepositoryTest {

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Test
  void UserAccount_하나가_저장된다() {
    //given
    UserAccount userAccount = UserAccount.builder()
        .authType(AuthType.GOOGLE)
        .authId("testAuthId")
        .role(Role.USER)
        .build();
    //when
    userAccountRepository.save(userAccount);

    //then
    UserAccount findUserAccount = userAccountRepository.findByAuthId("testAuthId").get(0);
    assertThat(findUserAccount.getAuthId()).isEqualTo("testAuthId");
  }

  @Test
  void TimeEntity_등록() {
    //given
//    LocalDateTime now = LocalDateTime.of(2100,2,15,0,0,0);
    LocalDateTime now = LocalDateTime.now();
    userAccountRepository.save(UserAccount.builder().role(Role.USER).authId("test").authType(AuthType.GOOGLE).build());

    //when
    List<UserAccount> postsLists = userAccountRepository.findAll();

    //then
    UserAccount ua = postsLists.get(0);

    System.out.println(">>>>>> createDate=" + ua.getCreatedDate() + ", modifiedDate=" + ua.getModifiedDate());

    assertThat(ua.getCreatedDate()).isAfterOrEqualTo(now);
    assertThat(ua.getModifiedDate()).isAfterOrEqualTo(now);
  }


}