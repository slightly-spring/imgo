package slightlyspring.imgo.domain.til.domain;

import lombok.*;
import slightlyspring.imgo.domain.tag.domain.Tag;

import javax.persistence.*;

@Entity
@Table(name = "til_tags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TilTag {
    @Id
    @GeneratedValue
    @Column(name = "til_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "til_id")
    private Til til;

    public TilTag(Til til, Tag tag) {
        this.til = til;
        this.tag = tag;
    }
}
