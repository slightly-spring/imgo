package slightlyspring.imgo.domain.user.controller;

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

@Controller()
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable Long userId, Model model) {
        if (!userService.isUserExist(userId)) {
            return "404";
        }
        UserProfile userProfile = userService.getUserProfile(userId);
        UserProfileDetail userProfileDetail = userService.getUserProfileDetail(userId);
        System.out.println(userProfile.getProfileDescription());
        model.addAttribute("userProfile", userProfile)
                .addAttribute("userProfileDetail", userProfileDetail);

        return "/user/profile";
    }

}
