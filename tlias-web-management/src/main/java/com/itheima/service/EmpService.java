package com.itheima.service;


import com.itheima.pojo.Emp;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    //分页查询的方法
    PageResult<Emp> page(Integer page, Integer pageSize,String name, Integer gender,
                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end);

    //新增员工的方法
    void save(Emp emp);

    //删除员工
    void delete(List<Integer> ids);

    //根据ID查询员工
    Emp getInfo(Integer id);

    //修改员工
    void update(Emp emp);


    LoginInfo login(Emp emp);
}
