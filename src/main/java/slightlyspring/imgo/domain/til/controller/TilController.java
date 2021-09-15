package slightlyspring.imgo.domain.til.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.service.TilService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    
    @GetMapping("/write")
    public String write() {

        // TODO 유저(+시리즈) 정보 가져오기

        return "/til/write";
    }

    @PostMapping("save")
    public String save(@RequestParam Map<String, Object> param) {
        Til til = (Til) param.get("til");
        List<Tag> tags = (List<Tag>) param.get("tags");

        // TODO tags 저장, 저장된 tag의 id를 til에 반영

        Long tilId = tilService.save(til);

        return "redirect:/til/detail/"+ tilId ;
    }
}
