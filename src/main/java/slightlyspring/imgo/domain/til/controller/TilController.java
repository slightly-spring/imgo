package slightlyspring.imgo.domain.til.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    @GetMapping("/write")
    public String write() {

        // TODO 유저(+시리즈) 정보 가져오기

        return "/til/write";
    }

}
