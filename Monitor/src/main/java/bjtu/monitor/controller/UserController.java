package bjtu.monitor.controller;

import bjtu.monitor.pojo.table.User;
import bjtu.monitor.service.MailService;
import bjtu.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

import static bjtu.monitor.utils.Global.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    public int loginUser(@PathVariable("username")String username,
                          @PathVariable("password")String password,
                          HttpSession session, HttpServletResponse response){
        return 200;
    }
    @PostMapping("/register")
    @ResponseBody
    public int register(User user, @RequestParam("code")String codeValue){
        String code = mailService.getCode(user.getEmail(),REGISTER);
        if(codeValue.compareTo(code)!=0){
            return CODEERROR;
        }
        user.setSex("男");
        user.setRegistertime(new Date());
        user.setRights(100);
        if(userService.registerUser(user)!=null){
            return SUCCESS;
        }
        return FAIL;
    }


}
