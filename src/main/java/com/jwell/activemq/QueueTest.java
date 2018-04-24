package com.jwell.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试ActiveMQ
 *
 * @author alvinqiu
 * @data 2018/04/23
 */
@Deprecated
@RestController
public class QueueTest {

    @Autowired
    private QueueSender queueSender;

    @RequestMapping("test")
    public void test() {
        queueSender.send();
    }
}
