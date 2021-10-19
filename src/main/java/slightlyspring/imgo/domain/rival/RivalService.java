package slightlyspring.imgo.domain.rival;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RivalService {

  private final RivalRepository rivalRepository;
  static int MAX_NUM = 5;

  public boolean isRivalByUserId(Long myId, Long targetId) {
    List<Rival> rivals = rivalRepository.findByUserId(myId);
    for (int i = 0; i < rivals.size(); i++) {
      if (rivals.get(i).getTarget().getId() == targetId) {
        return true;
      }
    }
    return false;
  }

  public List<Rival> getRivalsByUserId(Long userId) {
    return rivalRepository.findByUserId(userId);
  }

  public Long save(Rival rival) {
    Long userId = rival.getUser().getId();
    Long targetId = rival.getTarget().getId();
    //이미 등록되었는지 check 하고, 저장
    List<Rival> rivals = rivalRepository.findByUserId(
        userId);
    for (Rival r : rivals) {
      if (r.getTarget().getId()==targetId) {
        return r.getId();
      }
    }
    if (rivals.size() == MAX_NUM) {
      return 0l; // 꽉 찼을 때
    }

    return rivalRepository.save(rival).getId();
  }

}
