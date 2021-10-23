package slightlyspring.imgo;

import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
//@Controller
@SpringBootApplication
public class ImgoApplication {

//    private final HttpSession httpSession;

    public static void main(String[] args) {
        SpringApplication.run(ImgoApplication.class, args);
    }

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("userId", httpSession.getAttribute("userId"));
//        return "index";
//    }



}
