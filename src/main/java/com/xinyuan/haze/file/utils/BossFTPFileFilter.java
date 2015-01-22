package com.xinyuan.haze.file.utils;

import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;

/**
 * 移动Boss FTP文件过滤器
 * <p>
 *     在下载Boss FTP报文时需要根据文件名称过滤报文信息，该类扩展{@link org.apache.commons.net.ftp.FTPFileFilters},增加
 *     过滤文件名称以{@code "jdk"}开头的文件或文件夹。
 * </p>
 * @author sofar
 * @version 3.0
 * @see org.apache.commons.net.ftp.FTPFileFilters
 */
public class BossFTPFileFilter extends FTPFileFilters {

    /**
     * 过滤当前目录下以“jdk”开头的文件或文件夹
     */
    public static final FTPFileFilter BOSS_FILE = (file) -> {
        return file != null && file.getName().startsWith("2014");
    };
}
