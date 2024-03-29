package slightlyspring.imgo.domain.series.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slightlyspring.imgo.domain.series.domain.Series;
import slightlyspring.imgo.domain.series.repository.SeriesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public List<Series> getMySeries(Long userId) {
        return seriesRepository.findAllByUserId(userId);
    }

    public Long saveSeries(Series series) {
        if(!seriesRepository.existsByUserIdAndTitle(series.getUser().getId(), series.getTitle())) {
            return seriesRepository.save(series).getId();
        } else {
            return 0L;
        }
    }

    public void deleteSeries(Long seriesId) {
        seriesRepository.deleteById(seriesId);
    }
}
