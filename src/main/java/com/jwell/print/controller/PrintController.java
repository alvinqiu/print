package com.jwell.print.controller;

import com.jwell.print.service.PrintService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 打印服务接口
 *
 * @author alvinqiu
 * @date 2018/04/18
 */
@RestController
@RequestMapping(path = "/print")
public class PrintController {

    protected static Logger log = Logger.getLogger(PrintController.class);

    @Autowired
    private PrintService printService;

    @RequestMapping(value = "/pdf", method = RequestMethod.POST)
    @ResponseBody
    public String print(@RequestParam("pdfUrl") String pdfUrl, @RequestParam("printerKey") String printerKey) {
        log.debug("即将开始打印，打印的url地址是：" + pdfUrl);

        // 调用服务的打印接口
        return printService.print(pdfUrl, printerKey);
    }


    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public String printCode(@RequestParam("code") String code) {
        log.debug("即将开始打印二维码，打印的二维码内容是：" + code);

        return printService.printCode(code);
    }


    final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int RUN_TIMES = 5;
    private static final String PDF_URL = "http://www.gov.cn/zhengce/pdfFile/2018_PDF.pdf";
    private static final String PRINTER_KEY = "1";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @RequestMapping(value = "/test")
    @ResponseBody
    public void testPrint() {
        for (int i = 0; i < RUN_TIMES; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(this.getName() + "并发执行打印开始" + SIMPLE_DATE_FORMAT.format(new Date()));
                    printService.print(PDF_URL, PRINTER_KEY);
                    System.out.println(this.getName() + "并发执行打印结束" + SIMPLE_DATE_FORMAT.format(new Date()));
                }
            }.start();
            countDownLatch.countDown();
        }
        System.out.println("所有模拟请求结束---" + SIMPLE_DATE_FORMAT.format(new Date()));
    }
}
