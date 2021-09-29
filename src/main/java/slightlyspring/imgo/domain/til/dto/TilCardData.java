package slightlyspring.imgo.domain.til.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.tag.domain.Tag;

@Data
@Builder
public class TilCardData {
  private String title;
  private int likeCount;
  private LocalDateTime createdAt;
  private List<String> tags;
  private String nickname;
}
