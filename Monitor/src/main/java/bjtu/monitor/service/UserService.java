package bjtu.monitor.service;

import bjtu.monitor.pojo.table.User;

public interface UserService {
    int Login(String account,String password);
    int Register(User user);
    int modifyInfo(int ID,User user);
    int modifyPassword(int ID,String pass);
    int AssignRights(int rights,int id);   // （root级管理员）将权限分配给其他管理员
    User getUserByUserName(String name);
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <Description> UserService
 *  与用户有关的业务均在此接口中声明，比如登录，注册等等
 */
@Service
public interface UserService {

    /*注册部分*/

    /*
     *注册业务，传入一个对象用于注册,注册成功返回相应对象(带id),否则返回null
     * */
    //注册成为用户
    User registerUser(User user);

    /*登录部分*/

    /*
     * 同样,登陆成功返回用户实体(包含从数据库中查到的所有字段),否则返回null
     * */
    User loginUser(String username,String password);

    /*找回密码部分
     * xzx
     * */
    String retrievePassword(String username); //根据用户名查询密码并返回，调用此方法的前提是验证码输入正确

    /*通用部分*/
    List<User> listUser(int pn); //获取数据库中的所有用户 fbf
    List<User> listUser(); //获取数据库中的所有用户
    List<User> listUserSelective(User user,int pn); //xzx
    User getUserById(Integer id); //根据id获取用户 fbf
    User getUserByUserName(String username); //xzx
    boolean ifExistUser(String username); //wj
    boolean ifExistUser(Integer id); //wj
    int deleteUser(Integer userID);


}
