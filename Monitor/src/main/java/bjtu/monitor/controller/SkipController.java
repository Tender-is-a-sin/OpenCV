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

    @GetMapping("/toHome")
    public String toHome(){
        return "html/home";
    }

    @GetMapping("/toObfuscation")
    public String toObfuscation(){
        return "html/obfuscation";
    }

    @GetMapping("/toRecord")
    public String toRecord(){
        return "html/record";
    }

    @GetMapping("/toReplay")
    public String toReplay(){
        return "html/replay";
    }
}
