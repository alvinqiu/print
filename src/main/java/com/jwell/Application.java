package com.jwell;

import com.jwell.print.factory.PrinterFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


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

//    @Bean
//    public ActiveMQConnectionFactory connectionFactory() {
//        return new ActiveMQConnectionFactory();
//    }

//    @Bean
//    public Queue queue() {
//        return new ActiveMQQueue("print_queue");
//    }
//
//    @Bean
//    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory) {
//        System.out.println("get JmsMessagingTemplate");
//        return new JmsMessagingTemplate(connectionFactory);
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
//        System.out.println("get JmsTemplate");
//        return new JmsTemplate(connectionFactory);
//    }
}