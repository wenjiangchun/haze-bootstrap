package com.xinyuan.haze.soa.jms;

import javax.jms.Message;

/**
 * Created by sofar on 15-12-4.
 */
public class JmsMessage  {

    private String key;

    private String srcStr;

    private String destStr;

    private String content;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSrcStr() {
        return srcStr;
    }

    public void setSrcStr(String srcStr) {
        this.srcStr = srcStr;
    }

    public String getDestStr() {
        return destStr;
    }

    public void setDestStr(String destStr) {
        this.destStr = destStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
