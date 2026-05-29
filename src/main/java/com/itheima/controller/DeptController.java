package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import com.itheima.service.EmpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //日志注解
@RestController
@RequestMapping("/depts")
public class DeptController {
    //查询所有的数据
    @Resource(name = "deptServiceImpl")
    private DeptService deptService;
    @GetMapping()
    public Result getDepts() {
        //输出日志
        log.info("查询全部的部门数据");
        List<Dept> depts = deptService.getDepts();
        return Result.success(depts);
    }
    //根据id查询部门
    @GetMapping("/{id}")
    public Result getDeptById(@PathVariable("id") Integer id) {
        Dept dept = deptService.getDeptById(id);
        return Result.success(dept);
    }


    @DeleteMapping("/{id}")
    public Result deleteDept(@PathVariable("id") Integer id) {
        log.info("根据id删除部门:{}",id);
        deptService.deleteDept(id);//删除部门
        return Result.success();
    }

    //增加部门
    @PostMapping()
    public Result addDept(@RequestBody Dept dept) {
        log.info("增加部门：{}", dept.getName());
        deptService.insertDept(dept.getName());
        return Result.success();
    }

    //修改部门
    @PutMapping()
    public Result updateDept(@RequestBody Dept dept) {
        log.info("更新部门：{}",dept.getName());
        deptService.updateDept(dept);
        return Result.success();
    }
}
