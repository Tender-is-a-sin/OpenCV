package bjtu.monitor.mapper;

import bjtu.monitor.pojo.table.UserFile;
import bjtu.monitor.pojo.table.UserFileExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserFileMapper {
    int countByExample(UserFileExample example);

    int deleteByExample(UserFileExample example);

    int insert(UserFile record);

    int insertSelective(UserFile record);

    List<UserFile> selectByExample(UserFileExample example);

    int updateByExampleSelective(@Param("record") UserFile record, @Param("example") UserFileExample example);

    int updateByExample(@Param("record") UserFile record, @Param("example") UserFileExample example);
}