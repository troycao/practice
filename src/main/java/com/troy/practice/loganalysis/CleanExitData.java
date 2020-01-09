package com.troy.practice.loganalysis;

import java.util.List;
import java.util.Map;

/***
 * 清理exit中重复的数据
 */
public class CleanExitData {

    public static void main(String[] args) {
        String sql = "select * from pims_exit where exittime>='2019-07-16 00:00:00' and exittime<='2019-07-16 23:59:59'";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list) {
            String platenum = (String) map.get("PLATENUM");
            String parkid = (String) map.get("PARKID");
            String exittime = (String) map.get("EXITTIME");
            String exitSql = "select count(*) as exitCount from pims_exit where platenum='"+platenum
                    +"' and parkid='"+ parkid
                    +"' and exittime>'"+ exittime +"' and exittime<= to_char(to_date('"+ exittime +"','yyyy-mm-dd hh24:mi:ss') + 10/60/24,'yyyy-mm-dd hh24:mi:ss') ";
            List<Map<String, Object>> countList = DruidJdbcUtils.executeQuery(exitSql);

            String exitCount = countList.get(0).get("EXITCOUNT").toString();
           int count = Integer.parseInt(exitCount);
            if (count > 0){
                System.out.println(platenum + ":" + countList);
            }
        }
        //System.out.println(list);
    }
}
