package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mapper
public interface EmpMapper {
    //使用PageHelper定义的sql语句不能加分号，
    //仅仅能对紧跟在后面的第一个查询语句进行分页出来
   // @Select("select  e.* ,d.name deptName from emp e left join dept d on e.dept_id=d.id order by e.update_time desc ")

   //查询所有员工数据
    List<Emp> list(String name, Integer gender,
                           LocalDate begin, LocalDate end);

   @Options(useGeneratedKeys = true,keyProperty = "id")//主键返回 付值给emp
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "VALUES(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")

    void insert(Emp emp);

    //根据ID批量删除员工基本信息
    void deleteByIds(@Param("ids") List<Integer> ids);

    //根据ID查询员工信息和工作经历信息
    Emp getById(Integer id);

    //根据ID更新员工的基本信息
    void updateById(Emp emp);

    //统计员工职位人数
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();


    @Select("select id,username,name,image from emp " +
            "where username=#{username} and password=#{password} ")
    Emp selectByUsernameAndPassword(Emp emp);

 //-----------------------------------------------
//    //查询总记录数
//   @Select("select  count(*)  from emp e left join dept d on e.dept_id=d.id ;")
//
//    public Long count();
//
//    @Select("select  e.* ,d.name deptName from emp e left join dept d on e.dept_id=d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize};")
//    public List<Emp> list(Integer start, Integer pageSize);
}
