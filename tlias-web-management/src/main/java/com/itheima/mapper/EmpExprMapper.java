package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    //批量保存员工的工作经历信息
    void insertBatch(List<EmpExpr> exprList);


    //批量删除员工的工作经历信息
    void deleteByEmpIds(@Param("ids") List<Integer> ids);


}
