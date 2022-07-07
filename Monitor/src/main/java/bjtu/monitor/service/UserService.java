package bjtu.monitor.service;

import bjtu.monitor.pojo.table.User;

public interface UserService {
    int Login(String account,String password);
    int Register(User user);
    int modifyInfo(int ID,User user);
    int modifyPassword(int ID,String pass);
    int AssignRights(int rights,int id);   // （root级管理员）将权限分配给其他管理员
    User getUserByUserName(String name);
}
