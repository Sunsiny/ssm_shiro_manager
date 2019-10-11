package com.syc.perms.mapper;

import com.syc.perms.pojo.TbCarousel;
import com.syc.perms.pojo.TbCarouselExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCarouselMapper {
    long countByExample(TbCarouselExample example);

    int deleteByExample(TbCarouselExample example);

    int deleteByPrimaryKey(Byte id);

    int insert(TbCarousel record);

    int insertSelective(TbCarousel record);

    List<TbCarousel> selectByExample(TbCarouselExample example);

    TbCarousel selectByPrimaryKey(Byte id);

    int updateByExampleSelective(@Param("record") TbCarousel record, @Param("example") TbCarouselExample example);

    int updateByExample(@Param("record") TbCarousel record, @Param("example") TbCarouselExample example);

    int updateByPrimaryKeySelective(TbCarousel record);

    int updateByPrimaryKey(TbCarousel record);
}