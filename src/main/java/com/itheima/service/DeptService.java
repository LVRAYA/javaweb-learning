package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    public List<Dept> getDepts();
    public Dept getDeptById(Integer id);

    public void deleteDept(Integer id);

    public void insertDept(String name);
    public void updateDept(Dept dept);
}
