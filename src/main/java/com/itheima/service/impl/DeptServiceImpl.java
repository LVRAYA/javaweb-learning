package com.itheima.service.impl;

import com.itheima.context.BaseContext;
import com.itheima.mapper.DeptMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptLogService;
import com.itheima.service.DeptService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl implements DeptService {
    @Resource(name = "deptMapper")
    private DeptMapper deptMapper;
    @Resource(name = "empMapper")
    private EmpMapper empMapper;
    @Autowired
    private DeptLogService deptLogService;
    @Override
    public List<Dept> getDepts() {
        //不做处理直接返回
        return deptMapper.getDepts();
    }

    @Override
    public Dept getDeptById(Integer id) {
        return deptMapper.getDeptById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void deleteDept(Integer id) {
        //aop获取当前用户名
        Map<String,Object> currentUser = BaseContext.get();
        String operatorName =  currentUser !=null ? (String) currentUser.get("name") : "无名人";
        try {
            deptMapper.deleteDept(id);
            empMapper.deleteEmpByDepetId(id);
            deptLogService.saveDeptLog(id,operatorName+"成功删除部门");
        }catch (Exception e){
            deptLogService.saveDeptLog(id,operatorName+"失败删除部门");
            throw e;
        }

    }

    @Override
    public void insertDept(String name) {
        deptMapper.insertDept(name);
    }

    @Override
    public void updateDept(Dept dept) {
        deptMapper.updateDept(dept);
    }
}
