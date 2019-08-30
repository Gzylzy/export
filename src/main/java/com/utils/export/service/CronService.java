package com.utils.export.service;

import java.util.Map;

public interface CronService {

   //获取定时任务信息
   Map getCron(String name);
}
