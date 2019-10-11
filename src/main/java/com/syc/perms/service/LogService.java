package com.syc.perms.service;


import com.syc.perms.pojo.R;
import com.syc.perms.pojo.TbLog;
import com.syc.perms.pojo.UserSearch;

public interface LogService {
    //保存日志
    void saveLog(TbLog log);
    //获取日志内容
    R getLogList(Integer page, Integer limit, UserSearch search);
}
