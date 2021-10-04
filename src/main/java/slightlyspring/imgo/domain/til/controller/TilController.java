package slightlyspring.imgo.domain.til.controller;

import io.lettuce.core.dynamic.annotation.Param;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.til.domain.SourceType;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.dto.TilForm;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.til.service.TilCardService;
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import slightlyspring.imgo.domain.til.service.TilTagService;
import slightlyspring.imgo.domain.user.domain.User;
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
    private final HttpSession httpSession;
    private final S3FileUploader s3FileUploader;

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

//    @PostMapping("/save")
//    public String save(@ModelAttribute("tilForm") TilForm tilForm) {
//        // TODO userId using principal
//        Long userId = (Long) httpSession.getAttribute("userId");
//        List<String> tags = tilForm.getTags();
//        Til til = Til.builder()
//                .title(tilForm.getTitle())
//                .content(tilForm.getContent())
//                .sourceType(tilForm.getSourceType())
//                .source(tilForm.getSource())
//                .user(userRepository.getById(userId))
//                .series(seriesRepository.getById(tilForm.getSeriesId()))
//                .build();
//
//        List<Tag> savedTags = tagService.saveTags(tags);
//        Long tilId = tilService.save(til, savedTags);
//
//        return "redirect:/til/" + tilId ;
//    }
//

//    @GetMapping("/{userId}/til-cards")
//    public ResponseEntity<List<TilCardData>> getTilCardList(@PathVariable Long userId) {
//        List<TilCardData> tilCardDataList = tilCardService.getTilCardDataListByUserId(userId);
//        return new ResponseEntity<>(tilCardDataList, HttpStatus.OK);
//    }

    @GetMapping("/{userId}/til-cards")
    public ResponseEntity getTilCardList(@PageableDefault(size=5, sort="createdDate") Pageable pageable, @PathVariable Long userId) {

        List<TilCardData> tilCardDataPages = tilCardService.getTilCardDataPageByUserId(pageable, userId);
        return new ResponseEntity<>(tilCardDataPages, HttpStatus.OK);
    }

    @PostMapping("/image")
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return s3FileUploader.upload(multipartFile, "static/til");
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
