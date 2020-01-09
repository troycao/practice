package com.troy.practice.spring.config;

import com.troy.practice.spring.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    public Color color(){
        return new Color();
    }

}