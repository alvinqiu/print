package com.jwell.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author alvinqiu
 * @data 2018/04/23
 */
@Deprecated
@Component
public class QueueSender {
//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

//    @Autowired
//    private Queue queue;

    public void send() {
//        jmsTemplate.send(queue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setString("url", "http://www.gov.cn/zhengce/pdfFile/2018_PDF.pdf");
//                mapMessage.setString("printerKey", "1");
//                return mapMessage;
//            }
//        });

    }
}
