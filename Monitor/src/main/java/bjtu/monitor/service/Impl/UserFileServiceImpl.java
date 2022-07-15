package bjtu.monitor.service.Impl;

import bjtu.monitor.mapper.UserFileMapper;
import bjtu.monitor.mapper.UserMapper;
import bjtu.monitor.pojo.table.UserFile;
import bjtu.monitor.service.UserFileService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileMapper fileMapper;
    @Override
    public List<UserFile> listFile(int pn) {
//        PageHelper.startPage(pn,5); //每页显示5个数据
        return fileMapper.selectByExample(null);
    }
}
