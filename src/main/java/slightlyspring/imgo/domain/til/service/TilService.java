package slightlyspring.imgo.domain.til.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slightlyspring.imgo.domain.til.domain.Til;
import slightlyspring.imgo.domain.til.repository.TilRepository;
import slightlyspring.imgo.domain.user.service.UserService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TilService {

    private final UserService userService;
    private final TilRepository tilRepository;

    public Long save(Til til) {
        Long tilId = tilRepository.save(til).getId();

        Long characterCount = til.getContent().chars().count();
        userService.updateTilLog(til.getUser(), characterCount);

        return tilId;
    }

}
