package es.goya.dwes.saludo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping({ "/", "/greeting" })
    public String greeting(@RequestParam(name = "name", defaultValue = "") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting"; // src/main/resources/templates/greeting.html
    }
}