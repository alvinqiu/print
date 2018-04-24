package com.jwell.print.factory;

import com.google.gson.JsonObject;
import com.jwell.print.entity.PrinterEntity;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;

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

    static final String GIF = "gif";

    static final String PNG = "png";

    static final String JPG = "jpg";

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
    public static PrintService getPrinter(PrinterEntity printerEntity, JsonObject result) {
        PrintService printer = null;
        if (null != services && null != printerEntity) {
            String svcName;
            for (int i = 0; i < services.length; i++) {
                svcName = services[i].toString();
                if (svcName.indexOf(":") != -1) {
                    svcName = svcName.split(":")[1].trim();
                }
                if (svcName.equals(printerEntity.getPrinterName())) {
                    printer = services[i];

                    log.debug(printerEntity.toString());
                    result.addProperty("info", printerEntity.toString());
                    break;
                }
            }
        } else {
            // 使用默认打印机
            printer = PrintServiceLookup.lookupDefaultPrintService();

            log.debug("使用了默认打印机");
            result.addProperty("info", "使用了默认打印机");
            return printer;
        }

        return printer;
    }

    /**
     * 打印PDF
     *
     * @param printerEntity
     * @param inputStream
     * @throws Exception
     */
    public static void printPDF(PrinterEntity printerEntity, FileInputStream inputStream, JsonObject result) throws Exception {
        // 获取打印机
        PrintService printer = PrinterFactory.getPrinter(printerEntity, result);

        PDDocument document = PDDocument.load(inputStream);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(printer);
        job.print(pras);
        log.debug("打印开始");
    }

    /**
     * 打印二维码图片
     *
     * @param printerName
     * @param inputStream
     * @throws Exception
     */
    public static void printCode(String printerName, String fileName, FileInputStream inputStream) throws Exception {

        try {
            DocFlavor dof = null;
            if (fileName.endsWith(GIF)) {
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(JPG)) {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(PNG)) {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }

            PrintService ps = PrintServiceLookup.lookupDefaultPrintService();

            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(OrientationRequested.PORTRAIT);

            pras.add(new Copies(1));
            pras.add(PrintQuality.HIGH);
            DocAttributeSet das = new HashDocAttributeSet();

            // 设置打印纸张的大小（以毫米为单位）
            das.add(new MediaPrintableArea(0, 0, 210, 296, MediaPrintableArea.MM));

            Doc doc = new SimpleDoc(inputStream, dof, das);

            DocPrintJob job = ps.createPrintJob();

            job.print(doc, pras);
        } finally {
            inputStream.close();
            log.debug("inputStream 关闭");
        }


        log.debug("打印开始");
    }
}
