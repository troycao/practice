package com.troy.practice.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GaodeMapApi {

    public Map getLocation(String address) throws IOException {
        String url = String.valueOf(new StringBuffer("https://restapi.amap.com/v3/geocode/geo?output=json&key=5dc296e29a80c286e50c9f11a868acda&address=").append(address));
        URL resturl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) resturl.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
        reader.close();
        httpConn.disconnect();
        String status = jsonObject.getString("status");
        String info = jsonObject.getString("info");
        String infocode = jsonObject.getString("infocode");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status", status);
        map.put("info", info);
        map.put("infocode", infocode);
        if ("1".equals(status)) {
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            JSONObject object = jsonArray.getJSONObject(0);
            String location = object.getString("location");
            String[] str = location.split(",");
            //经度
            String longitude = str[0];
            //纬度
            String latitude = str[1];
            map.put("location", location);
            map.put("longitude", longitude);
            map.put("latitude", latitude);
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\data\\address.txt");
        InputStreamReader reader=new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader bfreader=new BufferedReader(reader);
        String line;

        File f2=new File("E:\\data\\detail.txt");
        PrintWriter printWriter =new PrintWriter(new FileWriter(f2,true),true);//第二个参数为true，从文件末尾写入 为false则从开头写入


        while((line=bfreader.readLine())!=null) {//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
            System.out.println(line);
            //String address = "上海市漕宝路3299号";

            GaodeMapApi gaodediApi = new GaodeMapApi();
            Map map = gaodediApi.getLocation(line);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(map);

            printWriter.println(line + "   " + map.get("longitude") + "   " + map.get("latitude") +"\t");
        }
        printWriter.close();//记得关闭输入流
    }
}
