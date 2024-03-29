package slightlyspring.imgo.domain.badge.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.util.Pair;

public enum BadgeType {
    TYPE1("셀럽", "/img/badge-celeb.svg",
        Pair.of(BadgeLevel.LV1, 10),
        Pair.of(BadgeLevel.LV2, 100),
        Pair.of(BadgeLevel.LV3, 1000),
        Pair.of(BadgeLevel.LV4, 10000),
        Pair.of(BadgeLevel.LV5, 100000)
        ), //셀럽
    TYPE2("너로 정했다", "/img/badge-you.svg",
        Pair.of(BadgeLevel.LV1, 1),
        Pair.of(BadgeLevel.LV2, 3),
        Pair.of(BadgeLevel.LV3, 5)
    ), //너로 정했다
    TYPE3("공공의 적", "/img/badge-enemy.svg",
        Pair.of(BadgeLevel.LV1, 1),
        Pair.of(BadgeLevel.LV2, 3),
        Pair.of(BadgeLevel.LV3, 5),
        Pair.of(BadgeLevel.LV4, 10),
        Pair.of(BadgeLevel.LV5, 100)
    ), //공공의 적
    TYPE4("작심3일", "/img/badge-3days.svg",
        Pair.of(BadgeLevel.LV1, 3),
        Pair.of(BadgeLevel.LV2, 7),
        Pair.of(BadgeLevel.LV3, 15),
        Pair.of(BadgeLevel.LV4, 30)
    ), //작심3일
    TYPE5("내일은 출판왕", "/img/badge-king.svg",
        Pair.of(BadgeLevel.LV1, 1),
        Pair.of(BadgeLevel.LV2, 3),
        Pair.of(BadgeLevel.LV3, 5),
        Pair.of(BadgeLevel.LV4, 10),
        Pair.of(BadgeLevel.LV5, 50)
    );  //내일은 출판왕

    public final String badgeName;

    public String logo;

    public List<Pair<BadgeLevel, Integer>> levelValues = new ArrayList<>();

    @SafeVarargs
    BadgeType(String name, String logo, Pair<BadgeLevel, Integer>... levelValueIn) {
        this.badgeName = name;
        this.logo = logo;
        this.levelValues.addAll(Arrays.asList(levelValueIn));
    }

    public Integer getValueByLevel(BadgeLevel badgeLevel) {
        if (this.levelValues.size()-1 < badgeLevel.toIndex()) {
            return null;
        }
        return this.levelValues.get(badgeLevel.toIndex()).getSecond();
    }

    public Integer getNextValueByLevel(BadgeLevel badgeLevel) {
        if (this.levelValues.size()-1 < badgeLevel.toIndex()+1 || badgeLevel.isLast()) {
            return null;
        }
        return this.levelValues.get(badgeLevel.next().toIndex()).getSecond();
    }


}
