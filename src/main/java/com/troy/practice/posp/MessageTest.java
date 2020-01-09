package com.troy.practice.posp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageTest {

    public static void main(String[] args) {
        String message = "A210501002040202700001504P1001000000F66666100300600007504008F846508405008320003  06010121.43090407009+31.19421";
        Map<String,String> messageMap = new HashMap<String, String>();
        if(message.length() >= 5){
            String usage = message.substring(0,2);
            System.out.println(message.substring(2,5));
            Integer messageLength = Integer.parseInt(message.substring(2,5));

            if ("A2".equals(usage)){
                String subMsg = message.substring(5);

                while (subMsg.length()>0){
                    String type = subMsg.substring(0,2);
                    Integer typeLength = Integer.parseInt(subMsg.substring(2,5));
                    String typeValue = subMsg.substring(5, 5+typeLength);

                    messageMap.put(type,typeValue);
                    subMsg = subMsg.substring(5+typeLength);
                }
            }else{
                System.out.println("非A2用法");
            }
        }

        System.out.println(messageMap.get("06"));
        System.out.println(messageMap.get("07"));
    }
}
