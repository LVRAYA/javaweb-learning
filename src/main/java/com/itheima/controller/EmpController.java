package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Resource(name = "empServiceImpl")
    private EmpService empService;
    @GetMapping()
    public Result empList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "gender", required = false) Short gender,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "begin", required = false) LocalDate begin,
                          @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "end", required = false) LocalDate end)
    {
        log.info("当前查询页面{},页面大小{}",page,pageSize);
        return Result.success( empService.listCondition(page, pageSize, name, gender, begin, end));
    }

    @GetMapping("/{id}")
    public Result getEmpById(@PathVariable("id") Integer id)
    {
        log.info("查询id：{}的信息",id);
        Emp emp = empService.getEmpById(id);
        return Result.success(emp);
    }

    @DeleteMapping("/{id}")
    public Result deleteEmp(@PathVariable("id") Integer id)
    {
        log.info("删除员工id:{}",id);
        empService.deleteEmp(id);
        return Result.success();
    }

    @PostMapping()
    public Result addEmp(@RequestBody Emp emp)
    {
        log.info("插入员工姓名: {},id:{}",emp.getName(),emp.getId());
        empService.insertEmp(emp);
        return Result.success();
    }

    @PutMapping()
    public Result updateEmp(@RequestBody Emp emp)
    {
        log.info("更新员工:{}",emp.getName());
        empService.updateEmp(emp);
        return Result.success();
    }

}
