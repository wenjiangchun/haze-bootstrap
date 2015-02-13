package com.xinyuan.haze.demo.camel;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;


public class BossFTPDownLoadFilter<T> implements GenericFileFilter<T> {

    private String filePrefix;

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public boolean accept(GenericFile<T> file) {
//        return file.getFileNameOnly().startsWith("BOSSSI");
        return file.getFileNameOnly().startsWith(filePrefix);
    }
}
