package com.syc.perms.mapper;

import com.syc.perms.pojo.TbLog;
import com.syc.perms.pojo.TbLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLogMapper {
    long countByExample(TbLogExample example);

    int deleteByExample(TbLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbLog record);

    int insertSelective(TbLog record);

    List<TbLog> selectByExample(TbLogExample example);

    TbLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbLog record, @Param("example") TbLogExample example);

    int updateByExample(@Param("record") TbLog record, @Param("example") TbLogExample example);

    int updateByPrimaryKeySelective(TbLog record);

    int updateByPrimaryKey(TbLog record);
}