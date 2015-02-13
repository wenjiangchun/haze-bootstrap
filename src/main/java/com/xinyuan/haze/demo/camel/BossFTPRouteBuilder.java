package com.xinyuan.haze.demo.camel;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by Soo on 2014/11/19.
 */
public class BossFTPRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        //from("file:d:/temp/inbox?delay=30000").to("file:d:/temp/outbox");
        //from("ftp://wstmms@193.193.193.222/447266/ftp?password=wstmms&delete=true&delay=30000&binary=true&bufferSize=10240&ftpClient.controlEncoding=utf-8").to("file:/tmp/outbox?bufferSize=10240");
        //from("file:d:/421bak/?delay=10000&tempFileName=tmp").to("ftp://wstmms@193.193.193.222/447266/ftp?password=wstmms&ftpClient.controlEncoding=utf-8&binary=true&tempPrefix=SIBOSS${file:name}&localWorkDirectory=d:/temp");
       // from("file:d:/421bak/?delay=10000&filter=#bossFilter").to("ftp://wstmms@193.193.193.222/447266/ftp?password=wstmms&binary=true&tempPrefix=SIBOSS&bufferSize=1024");
    }
}
