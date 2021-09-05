package slightlyspring.imgo.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import lombok.NoArgsConstructor;
import slightlyspring.imgo.domain.user.helper.AuthType;

@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@Getter
public class UserAccount {

    @Id @GeneratedValue
    @Column(name = "user_account_id")
    private Long Id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

//    @Enumerated(EnumType.STRING)
//    private AuthType authType;

//    private String idToken;

    @Builder
    public UserAccount(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public UserAccount update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
