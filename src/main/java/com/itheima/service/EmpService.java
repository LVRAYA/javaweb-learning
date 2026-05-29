package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;

import java.time.LocalDate;

public interface EmpService {

    public PageBean list(Integer start, Integer pageSize);
    public PageBean listCondition(Integer start, Integer pageSize,String name, Short gender, LocalDate begin, LocalDate end);

    //删除员工
    public void deleteEmp(Integer id);

    //新增员工
    public void insertEmp(Emp emp);

    void updateEmp(Emp emp);

    Emp getEmpById(Integer id);

    Emp login(String username, String password);

}
