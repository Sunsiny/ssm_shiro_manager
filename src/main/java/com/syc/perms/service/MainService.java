package com.syc.perms.service;

import com.syc.perms.pojo.TbUsers;

import java.util.List;

public interface MainService {
    List<TbUsers> getUsers();

    List<TbUsers> getTodayUsers();

    List<TbUsers> getUsersYesterday();

    List<TbUsers> getUsersMonth();

    List<TbUsers> getUsersYearWeek();

    long countByGender(int sex);

}
