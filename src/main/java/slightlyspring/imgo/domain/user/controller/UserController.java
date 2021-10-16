package slightlyspring.imgo.domain.user.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import slightlyspring.imgo.domain.user.domain.Rival;
import slightlyspring.imgo.domain.user.domain.UserBadge;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.dto.UserProfileDetail;
import slightlyspring.imgo.domain.user.service.UserService;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/{userId}")
    // view 를 분기하지 않고, profile 에 isMyPage 속성을 넘겨서 프론트에서 분기하게끔 하기
    // mypage 내의 읽기/작성 목록은 html 내에서 분기하도록
    // 작성목록을 버리고 읽기목록만 개발
    // 프로필, 작성목록 모두 상단에 '작성목록' 들어가게 하기
    public String profile(@PathVariable Long userId, Model model) {
        if (!userService.isUserExist(userId)) {
            return "404";
        }

        Long sessionUserId = (Long) httpSession.getAttribute("userId");

        UserProfile userProfile = userService.getUserProfile(userId);
        UserProfileDetail userProfileDetail = userService.getUserProfileDetail(userId);
        int firstDayOfWeek = LocalDate.now().with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1)
            .getDayOfMonth();
        PageData pageData = new PageData(userProfile, userProfileDetail);
        pageData.setFirstDayOfWeek(firstDayOfWeek);

        if (sessionUserId.equals(userId)) {
            pageData.setPageRole("myPage");
            pageData.setSidebarBtn("Til 분석하기");
        } else {
            pageData.setPageRole("profile");
            pageData.setSidebarBtn("라이벌등록하기");
        }

        model.addAttribute("pageData", pageData);

//        model.addAttribute("userProfile", userProfile)
//            .addAttribute("userProfileDetail", userProfileDetail)
//            .addAttribute("firstDayOfWeek",
//                LocalDate.now().with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1).getDayOfMonth())
//            .addAttribute("userId", userId);
        return "/user/profile";
    }

    @Data
    class PageData {
        //userProfile
        private Long id;
        private String nickname;
        private String profileImg;
        private String profileDescription;

        //userProfileDetail
        private List<UserBadge> userBadges = new ArrayList<>();
        private List<Rival> rivals = new ArrayList<>();
        private List<UserTilRecord> userTilRecords = new ArrayList<>();

        private String pageRole;
        private String sidebarBtn;
        private int firstDayOfWeek;

        public PageData(UserProfile userProfile, UserProfileDetail userProfileDetail) {
            this.id = userProfile.getId();
            this.nickname = userProfile.getNickname();
            this.profileImg = userProfile.getProfileImg();
            this.profileDescription = userProfile.getProfileDescription();

            this.userBadges = userProfileDetail.getUserBadges();
            this.rivals = userProfileDetail.getRivals();
            this.userTilRecords = getUserTilRecords();
        }

        public boolean isRivalActive(Rival rival) {
            LocalDateTime activeTime = LocalDateTime.now().plusDays(-1);
            Duration duration = Duration.between(activeTime, rival.getTarget().getLastWriteAt());
            return duration.getSeconds() >= 0;
        }

        public boolean isRecorded(int day) {
            return this.userTilRecords.stream().anyMatch(record -> record.getBaseDate().getDayOfMonth() == day);
        }
    }

}
