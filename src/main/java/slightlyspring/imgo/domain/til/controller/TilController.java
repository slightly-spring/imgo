package slightlyspring.imgo.domain.til.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.repository.TagRepository;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.domain.TilTag;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.repository.TilTagRepository;
import slightlyspring.imgo.domain.til.service.TilCardService;
import slightlyspring.imgo.domain.til.service.TilService;

import java.util.List;
import java.util.Map;
import slightlyspring.imgo.domain.til.service.TilTagService;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/til")
public class TilController {

    private final TilService tilService;
    private final TilCardService tilCardService;
//    private final UserService userService;
//    private final TilTagService tilTagService;

    // 테스트 데이터 생성용 repository
    private final UserRepository userRepository;
    private final TilRepository tilRepository;
    private final TagRepository tagRepository;
    private final TilTagRepository tilTagRepository;

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

        return "redirect:/til/detail/" + tilId ;
    }

    @GetMapping("/{userId}/til-cards")
    public ResponseEntity<List<TilCardData>> getTilCardList(@PathVariable Long userId) {
        return new ResponseEntity<>(tilCardService.getTilCardDataListByUserId(userId),
            HttpStatus.OK);
    }

    /**
     * 테스트용 데이터 주입
     */
    @PostConstruct
    public void init() {
        User user = User.builder().nickname("testUser").build();
        userRepository.save(user);

        Tag tagA = Tag.builder().name("tagA").build();
        Tag tagB = Tag.builder().name("tagB").build();
        tagRepository.save(tagA);
        tagRepository.save(tagB);


        Til tilA = Til.builder().title("tilA").user(user).build();
        Til tilB = Til.builder().title("tilB").user(user).build();
        tilRepository.save(tilA);
        tilRepository.save(tilB);

        TilTag tilTagAA = new TilTag(tilA, tagA);
        TilTag tilTagAB = new TilTag(tilA, tagB);
        TilTag tilTagBA = new TilTag(tilB, tagA);
        TilTag tilTagBB = new TilTag(tilB, tagB);
        tilTagRepository.save(tilTagAA);
        tilTagRepository.save(tilTagAB);
        tilTagRepository.save(tilTagBA);
        tilTagRepository.save(tilTagBB);
    }
}
