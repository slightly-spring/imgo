package slightlyspring.imgo.domain.rival;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import slightlyspring.imgo.domain.user.domain.User;

@ExtendWith(MockitoExtension.class)
class RivalServiceTest {

  @InjectMocks
  private RivalService rivalService;
  @Mock
  private RivalRepository rivalRepository;

  @Test
  @DisplayName("a 가 b 를 라이벌로 가지고 있는지 확인 - 라이벌일 때")
  void isRivalByUserId1() {
    //given
    int numOfRivals = 1;
    List<User> users = createUserEntities(numOfRivals*2);
    List<Rival> rivals = createRivalEntities(numOfRivals, users);

    //mocking
    given(rivalRepository.findByUserId(any())).willReturn(Arrays.asList(rivals.get(0)));

    //when
    boolean tmp = rivalService.isRivalByUserIdAndTargetId(users.get(0).getId(),
        users.get(0 + numOfRivals).getId());

    //then
    assertThat(tmp).isEqualTo(true);
  }

  @Test
  @DisplayName("a 가 b 를 라이벌로 가지고 있는지 확인 - 라이벌이 아닐 때")
  void isRivalByUserId2() {
    //given
    int numOfRivals = 1;
    List<User> users = createUserEntities(numOfRivals*2);
    // - 라이벌을 만들지 않음

    //mocking
    given(rivalRepository.findByUserId(any())).willReturn(Arrays.asList());

    //when
    boolean tmp = rivalService.isRivalByUserIdAndTargetId(users.get(0).getId(),
        users.get(0 + numOfRivals).getId());

    //then
    assertThat(tmp).isEqualTo(false);
  }


//  @Test
//  @DisplayName("라이벌 중복 저장")
//  //예외 발생 시키고 다시 테스트 짜기
//  void save1() {
//    //given
//    int numOfRivals = 1;
//    List<User> users = createUserEntities(numOfRivals*2);
//    List<Rival> rivals = createRivalEntities(numOfRivals, users);
//
//    //mocking
//    given(rivalRepository.findByUserId(any()))
//        .willReturn(Arrays.asList(rivals.get(0)));
//
//    //when
//    Long save = rivalService.save(rivals.get(0));
//
//    //then
//    assertThat(save).isEqualTo(rivals.get(0).getId());
//  }

  @Test
  @DisplayName("라이벌 초과 저장")
  void save2() {
    //given
    int numOfRivals = 6;
    List<User> users = createUserEntities(numOfRivals*2);
    List<Rival> rivals = createRivalEntities(numOfRivals, users);

    //mocking
    given(rivalRepository.findByUserId(any()))
        .willReturn(rivals.subList(0,5));

    //when
    Long save = rivalService.save(rivals.get(5));

    //then
    assertThat(save).isEqualTo(0l);
  }

  /**
   * users 의 앞 절반 = user
   * 뒤 절반 = target
   * @param users users.size == numOfEntity*2
   */
  public List<Rival> createRivalEntities(int numOfEntity, List<User> users) {
    List<Rival> rivals = new ArrayList<>();
    for (int i=0; i<numOfEntity; i++){
      rivals.add(
          Rival.builder().id(Long.valueOf(i)).user(users.get(i)).target(users.get(i + numOfEntity)).build());
    }
    return rivals;
  }

  public List<User> createUserEntities(int numOfEntity) {
    List<User> users = new ArrayList<>();
    for (int i=0; i<numOfEntity; i++){
      users.add(User.builder().id(Long.valueOf(i)).nickname("user" + i).build());
    }
    return users;
  }
}
