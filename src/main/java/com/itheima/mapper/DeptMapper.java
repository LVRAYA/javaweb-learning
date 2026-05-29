package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
    //查询所有的部门
    public List<Dept> getDepts();
    //按照id查询部门
    @Select("select * from dept where id = #{id}")
    public Dept getDeptById(Integer id);
    //按照id删除部门
    @Delete("delete from dept where id = #{id}")
    public void deleteDept(Integer id);
    //添加部门
    public void insertDept(String name);
    //修改部门
    public void updateDept(Dept dept);
}
