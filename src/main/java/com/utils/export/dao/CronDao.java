package com.utils.export.dao;

import java.util.Map;

public interface CronDao {

    //根据名称获取定时任务信息
    Map getCron(String name);

    //更新定时任务
    void updateCron(Map map);
}
