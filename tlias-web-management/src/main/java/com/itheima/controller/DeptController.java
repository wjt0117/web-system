package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DeptController {

    @Autowired
    private DeptService deptService;

    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    //查询所有的部门数据
    @GetMapping("/depts")
    public Result list(){
        System.out.println("查询全部部门数据");
        List<Dept> deptList=deptService.findAll();
        return Result.success(deptList);

    }


    //根据ID删除部门
    @DeleteMapping("/depts")
    public Result delete(@RequestParam("id") Integer id){
        System.out.println("根据ID删除部门:"+id);
        deptService.deleteById(id);
        return Result.success();
    }

    //根据name增加部门  requesrbody注解是将json数据封装到对象中
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        System.out.println("根据name增加"+dept);
        deptService.add(dept);
        return Result.success();

    }

    //根据ID查询部门
    @GetMapping("/depts/{id}")
    public Result gerInfo(@PathVariable("id") Integer id){
        System.out.println("根据ID查询部门："+id);
        Dept dept=deptService.getById(id);
        return Result.success(dept);

    }

    //修改部门
    @PutMapping("/depts")
    //前端传递的json数据封装到dept对象中
    public Result update(@RequestBody Dept dept){
        System.out.println("根修改部门："+dept);
        deptService.update(dept);
        return Result.success();

    }





}
