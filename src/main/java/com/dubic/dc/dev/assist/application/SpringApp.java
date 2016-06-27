package com.dubic.dc.dev.assist.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dubic
 */
@SpringBootApplication(scanBasePackages = "com.dubic.dc.dev.assist")
public class SpringApp {
    
//    @Bean
//    public HttpMessageConverters customConverters() {
//        HttpMessageConverter<GsonMessageConverter> additional = new GsonMessageConverter();
//        return new HttpMessageConverters(additional);
//    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringApp.class, args);
    }
}
