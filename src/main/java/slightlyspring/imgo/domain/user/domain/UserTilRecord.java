package slightlyspring.imgo.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

@Entity
@Table(name = "user_til_records")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTilRecord extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_til_records_id")
    private Long id;

//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDate baseDate;

    @ColumnDefault("1")
    private int tilCount;

    private int characterCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserTilRecord update(int c) {
        tilCount++;
        characterCount += c;
        return this;
    }
}
