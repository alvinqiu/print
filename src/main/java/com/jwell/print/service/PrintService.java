package com.jwell.print.service;

import com.google.gson.JsonObject;
import com.jwell.print.factory.PrinterFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 打印服务
 *
 * @author alvinqiu
 * @date 2018/04/18
 */
@Service
public class PrintService {

    /**
     * pdf文件存放路径
     */
    @Value("${path.temp}")
    private String tempPath;

    /**
     * 二维码文件存放路径
     */
    @Value("${path.codeTemp}")
    private String codeTempPath;

    protected static Logger log = Logger.getLogger(PrintService.class);

    private static final String MSG = "msg";

    public String print(String pdfUrl) {

        JsonObject result = new JsonObject();

        if (StringUtils.isEmpty(pdfUrl)) {
            log.debug("url地址不能为空");
            result.addProperty(MSG, "url地址不能为空");
            return result.toString();
        } else {
            HttpURLConnection conn = null;
            InputStream inputStream = null;

            try {
                URL url = new URL(pdfUrl);

                conn = (HttpURLConnection) url.openConnection();

                // 设定请求的方法为"POST"
                conn.setRequestMethod("POST");

                // Post 请求不能使用缓存
                conn.setUseCaches(false);

                // 设置超时间为5秒
                conn.setConnectTimeout(5000);

                // 防止403错误
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                // 得到输入流
                inputStream = conn.getInputStream();

                // 保存文件
                String fileName = saveFile(inputStream);

                // 执行打印(选择默认打印机)
                PrinterFactory.printPDF(null, new FileInputStream(fileName));

                log.debug(pdfUrl + " 打印成功");
                result.addProperty(MSG, pdfUrl + " 打印成功");
                return result.toString();
            } catch (Exception e) {
                log.debug("打印失败, 信息如下: " + e);
                result.addProperty(MSG, pdfUrl + " 打印失败");
                return result.toString();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                        log.debug("InputStream关闭成功");
                    }
                    if (conn != null) {
                        conn.disconnect();
                        log.debug("HttpURLConnection关闭成功");
                    }
                } catch (IOException e) {
                    log.debug("关闭InputStream/HttpURLConnection失败: " + e.getMessage());
                }
            }
        }

    }

    /**
     * 保存文件
     *
     * @param inputStream
     * @throws IOException
     */
    public String saveFile(InputStream inputStream) throws IOException {
        log.debug("保存文件中");

        byte[] getData = readInputStream(inputStream);

        File saveDir = new File(tempPath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        String fileName = saveDir + File.separator + System.currentTimeMillis();

        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);

        if (fos != null) {
            fos.close();
            log.debug("FileOutputStream 已关闭");
        }

        log.debug("保存文件成功");
        return fileName;
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
