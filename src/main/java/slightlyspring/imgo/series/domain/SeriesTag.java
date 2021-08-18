package slightlyspring.imgo.series.domain;

import lombok.Getter;
import slightlyspring.imgo.tag.domain.Tag;

import javax.persistence.*;

@Entity
@Table(name = "series_tags")
@Getter
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
