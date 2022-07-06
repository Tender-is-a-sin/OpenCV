package bjtu.monitor.controller;

import bjtu.monitor.pojo.table.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    public int loginUser(@PathVariable("username")String username,
                          @PathVariable("password")String password,
                          HttpSession session, HttpServletResponse response){
        return 200;
    }
}
