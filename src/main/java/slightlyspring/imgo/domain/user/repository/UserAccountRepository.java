package slightlyspring.imgo.domain.user.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import slightlyspring.imgo.domain.user.domain.UserAccount;

/**
 * UserAccount extends JpaRepository<UserAccount, Long>
 *   미리 검색 메소드를 입력해놓으면 그냥 query 를 날려서 사용할 수 있다
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
  // select * from user_accounts where user_accounts.email==email
//  Optional<UserAccount> findByEmail(String email);
  List<UserAccount> findByAuthId(String authId);
}
