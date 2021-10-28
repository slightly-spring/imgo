package slightlyspring.imgo.domain.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FeedController {

  @GetMapping("/")
  public String feed() {
    return "/feed/index";
  }

}
