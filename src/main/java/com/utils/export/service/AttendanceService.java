package com.utils.export.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AttendanceService {

    //导出考勤信息到数据库
    void exportInfoToDatabase(List list);

    //获取数据库中最新一条数据的日期
    String getRecentInfo();
}
