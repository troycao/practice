package com.troy.practice.loganalysis;

import java.io.*;
import java.sql.*;

public class DeleteSqlRepeatExit {

    public static String contentFileName = "E:\\20190808\\日志数据\\sql\\insert_delete_exit.sql";

    public static void main(String[] args) {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        ResultSet res = null;
        Connection conn = null;
        CallableStatement proc = null;
        String sql = "select * from (select platenum,exittime,parkid,count(platenum) as countnum from pims_exit group by platenum,exittime,parkid) a where a.countnum>1\n";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            while(res.next())
            {
                String platenum = res.getString("platenum");
                String exittime = res.getString("exittime");
                String parkid = res.getString("parkid");
                deleteExit(platenum,exittime,parkid);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteExit(String platenum,String exittime,String parkid){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        Boolean res = null;
        Connection conn = null;
        CallableStatement proc = null;

        String deleteSql = "delete from pims_exit where platenum='"+ platenum +"' and exittime='"+ exittime +"' and parkid='"+ parkid +"'";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.execute(deleteSql);
            String contentSql = "insert into pims_exit(platenum,exittime,parkid) values('"+ platenum+"','"+exittime +"','"+ parkid +"');";
            appendMethod(contentFileName, contentSql + "\r");
        }catch (Exception e){
            e.printStackTrace();
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
