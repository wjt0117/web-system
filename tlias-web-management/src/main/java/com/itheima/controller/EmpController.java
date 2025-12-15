package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//员工管理的controller
@Slf4j
@RestController
@RequestMapping("/emps")

public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    //分页查询的方法
    public Result Page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String name, Integer gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询： {},{},{},{},{},{}",page,pageSize,name,gender,begin,end);
       PageResult<Emp> pageResult= empService.page(page,pageSize,name,gender,begin,end);
       return Result.success(pageResult);
    }

    @PostMapping
    //新增员工
    public Result save(@RequestBody Emp emp){
        log.info("新增员工:{}",emp);
        empService.save(emp);
        return Result.success();
    }


    //删除员工
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工：{}",ids);

        empService.delete(ids);
        return Result.success();
    }

    //根据ID查询员工信息
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据ID查询员工信息: {}",id);
        Emp emp=empService.getInfo(id);
        return Result.success(emp);

    }
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工数据:{}",emp);
        empService.update(emp);
        return Result.success();
    }


}
