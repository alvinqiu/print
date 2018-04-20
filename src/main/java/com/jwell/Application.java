package com.jwell;

import com.jwell.print.factory.PrinterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring boot start
 *
 * @author alvinqiu
 * @data 2018/04/18
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        PrinterFactory.initial();
        SpringApplication.run(Application.class, args);
    }
}