package com.xinyuan.haze.file.utils;

/**
 * Created by sofar on 14-9-12.
 */
public class FTPThread implements Runnable {
    @Override
    public void run() {
        System.out.println("当前线程" + Thread.currentThread().getName());
        FTPUtils.downloadFiles("/home/sofar/下载/ftp", "/447266/ftp");
    }
}
