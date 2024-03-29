package slightlyspring.imgo.domain.series.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.til.dto.TilCardData;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeriesCardData {
  private Long seriesId;
  private String title;
  private String description;
  private List<String> tags;
  private String seriesImageUrl;
  private boolean completed;
}
