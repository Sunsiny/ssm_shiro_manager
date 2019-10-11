package com.syc.perms.mapper;

import com.syc.perms.pojo.TbRolesMenusExample;
import com.syc.perms.pojo.TbRolesMenusKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRolesMenusMapper {
    long countByExample(TbRolesMenusExample example);

    int deleteByExample(TbRolesMenusExample example);

    int deleteByPrimaryKey(TbRolesMenusKey key);

    int insert(TbRolesMenusKey record);

    int insertSelective(TbRolesMenusKey record);

    List<TbRolesMenusKey> selectByExample(TbRolesMenusExample example);

    int updateByExampleSelective(@Param("record") TbRolesMenusKey record, @Param("example") TbRolesMenusExample example);

    int updateByExample(@Param("record") TbRolesMenusKey record, @Param("example") TbRolesMenusExample example);
}