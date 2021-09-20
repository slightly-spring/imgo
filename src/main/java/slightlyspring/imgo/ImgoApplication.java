package slightlyspring.imgo;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import slightlyspring.imgo.domain.user.dto.SessionUser;

@SpringBootApplication
@Controller
public class ImgoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgoApplication.class, args);
    }

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
//        // session 에 있는 사용자 가져오기
//        // DB 에서 가져오게끔 바꾸기
        SessionUser user = (SessionUser) httpSession.getAttribute("user"); //1
        if (user != null) { //2
            model.addAttribute("name", user.getName());
        }

        return "index";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


}
