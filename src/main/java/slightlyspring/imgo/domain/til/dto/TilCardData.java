package slightlyspring.imgo.domain.til.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.tag.domain.Tag;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TilCardData {
  private Long tilId;
  private String title;
  private int likeCount;
  private String createdAt;
  private List<String> tags;
  private String nickname;
  private Long userId;
  private String tilImageUrl;
}
