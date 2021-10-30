package slightlyspring.imgo.domain.rival.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import slightlyspring.imgo.domain.rival.domain.Rival;
import slightlyspring.imgo.domain.rival.service.RivalService;
import slightlyspring.imgo.domain.user.repository.UserRepository;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rival")
public class RivalController {

    private final HttpSession httpSession;
    private final UserRepository userRepository;
    private final RivalService rivalService;

    @PostMapping("/{targetId}")
    public ResponseEntity save(@PathVariable Long targetId) {
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        Rival rival = Rival.builder()
                .user(userRepository.getById(sessionUserId))
                .target(userRepository.getById(targetId))
                .build();

        Long savedId = rivalService.saveRival(rival);
        if (savedId != 0L) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity delete(@PathVariable Long targetId) {
        Long sessionUserId = (Long) httpSession.getAttribute("userId");
        rivalService.deleteRival(sessionUserId, targetId);
        return new ResponseEntity(HttpStatus.OK);
    }
}