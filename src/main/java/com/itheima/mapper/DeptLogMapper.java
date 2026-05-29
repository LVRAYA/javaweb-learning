package com.itheima.mapper;

import com.itheima.pojo.DeptLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RestController;

@Mapper
public interface DeptLogMapper {
    //插入日志信息
    @Insert("insert into deptlog(id,content,create_time) " +
            "values(#{id},#{content},now())")
    void insertDeptLog(Integer id,String content);
}
