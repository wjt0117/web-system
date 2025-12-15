package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.Utils.JwtUtils;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.itheima.mapper.EmpExprMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private  EmpMapper empMapper;

    @Autowired
    private  EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

//    public EmpServiceImpl(EmpMapper empMapper, EmpExprMapper empExprMapper) {
//        this.empMapper = empMapper;
//
//        this.empExprMapper = empExprMapper;
//    }

    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize,String name, Integer gender,
                                LocalDate begin,LocalDate end) {
        //设置分页参数
        PageHelper.startPage(page, pageSize);

        //执行查询
        List<Emp> empList=empMapper.list(name,gender,begin,end);


        Page<Emp> p=(Page<Emp>) empList;
        //解析结果并封装
        return new PageResult<Emp>(p.getTotal(), p.getResult());


    }

    @Transactional//事务管理  --默认出现运行时异常才会回滚
    @Override
    public void save(Emp emp) {
        //保存员工的基本信息
        try {
            emp.setCreateTime(LocalDateTime.now());//增加创建时间记录在数据库中，因为前端没有传递创建时间和修改时间
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);


            //int a=1/0;
            //保存员工的工作经历信息
            List<EmpExpr> exprList=emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                //遍历集合 为empID赋值
                exprList.forEach(empExpr->{
                    empExpr.setEmpId(emp.getId());//给工作经历表的emp_id赋值，赋值的是员工表的id
                });


                empExprMapper.insertBatch(exprList);



            }
        } finally {
            EmpLog empLog=new EmpLog(null,LocalDateTime.now(),"新增员工"+emp);
            empLogService.insertLog(empLog);

        }


    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        //批量删除员工的基本信息
        empMapper.deleteByIds(ids);
        //批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {

        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        //根据ID修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //根据ID修改员工工作经历信息
            //先删除原来的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
            //再添加
        List<EmpExpr>  exprList=emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr-> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);

        }


    }

    @Override
    public LoginInfo login(Emp emp) {
        //调用mapper接口 根据员工的用户名和密码查询信息
       Emp e = empMapper.selectByUsernameAndPassword(emp);

        //判断是否存在，存在组装登录信息
        if(e!=null){
            log.info("登录成功，员工信息{}",e);

            //生成JWT令牌
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String jwt=JwtUtils.generateToken(claims);


            return new LoginInfo(e.getId(),e.getUsername(),e.getName(),jwt);
        }

        //不存在返回null
        return null;
    }


//    @Autowired
//    private EmpMapper empMapper;
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//
//
//
//      //----------------原始分页查询--
//        //调用mapper接口查询走记录数
//      //  Long total = empMapper.count();
//        //调用mapper接口，查询结果列表
//       // Integer start=(page-1)*pageSize;
//      //  List<Emp> rows = empMapper.list(start, pageSize);
//        //封装结果
//
//
//        return new PageResult<Emp>(total,rows);
//    }
}
