package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;


public interface DeptService {

    //查询所有部门数据
    List<Dept> findAll();

    //删除部门数据
    void deleteById(Integer id);

    //新增部门数据
    void add(Dept dept);


    //根据ID查询部门数据
    Dept getById(Integer id);

    void update(Dept dept);
}
