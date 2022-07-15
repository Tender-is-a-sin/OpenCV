package bjtu.monitor.controller;

import bjtu.monitor.pojo.table.User;
import bjtu.monitor.pojo.table.UserFile;
import bjtu.monitor.service.MailService;
import bjtu.monitor.service.UserFileService;
import bjtu.monitor.service.UserService;
import bjtu.monitor.utils.ReturnObject;
import bjtu.monitor.utils.Week;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

import static bjtu.monitor.utils.Global.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    UserFileService fileService;

    @GetMapping("/login/{username}/{password}")
    @ResponseBody
    public int loginUser(@PathVariable("username")String username,
                         @PathVariable("password")String password,
                         HttpSession session, HttpServletResponse response){


        User user = userService.loginUser(username,password);
        if(user==null){
            return FAIL;  //登录失败返回0
        }else {
//            List<Rights> rights = rightsService.listRights(user.getUserid());
//            user.setUserRights(rights);
            session.setAttribute("nowUser", user);
            Week week = userService.getUserFile(user.getUserid());
            session.setAttribute("week",week);
            Cookie cookie = new Cookie("id", String.valueOf(user.getUserid()));
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("登录 Cookie saved");
        }
        return user.getRights();  // 登录成功返回权限
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

    @GetMapping("/modifyUserInfo")
    @ResponseBody
    public int modifyUserInfo(Integer userId, String email,
                                 String username,String sex) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setSex(sex);
        return userService.modifyInfo(userId,newUser);

    }

    @GetMapping("/modifyUserPass")
    @ResponseBody
    public int modifyUserPass(Integer userId, String pass) {

        return userService.modifyPassword(userId,pass);

    }
    @GetMapping("/listUser/{pageNum}")
    @ResponseBody
    public ReturnObject<PageInfo> listUser(User user,
                                           @PathVariable("pageNum")Integer pageNum){

        List<User> userList = userService.listUser();
        PageInfo pageInfo = new PageInfo(userList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(SUCCESS,pageInfo);

        return result;
    }
    @GetMapping("/modifyUserRights")
    @ResponseBody
    public int modifyUserRights(Integer rights,Integer userId) {

        return userService.AssignRights(rights,userId);

    }
    @GetMapping("/listRecord/{pageNum}")
    @ResponseBody
    public ReturnObject<PageInfo> record(@PathVariable("pageNum")Integer pageNum){

        List<UserFile> fileList = fileService.listFile(pageNum);
        PageInfo pageInfo = new PageInfo(fileList,5);
        ReturnObject<PageInfo> result = new ReturnObject<>(SUCCESS,pageInfo);

        return result;
    }
    @PostMapping("/deleteUser")
    @ResponseBody
    public void deleteUser(Integer userid) {
        userService.deleteUser(userid);
        String paths = adminface;
        File file = new File(paths);
//        System.out.println("warning"+name);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++)
        {
            if(array[i].getName().compareTo(userid+".png")==0){
                array[i].delete();
            }
        }
    }
}
