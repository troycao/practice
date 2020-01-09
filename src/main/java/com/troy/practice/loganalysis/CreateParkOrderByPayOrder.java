package com.troy.practice.loganalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateParkOrderByPayOrder {

    public static String contentFileName  = "E:\\20190808\\日志数据\\sql\\v4\\parking_order_info_0808.sql";
    public static String questionFileName  = "E:\\20190808\\日志数据\\sql\\v4\\questionFileName_0808.sql";

    public static void main(String[] args) {
        String sql = "select a.id,a.create_time,a.pay_amount,a.pay_time,b.carno from PIMS_PAYMENT_ORDER_0808 a left join pims_pay b on a.id=b.paymentorderid  " +
                "where a.status='S' and a.notify_status='S' and a.create_time >='2019-08-08 00:00:00' and a.create_time <='2019-08-08 23:59:59' " +
                "and a.m_id in ('854290065138998', '854290065138191', '854290065138999') order by a.pay_time";

        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(sql);
        for (Map<String, Object> map:list){
            String id = (String) map.get("ID");
            String create_time = (String) map.get("CREATE_TIME");
            double total_amount = Double.parseDouble(map.get("PAY_AMOUNT").toString());
            String pay_time = (String) map.get("PAY_TIME");
            String carno = (String) map.get("CARNO");

            getParkingOrder(id,create_time,total_amount,pay_time,carno);
        }
    }

    public static void getParkingOrder(String id,String create_time,double pay_amount,String pay_time,String carno){
        String getOrderSql = "select * from (select * from PIMS_ORDER where platenum=? \n" +
                "and exittime>=to_char(to_date(?, 'yyyy-mm-dd hh24:mi:ss') - 10/24/60, 'yyyy-mm-dd hh24:mi:ss')\n" +
                "and exittime<=to_char(to_date(?, 'yyyy-mm-dd hh24:mi:ss') + 20/24/60, 'yyyy-mm-dd hh24:mi:ss')\n" +
                "order by exittime ) a where rownum<=1";
        System.out.println(getOrderSql);

        List<Map<String, Object>> list = DruidJdbcUtils.executeQuery(getOrderSql, carno,create_time,create_time);
        for (Map<String, Object> map:list){
            String platenum = (String) map.get("PLATENUM");
            String parkid = (String) map.get("PARKID");
            String entrytime = (String) map.get("ENTRYTIME");
            String exittime = (String) map.get("EXITTIME");

            System.out.println("platenum:" + platenum + " entrytime:" + entrytime + " parkid:" + parkid
                    + " exittime:" + exittime + " paymentid:" + id + " payamount:" + pay_amount
                    + " paytime:" + pay_time);

            String sql = "insert into parking_order_info(platenum,parkid,entrytime,exittime,paymentorderid,payamount,paytime) " +
                    "values('"+ platenum +"','"+ parkid +"','"+ entrytime +"','"+ exittime +"','"+ id +"','"+ pay_amount +"','"+
                    create_time +"');";
            System.out.println(sql);

            appendMethod(contentFileName, sql + "\r");
        }

        String question = "insert into question_parking_order_info(platenum,paytime) values('"+ carno +"','"+ create_time +"');";
        appendMethod1(questionFileName, question + "\r");

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
