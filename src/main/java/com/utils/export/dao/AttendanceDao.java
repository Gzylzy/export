package com.utils.export.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AttendanceDao {

    //数据库添加考勤数据
    void addAttendance(List list);

    //获取数据库中最新一条考勤记录日期
    String getRecentInfo();
}
