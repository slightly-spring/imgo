package slightlyspring.imgo.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slightlyspring.imgo.domain.user.service.UserService;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

  private final UserService userService;

  @PostMapping("/continuousDays/{userId}")
  public Map<String, String> updateContinuousDays(@PathVariable Long userId) {
    int nowContinuousDays = userService.updateNowContinuousDaysBatch(userId);
    int maxContinuousDays = userService.updateMaxContinuousDaysBatch(userId);
//    int maxContinuousPast30Days = userService.updateMaxContinuousPast30DaysBatch(userId);

    Map<String, String> jsonReturn = new HashMap<>();
    jsonReturn.put("nowContinuousDays", Integer.toString(nowContinuousDays));
    jsonReturn.put("maxContinuousDays", Integer.toString(maxContinuousDays));
//    jsonReturn.put("maxContinuousPast30Days", Integer.toString(maxContinuousPast30Days));

    return jsonReturn;
  }


}
