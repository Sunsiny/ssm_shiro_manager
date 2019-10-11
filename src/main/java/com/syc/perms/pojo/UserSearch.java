package com.syc.perms.pojo;

import lombok.Data;

//对这些字段信息，进行日志的记录
@Data
public class UserSearch {

    private String nickname;

    private String sex;

    private String status;

    private String createTimeStart;

    private String createTimeEnd;

    private String operation;

}
