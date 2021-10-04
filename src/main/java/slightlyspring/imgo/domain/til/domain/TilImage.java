package slightlyspring.imgo.domain.til.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "til_images")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TilImage {
    @Id
    @GeneratedValue
    @Column(name = "til_image_id")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "til_id")
    private Til til;
}
