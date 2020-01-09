package com.troy.practice.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestlocalDateTime {

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime1 = LocalDateTime.of(2015,10,10,10,10,10);
        System.out.println(localDateTime1);

        LocalDateTime plus = localDateTime.plusDays(1);
        System.out.println(plus);

    }
}
