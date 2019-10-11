package com.syc.perms.mapper;

import com.syc.perms.pojo.TbUsers;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//查询日期对应的数据
public interface MainMapper {

    @Select("SELECT * FROM tb_users WHERE TO_DAYS(NOW()) = TO_DAYS(create_time); ")
    List<TbUsers> getTodayUsers();

    @Select("SELECT * FROM tb_users WHERE TO_DAYS(NOW()) - TO_DAYS(create_time) = 1 ;")
    List<TbUsers> getUsersYesterday();

    @Select("SELECT * FROM tb_users WHERE DATEDIFF(create_time,NOW())<=0 AND DATEDIFF(create_time,NOW())>-7")
    List<TbUsers> getUsersYearWeek();

    @Select("SELECT * FROM tb_users WHERE DATE_FORMAT(create_time,'%Y%m') = DATE_FORMAT(CURDATE(),'%Y%m');")
    List<TbUsers> getUsersMonth();
}
