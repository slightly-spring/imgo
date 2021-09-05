package slightlyspring.imgo.domain.user.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import slightlyspring.imgo.global.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class loginController {

  private final HttpSession httpSession;

  @GetMapping("/login")
  public String login(Model model) {
    SessionUser user = (SessionUser) httpSession.getAttribute("user");
    if (user != null) {
      //세션에 저장된 값이 있을때만, model 에 userName 으로 등록
      //세션에 저장된 값이 없다면, 로그인 버튼만 보일 것
      model.addAttribute("userName", user.getName());
    }
    return "login";
  }

}
