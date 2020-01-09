package com.troy.practice.loganalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DeleteSqlRepeatEntry {

    public static String contentFileName = "E:\\20190808\\日志数据\\sql\\insert_delete_entry.sql";

    public static void main(String[] args) {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        ResultSet res = null;
        Connection conn = null;
        CallableStatement proc = null;
        String sql = "select * from (select platenum,entrytime,parkid,count(platenum) as countnum from pims_entry group by platenum,entrytime,parkid) a where a.countnum>1\n";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            while(res.next())
            {
                String platenum = res.getString("platenum");
                String entrytime = res.getString("entrytime");
                String parkid = res.getString("parkid");
                deleteEntry(platenum,entrytime,parkid);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEntry(String platenum,String entrytime,String parkid){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        Boolean res = null;
        Connection conn = null;
        CallableStatement proc = null;

        String deleteSql = "delete from pims_entry where platenum='"+ platenum +"' and entrytime='"+ entrytime +"' and parkid='"+ parkid +"'";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.execute(deleteSql);
            String contentSql = "insert into pims_entry(platenum,entrytime,parkid) values('"+ platenum+"','"+entrytime +"','"+ parkid +"');";
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
