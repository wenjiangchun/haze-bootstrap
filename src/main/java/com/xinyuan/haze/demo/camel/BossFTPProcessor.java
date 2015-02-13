package com.xinyuan.haze.demo.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

/**
 * Created by Soo on 2014/11/26.
 */
public class BossFTPProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String filePath = exchange.getIn().getHeader(Exchange.FILE_PATH, String.class);
        exchange.getIn().setBody(new File(filePath));
    }
}
