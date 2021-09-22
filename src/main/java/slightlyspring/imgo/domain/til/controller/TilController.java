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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    private final TagService tagService;
    private final UserRepository userRepository;
    
    @GetMapping("/write")
    public String write() {

        // TODO 유저(+시리즈) 정보 가져오기

        return "/til/write";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("tilForm") TilForm tilForm) {
        List<String> tags = tilForm.getTags();
        Til til = Til.builder()
                .title(tilForm.getTitle())
                .content(tilForm.getContent())
                .sourceType(tilForm.getSourceType())
                .source(tilForm.getSource())
                .user(userRepository.getById(6L))
                .series(tilForm.getSeries())
                .build();

        // TODO userId using principal
        // TODO content serving
        // TODO duplicated tags insert

        List<Tag> savedTags = tagService.saveTags(tags);
        Long tilId = tilService.save(til, savedTags);

        return "redirect:/til/detail/" + tilId ;
    }
}
