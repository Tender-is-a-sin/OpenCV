package bjtu.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SkipController {
    @GetMapping("/toLog")
    public String toLog(){
        return "html/Login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "html/Register";
    }
}
