package bjtu.monitor.service.Impl;

import bjtu.monitor.mapper.UserMapper;
import bjtu.monitor.pojo.table.User;
import bjtu.monitor.pojo.table.UserExample;
import bjtu.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static bjtu.monitor.utils.Global.*;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private MailService mailService;


    @Override
    public int updateUser(int userId, User user) {
        return 0;
    }

    @Override
    public int Register(User user) {
        return 0;
    }

    @Override
    public int modifyInfo(int ID, User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(ID);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else
            return FAIL;
    }

    @Override
    public int modifyPassword(int ID, String pass) {
        User user = new User();
        user.setPassword(pass);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(ID);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else
            return FAIL;
    }

    @Override
    public int AssignRights(int rights, int id) {
        User user = new User();
        user.setRights(rights);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(id);
        if(userMapper.updateByExampleSelective(user,userExample)>0)
            return SUCCESS;
        else return  FAIL;
    }

    @Override
    public User getUserByUserName(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(name);
    public Map<Integer, List<User>> getUserListByRightsForAssignContract() {
        return null;
    }

    @Override
    public User registerUser(User user) {
        // 判断是否已存在该用户
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> userList = userMapper.selectByExample(userExample);
        if (!userList.isEmpty())
        {
            return null;
        }

        // 否则注册为新的用户
        userMapper.insert(user);

        /* 成功后告知对方已成功注册 */
        mailService.sendMail(user.getEmail() ,
                "Register Notice    "+"Register successfully! Welcome to use our products, if you have any " +
                "comments, please feel free to feedback, we will actively improve, thank you.");

        return user;
    }

    @Override
    public User loginUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPasswordEqualTo(password).andUsernameEqualTo(username);
        List<User> users =userMapper.selectByExample(userExample);
        if(users!=null){
            if(users.size()==1) return users.get(0);
            else return null;
        }
        else return null;
    }

    @Override
    public String retrievePassword(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users!=null){
            if (users.size()!=0) return users.get(0).getPassword();
            else return null;
        }
        return null;
    }

    @Override
    public List<User> listUser(int pn) {
        PageHelper.startPage(pn,5); //每页显示5个数据
        return userMapper.selectByExample(null);
    }

    @Override
    public List<User> listUser() {
        return userMapper.selectByExample(null);
    }

    @Override
    public List<User> listUserSelective(User user, int pn) {
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByUserName(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            return users.get(0);
        }
        return null;
    }

    @Override
    public boolean ifExistUser(String username)
    {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean ifExistUser(Integer id)
    {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(id);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int deleteUser(Integer userID){
        return userMapper.deleteByPrimaryKey(userID);
    }

    @Override
    public List<User> getUsersAboutContract(int contractId) {
        return null;
    }


}
