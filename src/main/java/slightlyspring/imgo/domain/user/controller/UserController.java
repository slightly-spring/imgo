package slightlyspring.imgo.domain.user.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import slightlyspring.imgo.domain.user.dto.ProfilePageData;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.dto.UserProfileDetail;
import slightlyspring.imgo.domain.user.service.UserService;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        if (!userService.isUserExist(userId)) {
            return "404";
        }

        Long sessionUserId = (Long) httpSession.getAttribute("userId");

        UserProfile userProfile = userService.getUserProfile(userId);
        UserProfileDetail userProfileDetail = userService.getUserProfileDetail(userId);

        ProfilePageData pageData = new ProfilePageData(userProfile, userProfileDetail);

        if (sessionUserId.equals(userId)) {
            pageData.setPageRole("mypage");
        } else {
            pageData.setPageRole("profile");
        }

        model.addAttribute("pageData", pageData);

        return "/user/profile";
    }

}
