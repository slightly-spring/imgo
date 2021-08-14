package slightlyspring.imgo.user.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "user_accounts")
@Getter
public class UserAccount {

    @Id @GeneratedValue
    private Long Id;

    @OneToOne
    private User user;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String idToken;

}
