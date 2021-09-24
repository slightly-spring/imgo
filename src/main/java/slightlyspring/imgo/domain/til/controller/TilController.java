package slightlyspring.imgo.domain.til.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilForm;
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @GetMapping("/write")
    public String write() {

        // TODO 유저(+시리즈) 정보 가져오기

        return "/til/write";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("tilForm") TilForm tilForm) {
        // TODO userId using principal
        Long userId = (Long) httpSession.getAttribute("userId");
        List<String> tags = tilForm.getTags();
        Til til = Til.builder()
                .title(tilForm.getTitle())
                .content(tilForm.getContent())
                .sourceType(tilForm.getSourceType())
                .source(tilForm.getSource())
                .user(userRepository.getById(userId))
                .series(tilForm.getSeries())
                .build();

        List<Tag> savedTags = tagService.saveTags(tags);
        Long tilId = tilService.save(til, savedTags);

        return "redirect:/til/detail/" + tilId ;
    }
}
