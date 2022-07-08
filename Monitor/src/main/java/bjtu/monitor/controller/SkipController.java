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

    @GetMapping("/toFacesign")
    public String toFacesign(){
        return "html/admin/facesign";
    }

    @GetMapping("/toRealtimemonitor")
    public String toRealtimemonitor(){
        return "html/admin/realtimemonitor";
    }
    @GetMapping("/toUser_info_jurisdiction")
    public String toUser_info_jurisdiction(){
        return "html/admin/user_info_jurisdiction";
    }
    @GetMapping("/toAdmin")
    public String toAdmin(){
        return "html/admin/statistics";
    }

    @GetMapping("/toStatistics")
    public String toStatistics(){
        return "html/admin/statistics";
    }

    @GetMapping("/toRecord_admin")
    public String toRecord_admin(){
        return "html/admin/record_admin";
    }
    @GetMapping("/toReplay_admin")
    public String toReplay_admin(){
        return "html/admin/replay_admin";
    }
    @GetMapping("/toWhitelist")
    public String toWhitelist(){
        return "html/admin/whitelist";
    }
}
