package slightlyspring.imgo.domain.series.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.tag.domain.Tag;

import javax.persistence.*;

@Entity
@Table(name = "series_tags")
@Getter
@Builder
@AllArgsConstructor
public class SeriesTag {
    @Id
    @GeneratedValue
    @Column(name = "series_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
}
