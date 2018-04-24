package com.jwell.print.config;

import com.jwell.print.entity.PrinterEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author alvinqiu
 * @data 2018/04/23
 */
@Component
@ConfigurationProperties(prefix = "listMap")
public class PrinterConfig {

    private List<PrinterEntity> printerList;

    public List<PrinterEntity> getPrinterList() {
        return printerList;
    }

    public void setPrinterList(List<PrinterEntity> printerList) {
        this.printerList = printerList;
    }
}
