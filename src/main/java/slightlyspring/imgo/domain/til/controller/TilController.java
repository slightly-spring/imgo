package slightlyspring.imgo.domain.til.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilForm;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.til.service.TilCardService;
import slightlyspring.imgo.domain.til.service.TilImageService;
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import slightlyspring.imgo.domain.user.service.UserService;
import slightlyspring.imgo.infra.S3FileUploader;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    private final TagService tagService;
    private final UserRepository userRepository;
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

    @DeleteMapping("/{tilId}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Long tilId, HttpServletRequest request) {
        // TODO userId using principal
        Long userId = (Long) httpSession.getAttribute("userId");
        Til til = tilRepository.getById(tilId);

        if(Objects.equals(til.getUser().getId(), userId)) {
            tilRepository.deleteById(tilId);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/write")
    public String write(Model model) {
        // TODO userId using principal
        Long userId = (Long) httpSession.getAttribute("userId");

        List<Series> seriesList = seriesRepository.findAllByUserId(userId);

        model.addAttribute("seriesList", seriesList);
        return "/til/write";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("tilForm") TilForm tilForm) {
        // TODO userId using principal
        Long userId = (Long) httpSession.getAttribute("userId");
        List<String> tags = tilForm.getTags();

        Til.TilBuilder tilBuilder = Til.builder()
                .title(tilForm.getTitle())
                .content(tilForm.getContent())
                .sourceType(tilForm.getSourceType())
                .source(tilForm.getSource())
                .user(userRepository.getById(userId));
        Optional.ofNullable(tilForm.getSeriesId())
                .ifPresent(id -> tilBuilder.series(seriesRepository.getById(id)));
        Til til = tilBuilder.build();

        List<Tag> savedTags = tagService.saveTags(tags);
        Long tilId = tilService.save(til, savedTags);

        return "redirect:/til/" + tilId ;
    }

    @GetMapping("/{userId}/til-cards")
    public ResponseEntity tilCardsByUserId(@PageableDefault(size=5, sort="id", direction = Direction.DESC) Pageable pageable, @PathVariable Long userId) {

        List<TilCardData> tilCardDataPages = tilCardService.getTilCardDataByUserId(pageable, userId);
        ResponseEntity<List<TilCardData>> tilCardResponse = new ResponseEntity<>(
            tilCardDataPages, HttpStatus.OK);
        return tilCardResponse;
    }

    @GetMapping("/til-cards")
    public ResponseEntity tilCards(
        @PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {
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
