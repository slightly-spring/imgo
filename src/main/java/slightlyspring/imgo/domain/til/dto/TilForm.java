package slightlyspring.imgo.domain.til.dto;

import lombok.Getter;
import lombok.Setter;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.til.domain.SourceType;

import java.util.List;

@Getter @Setter
public class TilForm {
    private String title;
    private String content;
    private SourceType sourceType;
    private String source;
    private Series series;
    private Long userId;
    private List<String> tags;
}
