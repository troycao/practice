package com.troy.practice.loganalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class AnalysisV2 {

    public static String contentFileName  = "E:\\20190808\\日志数据\\sql\\parking_order_info_v2.sql";
    public static String questionFileName  = "E:\\20190808\\日志数据\\sql\\questionFileName_v2.sql";

    public static void main(String[] args) {

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        ResultSet res = null;
        Connection conn = null;
        CallableStatement proc = null;
        String sql = "select a.id,a.create_time,a.total_amount,a.pay_time,b.carno from PIMS_PAYMENT_ORDER_0808 a left join pims_pay b on a.id=b.paymentorderid  " +
                "where a.status='S' and a.notify_status='S' and a.update_time >='2019-07-15 00:00:00' and a.update_time <='2019-08-09 00:00:00' " +
                "and a.m_id in ('854290065138998', '854290065138191', '854290065138999') order by a.pay_time";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            while(res.next())
            {
                String id = res.getString("id");
                String create_time = res.getString("create_time");
                double total_amount = res.getDouble("total_amount");
                String pay_time = res.getString("pay_time");
                String carno = res.getString("carno");

                getParkingOrder(id,create_time,total_amount,pay_time,carno);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getParkingOrder(String id,String create_time,double total_amount,String pay_time,String carno){
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
        Statement stmt = null;
        ResultSet res = null;
        Connection conn = null;
        CallableStatement proc = null;

        String getOrderSql = "select * from (select * from PIMS_ORDER where platenum='" + carno + "' \n" +
                "and exittime>=to_char(to_date('"+ pay_time +"', 'yyyy-mm-dd hh24:mi:ss')-3/24/60, 'yyyy-mm-dd hh24:mi:ss')\n" +
                "and exittime<=to_char(to_date('"+ pay_time +"', 'yyyy-mm-dd hh24:mi:ss') + 20/24/60, 'yyyy-mm-dd hh24:mi:ss')\n" +
                "order by exittime ) a where rownum<=1";
        System.out.println(getOrderSql);

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "pims", "Oracle1234!");
            stmt = conn.createStatement();
            res = stmt.executeQuery(getOrderSql);
            while (res.next()){

                String platenum = res.getString("platenum");
                String parkid = res.getString("parkid");
                String entrytime = res.getString("entrytime");
                String exittime = res.getString("exittime");

                System.out.println("platenum:" + platenum + " entrytime:" + entrytime + " parkid:" + parkid
                        + " exittime:" + exittime + " paymentid:" + id + " totalamount:" + total_amount
                        + " paytime:" + pay_time);

                String sql = "insert into parking_order_info(platenum,parkid,entrytime,exittime,paymentorderid,payamount,paytime) " +
                        "values('"+ platenum +"','"+ parkid +"','"+ entrytime +"','"+ exittime +"','"+ id +"','"+ total_amount +"','"+
                        create_time +"');";
                System.out.println(sql);

                appendMethod(contentFileName, sql + "\r");
            }

            String question = "insert into question_parking_order_info(platenum,paytime) values('"+ carno +"','"+ create_time +"');";
            appendMethod1(questionFileName, question + "\r");

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

    public static void appendMethod1(String fileName, String content) {
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
