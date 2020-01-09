package com.troy.practice.loganalysis;

import java.io.*;

public class PayNotifyFileUtils {

    public static String appendFileName = "E:\\20190808\\日志数据\\data\\pay_notify_0725.txt";

    public static void main(String[] args) {
        /*for (int i = 15; i <=31 ; i++) {
            String fileName = "E:\\20190808\\日志数据\\logs2.23\\pims.2019-07-"+ i +".log";
            readFileByLines(fileName);
        }
        for (int i = 0; i < 9; i++) {
            String fileName = "E:\\20190808\\日志数据\\logs2.23\\pims.2019-08-0"+ i +".log";
            readFileByLines(fileName);
        }
        for (int i = 15; i <=31 ; i++) {
            String fileName = "E:\\20190808\\日志数据\\logs0.43\\pims.2019-07-"+ i +".log";
            readFileByLines(fileName);
        }
        for (int i = 0; i < 9; i++) {
            String fileName = "E:\\20190808\\日志数据\\logs0.43\\pims.2019-08-0"+ i +".log";
            readFileByLines(fileName);
        }*/

        String fileName = "E:\\20190808\\日志数据\\logs0.43\\pims.2019-07-25.log";
        readFileByLines(fileName);

    }

    /**
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
            String matchStr2 = "支付结果异步通知SuBin结束";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                if (tempString.contains(matchStr2)){
                    System.out.println("line " + line + ": " + tempString );
                    appendMethod(appendFileName, tempString.substring((tempString.indexOf(matchStr2) + matchStr2.length())
                            , tempString.length()) + "\r");
                    /*System.out.println(tempString.substring((tempString.indexOf(matchStr2) + matchStr2.length())
                            , tempString.length()) );*/
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
