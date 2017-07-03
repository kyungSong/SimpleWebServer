package website.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Created by Kyung Song on 6/28/2017.
 */

@Controller
public class WebController {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required = false, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";

    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
}
