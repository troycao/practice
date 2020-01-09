package com.troy.practice.loganalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class AnalysisV3 {

    public static String contentFileName  = "E:\\20190808\\日志数据\\sql\\parking_order_info_v3.sql";
    public static String questionFileName  = "E:\\20190808\\日志数据\\sql\\questionFileName_v3.sql";

    public static void main(String[] args) {
        String getPlateNumSql = "select distinct(platenum) from pims_entry where entrytime >='2019-07-15 00:00:00'";
        String countPayOrderSql = "";
        String countOrderSql = "";
        String entrySql = "";
        String exitSql = "";
        ResultSet rs = JdbcUtil.executeSql(getPlateNumSql,null);
        try {
            while (rs.next()){
                String platenum = rs.getString("PLATENUM");
                countPayOrderSql = "select count(a.id) as payCount from PIMS_PAYMENT_ORDER_0808 a left join pims_pay b on a.id=b.paymentorderid\n" +
                        "where a.status='S' and a.notify_status='S' and a.update_time >='2019-07-15 00:00:00' and a.update_time <='2019-08-09 00:00:00'\n" +
                        "and a.m_id in ('854290065138998', '854290065138191', '854290065138999') and b.carno='"+ platenum +"'  order by a.pay_time";
                ResultSet countPayRs = JdbcUtil.executeSql(countPayOrderSql,null);

                countOrderSql = "select count(*) as cuntOrder from pims_order";
                ResultSet countOrderRs = JdbcUtil.executeSql(countOrderSql,null);

                Integer payCount = 0;
                Integer orderCount = 0;
                Integer entryCount = 0;
                Integer exitCount = 0;
                while (countPayRs.next()){
                    payCount = countPayRs.getInt("payCount");
                }

                while (countOrderRs.next()){
                    orderCount = countOrderRs.getInt("cuntOrder");
                }

                entrySql = "select count(*) as entryCount from pims_entry where entrytime>='2019-07-15 00:00:00'";
                ResultSet entryRs = JdbcUtil.executeSql(entrySql,null);
                while (entryRs.next()){
                    entryCount = entryRs.getInt("entryCount");
                }

                exitSql = "select count(*) as exitCount entryCount from pims_exit where entrytime>='2019-07-15 00:00:00'";
                ResultSet exitRs = JdbcUtil.executeSql(exitSql,null);
                while (exitRs.next()){
                    exitCount = entryRs.getInt("exitCount");
                }

                if (entryCount == exitCount){
                    System.out.println("platenum:" + platenum +"进出场数据完整");
                }else{
                    System.out.println("platenum:" + platenum +"进出场数据不完整");
                }

                if (entryCount == orderCount){
                    System.out.println("platenum:" + platenum +"进场数据等于计算的order订单数据");
                }else{
                    System.out.println("platenum:" + platenum +"进场数据等于不等于计算的order订单数据,需要重新计算");
                }

                if (payCount == orderCount){
                    System.out.println("platenum:" + platenum +"数据完整");
                }else {
                    System.out.println("platenum:" + platenum +"数据不完整");
                }

            }
        }catch (Exception e){
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
