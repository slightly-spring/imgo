package slightlyspring.imgo.domain.user.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.tag.domain.Tag;
import slightlyspring.imgo.domain.tag.service.TagService;
import slightlyspring.imgo.domain.til.domain.SourceType;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.til.service.TilService;
import slightlyspring.imgo.domain.user.domain.User;
import slightlyspring.imgo.domain.user.domain.UserTilRecord;
import slightlyspring.imgo.domain.user.dto.UserProfile;
import slightlyspring.imgo.domain.user.repository.UserRepository;
import slightlyspring.imgo.domain.user.repository.UserTilRecordRepository;


@SpringBootTest
@Transactional
//@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TilService tilService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserTilRecordRepository userTilRecordRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private TilRepository tilRepository;
//    @Autowired
//    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    void setUp() {
        String nickname = "test01";
        String profileImg = "sample_image_url";
        String profileDescription = "hi it is description";

        user = User.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .profileDescription(profileDescription)
                .build();

        userRepository.save(user);
    }

    @Test
    void 유저_프로필_출력() {
        //given
        String nickname = "test01";
        String profileImg = "sample_image_url";
        String profileDescription = "hi it is description";

        User user = userRepository.save(User.builder()
            .nickname(nickname)
            .profileImg(profileImg)
            .profileDescription(profileDescription)
            .build());
        //when
        UserProfile userProfile = userService.getUserProfile(user.getId());

        //then
        assertThat(userProfile.getNickname(), is(equalTo(user.getNickname())));
        assertThat(userProfile.getProfileImg(), is(equalTo(user.getProfileImg())));
        assertThat(userProfile.getProfileDescription(), is(equalTo(user.getProfileDescription())));
    }

    @Test
    void update_now_지속작성기간1() {
        //given
        Long userId = user.getId();

        //when
        int nowContinuousDays = userService.updateNowContinuousDaysBatch(userId);

        //then
        assertThat(nowContinuousDays).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_now_지속작성기간_30일내_연속() {
        //given
        int numOfRecord = 10;
        List<UserTilRecord> records = new ArrayList<>();
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i);
            records.add(tmp);
        }

        //when
        int nowContinuousDays = userService.updateNowContinuousDaysBatch(user.getId());

        //then
        assertThat(nowContinuousDays).isEqualTo(10);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_now_지속작성기간_30일내_불연속() {
        //given
        int numOfRecord = 10;
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            if (i<5) {
                changeBaseDate(tmp, i);
            } else {
                changeBaseDate(tmp, i+3);
            }

        }

        //when
        int nowContinuousDays = userService.updateNowContinuousDaysBatch(user.getId());

        //then
        assertThat(nowContinuousDays).isEqualTo(5);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_now_지속작성기간_오늘안씀() {
        //given
        int numOfRecord = 10;
        List<UserTilRecord> records = new ArrayList<>();
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i+1);
            records.add(tmp);
        }

        //when
        int nowContinuousDays = userService.updateNowContinuousDaysBatch(user.getId());

        //then
        assertThat(nowContinuousDays).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_now_지속작성기간_연속30일넘어감() {
        //given
        int numOfRecord = 33;
        List<UserTilRecord> records = new ArrayList<>();
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i);
            records.add(tmp);
        }

        //when
        int nowContinuousDays = userService.updateNowContinuousDaysBatch(user.getId());

        //then
        assertThat(nowContinuousDays).isEqualTo(30);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_max_지속작성기간_안씀() {
        //given

        //when
        int maxContinuousDays = userService.updateMaxContinuousDaysBatch(user.getId());

        //then
        assertThat(maxContinuousDays).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_max_지속작성기간_30일이내_연속() {
        //given
        int numOfRecord = 10;
        List<UserTilRecord> records = new ArrayList<>();
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i+1);
            records.add(tmp);
        }

        //when
        int maxContinuousDays = userService.updateMaxContinuousDaysBatch(user.getId());

        //then
        assertThat(maxContinuousDays).isEqualTo(10);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_max_지속작성기간_30일이내_불연속() {
        //given
        int numOfRecord = 15;
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i*2);
        }

        //when
        int maxContinuousDays = userService.updateMaxContinuousDaysBatch(user.getId());

        //then
        assertThat(maxContinuousDays).isEqualTo(1);
    }

    @Test
    @WithMockUser(roles = "USER")
    void update_max_지속작성기간_30일걸쳐서() {
        //given
        int numOfRecord = 2;
        for (int i = 0; i < numOfRecord; i++) {
            UserTilRecord tmp = UserTilRecord.builder().user(user).build();
            userTilRecordRepository.save(tmp);
            changeBaseDate(tmp,i+29);
        }

        //when
        int maxContinuousDays = userService.updateMaxContinuousDaysBatch(user.getId());

        //then
        assertThat(maxContinuousDays).isEqualTo(1);
    }

    /*---helper---*/
    private Til createTil() {
        Series series = Series.builder()
            .title("series title")
            .build();
        Series savedSeries = seriesRepository.save(series);

        List<String> tagList = Arrays.asList("tag1", "tag2");
        List<Tag> savedTags = tagService.saveTags(tagList);
        Long tilId = tilService.save(Til.builder()
            .title("til title")
            .content("til content test")
            .sourceType(SourceType.COMMON)
            .source("스프링 데이터 JPA")
            .user(user)
            .series(savedSeries)
            .build(), savedTags);
        return tilRepository.getById(tilId);
    }



    private void changeBaseDate(UserTilRecord record, int ageInDays) {
        // This syntax is for H2 DB.  For MySQL you need to use DATE_ADD
        String sql = "UPDATE user_til_records SET base_date = DATEADD('DAY', -"+ageInDays+", NOW()) WHERE user_til_records_id='"+record.getId()+"'";
        jdbcTemplate.execute(sql);
        record.setBaseDate(LocalDate.now().minusDays(ageInDays));
    }
}