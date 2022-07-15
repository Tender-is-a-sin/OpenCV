package bjtu.monitor.service;

import bjtu.monitor.pojo.table.User;
import bjtu.monitor.pojo.table.UserFile;

import java.util.List;

public interface UserFileService {

    List<UserFile> listFile(int pn); //获取数据库中的所有用户
}
