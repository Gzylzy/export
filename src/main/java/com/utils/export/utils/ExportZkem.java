package com.utils.export.utils;

import com.utils.export.service.AttendanceService;
import com.utils.export.service.MachineInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ExportZkem {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MachineInfoService machineInfoService;

    public static ExportZkem exportZkem;

    private static Logger logger = LoggerFactory.getLogger(ExportZkem.class);

    @PostConstruct
    public void init() {
        exportZkem = this;
        exportZkem.attendanceService = this.attendanceService;
    }

    ZkemSDK sdk = new ZkemSDK();

    /**
     * 连接考勤机
     * @return
     */
    public boolean getConnectZkem() {
        LocalDateTime today = LocalDateTime.now();
        logger.info("连接时间：" + today);
        //获取考勤机ip，端口等数据
        Map machineInfo = exportZkem.machineInfoService.getMachineInfo(1);
        String ip = machineInfo.get("ip").toString();
        int port = Integer.parseInt(machineInfo.get("port").toString());
        //连接考勤机
        boolean connFlag = sdk.connect(ip, port);
        if (connFlag){
            logger.info("连接成功");
            return true;
        }else {
            logger.error("连接失败，尝试重新连接");
           return false;
        }
    }

    /**
     * 导出数据至数据库
     */
    public void exportZkem(){
        if (sdk.readGeneralLogData()){
            //日期格式化
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //获取记录
            List<Map<String,Object>> resultList = sdk.getGeneralLogData();
            //记录排序
            Collections.sort(resultList, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    try {
                        Date name1 = df.parse(o1.get("Time").toString());//name1是从你list里面拿出来的一个
                        Date name2 = df.parse(o2.get("Time").toString()); //name1是从你list里面拿出来的第二个name
                        return name1.compareTo(name2);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            //获取数据库最新一条记录日期
            String recentTime = exportZkem.attendanceService.getRecentInfo();
            if(recentTime == null){
                recentTime = "2019-01-01";
            }
            try{
                Date recentDate = df.parse(recentTime);
                for (int i = 0; i < resultList.size(); i++){
                    Map map = resultList.get(i);
                    if (!recentDate.before(df.parse(map.get("Time").toString()))){
                        resultList.remove(i);
                        i--;
                    }else {
                        Map userMap = sdk.getUserInfoByNumber(map.get("EnrollNumber").toString());
                        map.putAll(userMap);
                    }
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
            if(resultList.size() != 0){
                exportZkem.attendanceService.exportInfoToDatabase(resultList);

            } else {
                logger.info("无新纪录");
            }
            sdk.disConnect();
            logger.info("连接终止");
        } else {
            logger.warn("数据为空");
        }
    }
}
