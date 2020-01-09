package com.troy.practice.loganalysis;

import java.io.*;

public class SqlPay {

    public static String appendSqlFile = "E:\\20190808\\日志数据\\sql\\sql_pay_0725.sql" ;

    public static void main(String[] args) {
        String fileName = "E:\\20190808\\日志数据\\data\\pay_notify_0725.txt";
        readFileByLines(fileName);
    }

    /*
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            String sql = "";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);

                String[] temps = tempString.split(",");
                String paymentOrderId = temps[0].split("=")[1].trim();
                String carNo = temps[1].split("=")[1] .trim();
                String notifyStatus = temps[2].split("=")[1].trim();
                if (!"".equals(paymentOrderId) && !"".equals(carNo) && !"".equals(notifyStatus)){
                    sql = "INSERT INTO pims_pay(paymentOrderId,carNo,notifyStatus) VALUES('"+ paymentOrderId
                            +"','"+ carNo
                            +"','"+ notifyStatus
                            +"');";
                    System.out.println("line " + line + ": " + sql);
                    appendMethod(appendSqlFile, sql + "\r");
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
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
