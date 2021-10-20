package slightlyspring.imgo.domain.til.controller;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.service.UserService;
import slightlyspring.imgo.infra.S3FileUploader;


@Controller
@RequiredArgsConstructor
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
    private final UserRepository userRepository;
    private final TilService tilService;
    private final TagService tagService;

    @GetMapping("/{tilId}")
    public String detail(@PathVariable Long tilId, Model model) {
        Til til = tilRepository.getById(tilId);
        model.addAttribute("til", til);
        return "/til/detail";
    }

    private final TilCardService tilCardService;
//    private final UserService userService;
//    private final TilTagService tilTagService;

    // 테스트 데이터 생성용 repository
    private final TagRepository tagRepository;
    private final TilTagRepository tilTagRepository;

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


//    /**
//     * 테스트용 데이터 주입
//     */
//    @PostConstruct
//    public void init() {
//        User user = User.builder().nickname("testUser").build();
//        userRepository.save(user);
//        int offset = 14;
//        for (int i=0; i<offset; i++) {
//            Tag tagA = Tag.builder().name("tag" + i).build();
//            Tag tagB = Tag.builder().name("tag" + (i + offset)).build();
//            tagRepository.save(tagA);
//            tagRepository.save(tagB);
//
//            Til til = Til.builder().title("testTil"+i).user(user).build();
//            tilRepository.save(til);
//
//            TilTag tilTagAA = new TilTag(til, tagA);
//            TilTag tilTagAB = new TilTag(til, tagB);
//            tilTagRepository.save(tilTagAA);
//            tilTagRepository.save(tilTagAB);
//        }
//    }
}
