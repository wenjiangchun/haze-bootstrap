package com.xinyuan.haze.demo.camel;

import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;

public class BossFTPUpLoadFilter<T> implements GenericFileFilter<T> {
    public boolean accept(GenericFile<T> file) {
        return file.getFileNameOnly().startsWith("SIBOSS");
    }
}
