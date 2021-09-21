package slightlyspring.imgo.domain.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  USER("ROLE_USER", "일반 사용자");

  private final String Key;
  private final String title;
}
