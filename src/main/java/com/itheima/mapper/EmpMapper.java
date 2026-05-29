package com.itheima.mapper;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    @Select("select * from emp")
    public List<Emp> list();

    public List<Emp> listCondition(String name, Short gender, LocalDate begin, LocalDate end);

    //根据id删除员工
    @Delete("delete from emp where id = #{id}")
    public void deleteEmp(Integer id);

    //新增员工
    public void insertDept(Emp emp);

    void updateEmp(Emp emp);

    Emp getEmpById(Integer id);

    Emp login(String username, String password);

    @Delete("delete from emp where dept_id=#{id}")
    void deleteEmpByDepetId(Integer id);
}
