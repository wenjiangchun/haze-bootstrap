package com.xinyuan.haze.soa.camel.spring;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * Created by sofar on 15-10-23.
 */
public class MyProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(MyProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        try {

            InputStream body = exchange.getIn().getBody(InputStream.class);
            BufferedReader in = new BufferedReader(new InputStreamReader(body));
            StringBuffer strbf = new StringBuffer("");
            String str = null;
            str = in.readLine();
            while (str != null) {
                logger.debug(str);
                strbf.append(str + " ");
                str = in.readLine();
            }
            Map headers = exchange.getIn().getHeaders();
            headers.forEach((k,v) -> {
                logger.debug( k + " = " + v);
                HttpServletRequest request = (HttpServletRequest) headers.get("CamelHttpServletRequest");
                logger.debug( request.getRemoteAddr());
            });



            //exchange.getOut().setHeader(Exchange.FILE_NAME, "converted.txt");
            // set the output to the file
           // exchange.getOut().setBody(strbf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
