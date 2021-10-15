package slightlyspring.imgo.domain.user.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        if (sessionUserId.equals(userId)) {
            model.addAttribute("pageRole", "mypage");
        } else {
            model.addAttribute("pageRole", "profile");
        }

        model.addAttribute("userProfile", userProfile)
            .addAttribute("userProfileDetail", userProfileDetail)
            .addAttribute("firstDayOfWeek",
                LocalDate.now().with(WeekFields.of(Locale.KOREA).dayOfWeek(), 1).getDayOfMonth())
            .addAttribute("userId", userId);
        return "/user/profile_test";
    }

}
