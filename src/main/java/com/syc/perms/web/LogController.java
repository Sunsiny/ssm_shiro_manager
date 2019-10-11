package com.syc.perms.web;

import com.syc.perms.pojo.R;
import com.syc.perms.pojo.UserSearch;
import com.syc.perms.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/logList", method = RequestMethod.GET)
    public String showLogList() {
        return "page/log/logList";
    }

    @ResponseBody
    @RequestMapping(value = "/getLogList", method = RequestMethod.GET)
    public R getLogList(Integer page, Integer limit, UserSearch search) {
        return logService.getLogList(page,limit,search);
    }

}
