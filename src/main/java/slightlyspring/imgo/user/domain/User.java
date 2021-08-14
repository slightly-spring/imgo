package slightlyspring.imgo.user.domain;

import lombok.Generated;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "UserAccount")
    private UserAccount userAccount;

    private String nickname;

    private String profileImg;

    private String profileDescription;

    private int nowContinuousDays;

    private int maxContinuousDays;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
