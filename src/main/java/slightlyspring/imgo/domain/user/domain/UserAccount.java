package slightlyspring.imgo.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.global.config.JpaAuditConfig.CreatedModifiedTimeEntity;

@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
/**
 * Entity UserAccount
 *
 * id Long
 * user_id User
 * auth-type AuthType
 * auth-id String (google: sub value / kakao: / naver: ...)
 * role Role
 *
 * 모든 칼럼이 올바른 값을 가져야 함
 * so, default 값 설정 안해놓기 => 0, null, false 로 기본값이 잡힘
 */
public class UserAccount extends CreatedModifiedTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_account_id")
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String authId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @Enumerated(EnumType.STRING)
    private Role role;

    /*---메서드---*/
    public UserAccount updateRole(Role role) {
        this.role = role;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
