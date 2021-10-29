package slightlyspring.imgo.domain.series.controller;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.dto.SeriesCardData;
import slightlyspring.imgo.domain.series.dto.SeriesTitle;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;
import slightlyspring.imgo.domain.series.service.SeriesCardService;
import slightlyspring.imgo.domain.series.service.SeriesService;
import slightlyspring.imgo.domain.til.dto.TilCardData;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SeriesCardService seriesCardService;

    @GetMapping("/{userId}")
    @ResponseBody
    public List<SeriesTitle> findMySeries(@PathVariable Long userId, @RequestParam(name = "title", required = false) String title) {
        List<Series> result;
        if(title != null) {
            result = seriesRepository.findAllByUserIdAndTitleContains(userId, title);
        } else {
            result = seriesRepository.findAllByUserId(userId);
        }
        return result.stream().map(r -> modelMapper.map(r, SeriesTitle.class)).collect(Collectors.toList());
    }

    @PostMapping("/{userId}")
    @ResponseBody
    public Long save(@PathVariable Long userId, @RequestBody Map<String, String> map) {
        String title = map.get("title");
        Series series = Series.builder()
                .title(title)
                .user(userRepository.getById(userId))
                .build();
        return seriesService.saveSeries(series);
    }

    @GetMapping("/{userId}/series-cards")
    public ResponseEntity seriesCardsByUserId(@PageableDefault(size=5, sort="id", direction = Direction.DESC) Pageable pageable, @PathVariable Long userId) {

        List<SeriesCardData> seriesCardDataPages = seriesCardService.getSeriesCardDataByUserID(pageable, userId);
        ResponseEntity<List<SeriesCardData>> seriesCardResponse = new ResponseEntity<>(
            seriesCardDataPages, HttpStatus.OK);
        return seriesCardResponse;
    }

    @GetMapping("/series-cards")
    public ResponseEntity seriesCards(@PageableDefault(size=5, sort="id" , direction = Direction.DESC) Pageable pageable) {

        List<SeriesCardData> seriesCardDataPages = seriesCardService.getSeriesCardData(pageable);
        ResponseEntity<List<SeriesCardData>> seriesCardResponse = new ResponseEntity<>(
            seriesCardDataPages, HttpStatus.OK);
        return seriesCardResponse;
    }


}
