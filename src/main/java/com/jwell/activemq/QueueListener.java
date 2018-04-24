package com.jwell.activemq;

import com.jwell.print.factory.PrinterFactory;
import com.jwell.print.service.PrintService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * 监听ActiveMQ消息
 *
 * @author alvinqiu
 * @data 2018/04/23
 */
@Component
public class QueueListener {

    protected static Logger log = Logger.getLogger(PrinterFactory.class);

    @Autowired
    private PrintService printService;

    @JmsListener(destination = "${spring.activemq.destination.dev}")
    public void receive(Message message) throws JMSException {

        log.debug("接收来自MQ消息");

        if (message instanceof MapMessage) {

            MapMessage mapMessage = (MapMessage) message;

            log.debug("接收来自MQ消息, url: " + mapMessage.getString("url"));
            log.debug("接收来自MQ消息, printerKey: " + mapMessage.getString("printerKey"));

            // 调用服务的打印接口
            printService.print(mapMessage.getString("url"), mapMessage.getString("printerKey"));
        } else {
            log.debug("接收来自MQ消息失败, 请检查destination和参数(url/printerKey)");
        }
    }
}
