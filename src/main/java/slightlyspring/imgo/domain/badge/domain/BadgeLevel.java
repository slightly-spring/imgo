package slightlyspring.imgo.domain.badge.domain;

import java.util.List;
import org.springframework.data.util.Pair;

public enum BadgeLevel{
  LV1,
  LV2,
  LV3,
  LV4,
  LV5;

  public boolean isLast() {
    return values().length-1 == this.toIndex();
  }

  public BadgeLevel next() {
      return values()[this.ordinal()+1 % values().length];
  }

  public int toIndex() {
    return this.ordinal();
  }

}
