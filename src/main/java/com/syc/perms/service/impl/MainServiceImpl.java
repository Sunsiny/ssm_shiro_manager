package com.syc.perms.service.impl;

import com.syc.perms.mapper.MainMapper;
import com.syc.perms.mapper.TbUsersMapper;
import com.syc.perms.pojo.TbUsers;
import com.syc.perms.pojo.TbUsersExample;
import com.syc.perms.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    @Autowired
    private TbUsersMapper usersMapper;

    @Autowired
    private MainMapper mainMapper;

    //查询所有用户
    @Override
    public List<TbUsers> getUsers() {
        return usersMapper.selectByExample(new TbUsersExample());
    }
    //获取今天用户的信息
    @Override
    public List<TbUsers> getTodayUsers() {
        return mainMapper.getTodayUsers();
    }
    //昨天
    @Override
    public List<TbUsers> getUsersYesterday() {
        return mainMapper.getUsersYesterday();
    }
    //这一月
    @Override
    public List<TbUsers> getUsersMonth() {

        return mainMapper.getUsersMonth();
    }
    //这一个星期的
    @Override
    public List<TbUsers> getUsersYearWeek() {
        return mainMapper.getUsersYearWeek();
    }
    //查询人的性别的数量
    @Override
    public long countByGender(int sex) {
        TbUsersExample example = new TbUsersExample();
        example.createCriteria().andSexEqualTo(String.valueOf(sex));
        return usersMapper.countByExample(example);
    }
}
