package slightlyspring.imgo.domain.tag.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import slightlyspring.imgo.domain.series.domain.SeriesTag;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedTimeOnlyEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
@Table(name = "tags")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends CreatedTimeOnlyEntity {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    @FullTextField
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<TilTag> tilTags = new ArrayList<>();

    @OneToMany(mappedBy = "series")
    private List<SeriesTag> seriesTags = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
}
