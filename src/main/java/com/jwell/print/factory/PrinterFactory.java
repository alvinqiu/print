package com.jwell.print.factory;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 打印机容器获取，项目启动时静态加载获取当前电脑所有打印机服务
 *
 * @author alvinqiu
 * @date 2018/04/18
 */
public class PrinterFactory {

    protected static Logger log = Logger.getLogger(PrinterFactory.class);

    static PrintService[] services;

    static PrintRequestAttributeSet pras;

    static DocAttributeSet das;

    /**
     * 初始化获取打印服务对象
     */
    public static void initial() {
        // 设置打印机设置
        pras = new HashPrintRequestAttributeSet();

        // 查找打印机服务
        services = PrintServiceLookup.lookupPrintServices(null, null);
    }

    /**
     * 获取打印机对象
     */
    public static PrintService getPrinter(String printerName) {
        PrintService printer = null;
        if (null != services && null != printerName) {
            for (int i = 0; i < services.length; i++) {
                log.debug("打印机设备" + i + ": " + services[i]);
                String svcName = services[i].toString();
                if (svcName.equals(printerName)) {
                    printer = services[i];
                    break;
                }
            }
        } else {
            // 使用默认打印机
            printer = PrintServiceLookup.lookupDefaultPrintService();
            log.debug("使用的打印机设备: " + printer);
            return printer;
        }
        return printer;
    }

    /**
     * 打印PDF
     *
     * @param printerName
     * @param inputStream
     * @throws Exception
     */
    public static void printPDF(String printerName, FileInputStream inputStream) throws Exception {
        // 获取打印机
        PrintService printer = PrinterFactory.getPrinter(printerName);

        PDDocument document = PDDocument.load(inputStream);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(printer);
        job.print(pras);
        log.debug("打印开始");
    }
}
