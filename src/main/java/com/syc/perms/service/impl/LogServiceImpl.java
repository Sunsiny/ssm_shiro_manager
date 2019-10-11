package com.syc.perms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syc.perms.mapper.TbLogMapper;
import com.syc.perms.pojo.R;
import com.syc.perms.pojo.TbLog;
import com.syc.perms.pojo.TbLogExample;
import com.syc.perms.pojo.UserSearch;
import com.syc.perms.service.LogService;
import com.syc.perms.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private TbLogMapper logMapper;
    //保存日志
    @Override
    public void saveLog(TbLog log) {
        logMapper.insert(log);
    }

    @Override
    public R getLogList(Integer page, Integer limit, UserSearch search) {
        PageHelper.startPage(page, limit);

        TbLogExample example = new TbLogExample();
        example.setOrderByClause("id DESC");
        TbLogExample.Criteria criteria = example.createCriteria();

        //按照operation进行模糊查询
        if (search.getOperation() != null && !"".equals(search.getOperation())) {
            criteria.andOperationLike("%" + search.getOperation() + "%");
        }

        if (search.getCreateTimeStart() != null && !"".equals(search.getCreateTimeStart())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.getDateByString(search.getCreateTimeStart()));
        }

        if (search.getCreateTimeEnd() != null && !"".equals(search.getCreateTimeEnd())) {
            criteria.andCreateTimeLessThanOrEqualTo(DateUtil.getDateByString(search.getCreateTimeEnd()));
        }

        List<TbLog> logs = logMapper.selectByExample(example);

        PageInfo<TbLog> info=new PageInfo<>(logs);
        R result=new R();
        result.setCode(0);
        result.setCount(info.getTotal());
        result.setData(info.getList());
        return result;
    }
}
