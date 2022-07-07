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

    @Override
    public int Login(String account, String password) {
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
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            return users.get(0);
        }
        return null;
    }
}
