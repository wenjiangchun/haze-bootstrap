package com.xinyuan.haze.file.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sofar on 14-9-13.
 */
public class MyFTPClient extends FTPClient {

    private Set<FTPFile> downLoadFiles = new HashSet<>();

    private Set<File> uploadFiles = new HashSet<>();

    public synchronized Set<FTPFile> getDownLoadFiles() {
        return downLoadFiles;
    }

    public synchronized void setDownLoadFiles(Set<FTPFile> downLoadFiles) {
        this.downLoadFiles = downLoadFiles;
    }

    public synchronized Set<File> getUploadFiles() {
        return uploadFiles;
    }

    public synchronized void setUploadFiles(Set<File> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public MyFTPClient() {
        super();
    }
}
