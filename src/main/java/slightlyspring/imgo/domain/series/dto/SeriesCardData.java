package slightlyspring.imgo.domain.series.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeriesCardData {

  String title;
  String description;
  List<String> tags;
  boolean isCompleted;
  // like
}
