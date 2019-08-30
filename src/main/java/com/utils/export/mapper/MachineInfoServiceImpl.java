package com.utils.export.mapper;

import com.utils.export.dao.MachineInfoDao;
import com.utils.export.service.MachineInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MachineInfoServiceImpl implements MachineInfoService {

    @Resource
    private MachineInfoDao machineInfoDao;

    @Override
    public Map getMachineInfo(int code){
        return machineInfoDao.getMachineInfo(code);
    }
}
