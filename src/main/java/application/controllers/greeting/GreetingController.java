package application.controllers.greeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
}
