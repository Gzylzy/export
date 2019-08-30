package com.utils.export.mapper;

import com.utils.export.dao.AttendanceDao;
import com.utils.export.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class AttendanceImpl implements AttendanceService {

    @Resource
    AttendanceDao attendanceDao;

    private static Logger logger = LoggerFactory.getLogger(AttendanceImpl.class);

    @Override
    public void exportInfoToDatabase(List list){
        attendanceDao.addAttendance(list);
        logger.info("导出完成");
    }

    @Override
    public String getRecentInfo(){
        return attendanceDao.getRecentInfo();
    }
}
