package com.troy.practice.loganalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CalcOrder {

    public static String contentFileName = "E:\\20190808\\日志数据\\sql\\v4\\order_sql.sql";

    public static void main(String[] args) {
        String sql = "select * from pims_entry where entrytime>='2019-07-15 00:00:00' order by entrytime ";
        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list){
            String platenum = (String) map.get("PLATENUM");
            String entrytime = (String) map.get("ENTRYTIME");
            String parkid = (String) map.get("PARKID");
            getExit(platenum,entrytime,parkid);
        }
    }

    public static void getExit(String platenum,String entrytime,String parkid){
        String exitSql = "select * from (select * from pims_exit where platenum='"
                + platenum + "' and parkid='"+ parkid +"' and exittime>='"
                + entrytime + "' order by exittime) a where rownum<=1 ";

        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(exitSql);
        for (Map<String, Object> map:list) {
            String exittime = (String) map.get("EXITTIME");
            System.out.println("platenum:" + platenum + " entrytime:" + entrytime + " parkid:" + parkid + " exittime:" + exittime);
            String sql = "INSERT INTO pims_order(platenum,parkId,entrytime,exittime) VALUES('"+ platenum
                    +"','"+ parkid
                    +"','"+ entrytime
                    +"','"+ exittime +"');";
            appendMethod(contentFileName, sql + "\r");
        }
    }

    public static void appendMethod(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
