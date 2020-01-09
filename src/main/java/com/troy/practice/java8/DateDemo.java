package com.troy.practice.java8;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateDemo {

    public static void main(String[] args) {
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now().plusHours(-2));
        System.out.println(format);

        String localTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now().plusHours(-2));
        System.out.println(localTime);

        String price = String.format("%.1f", Math.random());
        System.out.println(price);

        String soldId = String.format("%.0f",Math.random()*100000);
        System.out.println(soldId);
    }
}
