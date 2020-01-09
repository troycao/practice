package com.troy.practice.loganalysis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.*;

public class SqlExit {

    public static String appendSqlFile = "E:\\20190808\\日志数据\\sql\\sql_exit.txt";

    public static void main(String[] args) {
        String fileName = "E:\\20190808\\日志数据\\data\\exit.txt";
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

                JSONObject object = (JSONObject) JSONObject.parse(tempString);
                //System.out.println(object.getJSONObject("data").get("PlateNum"));
                String plateNum = (String) object.getJSONObject("data").get("PlateNum");
                String exitTime = (String) object.getJSONObject("data").get("ExitTime");
                String data =  (String) object.getJSONObject("data").get("data");
                if (!"".equals(data) && !StringUtils.isEmpty(data)){
                    JSONObject dataObject = (JSONObject) JSONObject.parse(data);
                    if (dataObject != null){
                        String parkId = (String) dataObject.get("parkid");
                        String parkname = (String) dataObject.get("parkname");

                        sql = "INSERT INTO pims_exit(platenum,exittime,parkId,parkname) VALUES('"+ plateNum
                                +"','"+ exitTime
                                +"','"+ parkId
                                +"','"+ parkname +"');";
                        System.out.println("line " + line + ": " + sql);
                        appendMethod(appendSqlFile, sql + "\r");
                    }
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
