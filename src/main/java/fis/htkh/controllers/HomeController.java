package fis.htkh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "views/frontend/index";
    }

    @GetMapping("/admin/home")
    public String home(ModelMap model) {
        model.addAttribute("active", "home");
        return "views/backend/home";
    }
}
