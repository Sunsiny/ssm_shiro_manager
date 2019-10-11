package com.syc.perms.web;


import com.syc.perms.pojo.TbUsers;
import com.syc.perms.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    MainService mainService;

    /**
     * 查询总的用户
     */
    @ResponseBody
    @RequestMapping(value = "/getUserTotal", method = RequestMethod.GET)
    public List<TbUsers> getTotalUser() {
        return mainService.getUsers();
    }
    /**
     * 查询今天创建的用户
     */
    @ResponseBody
    @RequestMapping(value = "/getUsersToday", method = RequestMethod.GET)
    public List<TbUsers> getUsersToday() {
        return mainService.getTodayUsers();
    }
    //昨天用户信息的查询
    @ResponseBody
    @RequestMapping(value = "/getUsersYestoday", method = RequestMethod.GET)
    public List<TbUsers> getUsersYesterday() {
        return mainService.getUsersYesterday();
    }
    //这一周信息
    @ResponseBody
    @RequestMapping(value = "/getUsersYearWeek", method = RequestMethod.GET)
    public List<TbUsers> getUsersYearWeek() {
        return mainService.getUsersYearWeek();
    }
    //这一个月的信息
    @ResponseBody
    @RequestMapping(value = "/getUsersMonth", method = RequestMethod.GET)
    public List<TbUsers> getUsersMonth() {
        return mainService.getUsersMonth();
    }

    /**
     * 统计每个性别的人数
     * {'categories':,'values':'xxx'}
     */
    @ResponseBody
    @RequestMapping(value = "/dataAccessGender", method = RequestMethod.GET)
    //利用键值对，存储性别的信息
    public Map<String, Object> dataAccessGender() {
        Map<String, Object> map = new HashMap<>();
        //写一个数组，用来声明性别
        String[] categories = {"男", "女", "保密"};
        //
        map.put("categories", categories);
        //0,1,2分别代表不同的性别，2保密
        List<Map<String, Object>> values = new ArrayList<>();
        for (int sex = 0; sex < categories.length; sex++) {
            //查询出全部性别的数量
            long sexCount = mainService.countByGender(sex);

            //一个map是一个性别
            Map<String, Object> json = new HashMap<>();
            //每个性别的数量统计
            json.put("value", sexCount);
            //性别的名字
            json.put("name", categories[sex]);
            //用json来传值
            values.add(json);
        }
        //不同性别的数量
        map.put("values", values);
        return map;
    }

}
