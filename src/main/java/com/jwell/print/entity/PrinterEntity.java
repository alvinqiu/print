package com.jwell.print.entity;

import org.springframework.stereotype.Component;

/**
 * 打印机实体
 *
 * @author alvinqiu
 * @data 2018/04/23
 */
@Component
public class PrinterEntity {

    /**
     * 机组线 key
     */
    private String key;

    /**
     * 打印机名称
     */
    private String printerName;

    /**
     * 打印机位置
     */
    private String address;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "打印机信息 --- 机组线: " +
                key + ", 打印机名称: " +
                printerName + ", 打印机位置: " +
                address;
    }
}
