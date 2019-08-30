package com.utils.export.mapper;

import com.utils.export.dao.CronDao;
import com.utils.export.service.CronService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CronServiceImpl implements CronService {

    @Resource
    CronDao cronDao;

    @Override
    public Map getCron(String name){
        return cronDao.getCron(name);
    }

}
