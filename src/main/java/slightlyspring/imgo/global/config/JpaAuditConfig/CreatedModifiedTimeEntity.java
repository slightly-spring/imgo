package slightlyspring.imgo.global.config.JpaAuditConfig;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //2
/**
 * CreatedModifiedTimeEntity 를 상속받아서 Entity 작성.
 * -> Entity column 에 자동으로 createdDate, modifiedDate 가 추가
 */
public class CreatedModifiedTimeEntity {

  @CreatedDate // Entity 가 생성되어 저장될 때 시간이 자동 저장됨
  private LocalDateTime createdDate;

  @LastModifiedDate // 조회한 Entity 의 값을 변경할 때 시간이 자동 저장됨
  private LocalDateTime modifiedDate;
}
