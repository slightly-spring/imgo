package slightlyspring.imgo.domain.series.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesRepository seriesRepository;

    @PostMapping("/{userId}")
    @ResponseBody
    public List<Series> findMySeries(@PathVariable Long userId, @RequestParam(name = "title", required = false) String title) {
        if(title != null) {
            return seriesRepository.findAllByTitleContainsAndUserId(title, userId);
        } else {
            return seriesRepository.findAllByUserId(userId);
        }
    }
}
