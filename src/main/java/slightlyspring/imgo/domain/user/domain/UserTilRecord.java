package slightlyspring.imgo.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

@Entity
@Table(name = "user_til_records")
@Getter
@Builder
@DynamicInsert //Dynamic - null 값이 들어오면 쿼리에 미포함
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
/**
 * 하루마다 최초에 글을 쓸 때에 생성
 * 그날 쓴 글의 개수
 * 그날 쓴 글자 수
 * 기록
 */
public class UserTilRecord extends CreatedModifiedTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_til_records_id")
    private Long id;

    @CreatedDate
    private LocalDate baseDate;

    @Column(nullable = false)
    @Builder.Default
    private int tilCount = 1;

    @Column(nullable = false)
    @Builder.Default
    private int characterCount = 0; // 본문 글자 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserTilRecord update(int c) {
        tilCount++;
        characterCount += c;
        return this;
    }
}
