package slightlyspring.imgo.domain.til.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilForm;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.service.TilCardService;
import slightlyspring.imgo.domain.til.service.TilImageService;
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import slightlyspring.imgo.domain.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SeriesRepository seriesRepository;
    private final TilRepository tilRepository;
    private final TilImageService tilImageService;
    private final HttpSession httpSession;
    private final TilCardService tilCardService;

    @GetMapping("/{tilId}")
    public String detail(@PathVariable Long tilId, Model model) {
        Til til = tilRepository.getById(tilId);
        model.addAttribute("til", til);
        return "/til/detail";
    }

    @GetMapping("/write")
    public String write(Model model) {
        // TODO userId using principal
        Long userId = (Long) httpSession.getAttribute("userId");

        List<Series> seriesList = seriesRepository.findAllByUserId(userId);

        model.addAttribute("seriesList", seriesList);
        model.addAttribute("userId", userId);
        return "/til/write";
    }

    @PostMapping("/save")
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
                .series(seriesRepository.getById(tilForm.getSeriesId()))
                .build();

        userService.updateLastWriteAt(userId, LocalDateTime.now());

        List<Tag> savedTags = tagService.saveTags(tags);
        Long tilId = tilService.save(til, savedTags);

        return "redirect:/til/" + tilId ;
    }

    @GetMapping("/{userId}/til-cards")
    public ResponseEntity tilCardsByUserId(@PageableDefault(size=5, sort="createdDate") Pageable pageable, @PathVariable Long userId) {

        List<TilCardData> tilCardDataPages = tilCardService.getTilCardDataByUserId(pageable, userId);
        ResponseEntity<List<TilCardData>> tilCardResponse = new ResponseEntity<>(
            tilCardDataPages, HttpStatus.OK);
        return tilCardResponse;
    }

    @GetMapping("/til-cards")
    public ResponseEntity tilCards(
        @PageableDefault(size = 5, sort = "createdDate") Pageable pageable) {
        List<TilCardData> tilCardDataPages = tilCardService.getTilCardData(pageable);
        ResponseEntity<List<TilCardData>> tilCardResponse = new ResponseEntity<>(
            tilCardDataPages, HttpStatus.OK);
        return tilCardResponse;
    }

    @PostMapping("/image")
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return tilImageService.uploadImage(multipartFile, "static/til");
    }
}
