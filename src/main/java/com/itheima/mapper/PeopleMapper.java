package com.itheima.mapper;

import com.itheima.pojo.People;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PeopleMapper {
    public void savePeople(People people);
}
