package com.xinyuan.haze.web;

import org.springframework.web.servlet.view.InternalResourceView;

import java.io.File;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Sofar on 2016/8/19.
 */
public class MyInternalView extends InternalResourceView {
    @Override
    public boolean checkResource(Locale locale) throws Exception {
        URL url = this.getServletContext().getClassLoader().getResource(getUrl());
        File file = new File(url.getFile());
        //File file = new File(this.getServletContext().getRealPath("/") + getUrl());
        return file.exists();// 判断该页面是否存在
    }


}
