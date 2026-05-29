package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.PageBean;
import com.itheima.service.EmpService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Resource(name = "empMapper")
    EmpMapper empMapper;
    @Override
    public PageBean list(Integer start, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(start, pageSize);
        //执行查询
        List<Emp> empList = empMapper.list();
        Page<Emp> p = (Page<Emp>) empList;
        //返回PageBean封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public PageBean listCondition(Integer start, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        //设置分页参数
        PageHelper.startPage(start, pageSize);
        //执行查询
        List<Emp> empList = empMapper.listCondition(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        //返回PageBean封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    //删除员工
    @Override
    public void deleteEmp(Integer id) {
        empMapper.deleteEmp(id);
    }

    //新增员工
    @Override
    public void insertEmp(Emp emp) {
        empMapper.insertDept(emp);
    }

    @Override
    public void updateEmp(Emp emp) {
        empMapper.updateEmp(emp);
    }

    @Override
    public Emp getEmpById(Integer id) {

        return empMapper.getEmpById(id);
    }

    @Override
    public Emp login(String username, String password) {

        return empMapper.login(username,password);
    }




}
