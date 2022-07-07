package bjtu.monitor.controller;
import bjtu.monitor.pojo.table.User;
import bjtu.monitor.utils.Global;
import bjtu.monitor.utils.ReturnObject;
import com.sun.mail.imap.Rights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import bjtu.monitor.service.UserService;

import static bjtu.monitor.utils.Global.SUCCESS;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
//    @Autowired
//    private RightsService rightsService;



    /*
     * 登录请求，登录成功跳转到首页;失败重定向(redirect:xxx)到登录页面，并给出相应提示
     * */
    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    public int loginUser(@PathVariable("username")String username,
                          @PathVariable("password")String password,
                          HttpSession session, HttpServletResponse response){


        User user = userService.loginUser(username,password);
        if(user==null){
            return 0;
        }else {
//            List<Rights> rights = rightsService.listRights(user.getUserid());
//            user.setUserRights(rights);
            session.setAttribute("nowUser", user);
            Cookie cookie = new Cookie("id", String.valueOf(user.getUserid()));
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("登录 Cookie saved");
        }
        return 200;
    }


}
