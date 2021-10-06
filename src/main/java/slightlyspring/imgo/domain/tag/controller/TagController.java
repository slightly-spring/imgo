package slightlyspring.imgo.domain.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/suggestions")
    public List<String> getSuggestions(@RequestParam String value) {
        List<Tag> tags = tagService.searchTags(value);
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }
}
