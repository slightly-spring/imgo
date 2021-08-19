package slightlyspring.imgo.domain.user.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "user_accounts")
@Getter
public class UserAccount {

    @Id @GeneratedValue
    @Column(name = "user_account_id")
    private Long Id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String idToken;

}
