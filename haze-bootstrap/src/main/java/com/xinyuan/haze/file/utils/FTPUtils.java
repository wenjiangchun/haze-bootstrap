package com.xinyuan.haze.file.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamException;
import org.apache.commons.net.io.CopyStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * FTP工具类
 * <p><b>说明：</b>该工具类提供了FTP上传下载功能。 暂不支持断点续传</p>
 *
 * @author sofar
 */
public final class FTPUtils {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtils.class);

    /*public static final String FTP_IP = "218.206.203.230";
    public static final String USER_NAME = "si447266";
    public static final String PASSWORD = "447266";
    */

    /**
     * 操作系统文件分隔符
     */
    public static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * 正在处理文件后缀名
     */
    public static final String FILE_PROCESSING_SUFFIX = ".tmp";

    /**
     * FTP服务器文件分隔符
     */
    public static final String FTP_FILE_SEPARATOR = "/";

    public static final String FTP_IP = "193.193.193.222";
    public static final String USER_NAME = "wstmms";
    public static final String PASSWORD = "wstmms";

    private static AtomicInteger clientCount = new AtomicInteger(0);

    private static Map<String, FTPClient> ftpClientPools = new ConcurrentHashMap<>();

    /**
     * 正在处理的文件集合, 在上传下载中，将正在处理的文件信息放置该集合中，其它上传下载任务在上传下载前将判断该集合中是否存在
     * 要处理的文件信息。
     */
    private static volatile Set<FTPFile> processingFiles = new HashSet<>();

    private static Map<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * ftpClientPools对象中FTPClient键名称，通过该名称获取ftpClientPools中保存的FTPClient对象。
     */
    private static final String FTP_CLIENT_POOLS_KEY = "ftp_client_key";
    private static  Object flag = new Object();

    private static final MyFTPClient myClient = new MyFTPClient();

    private FTPUtils() {
        throw new UnsupportedOperationException("FTPUtils不能被实例化！");
    }

    /**
     * 从FTP服务器下载文件至本地目录
     *
     * @param localFilePath 本地文件路径
     * @param remoteFilePath 远程文件所在文件夹路径
     */
    public static void downloadFiles(String localFilePath, String remoteFilePath) {
        try {
            FTPClient ftpClient = getFTPClient();
            download(localFilePath, remoteFilePath, ftpClient, BossFTPFileFilter.BOSS_FILE);
        } catch (IOException e) {
            logger.error("从FTP服务器文件夹【{}】下下载文件失败！失败原因【{}】", remoteFilePath,  e.getMessage());
            e.printStackTrace();
        } finally {
            //关闭连接
            closeFTPClient();
        }
    }

    /**
     * 从远程FTP服务器指定目录下载文件。
     * <p>
     *     <b>说明：</b>
     *     1.该方法根据过滤器下载文件夹下所有符合条件的文件和子文件夹中文件。
     *     2.下载文件过程中，将执行以下判断：
     *     <ul>
     *         <li>首先将本地当前目录与远程FTP服务器目录文件对比，如果本地不存在同名文件则开始下载</li>
     *         <li>如果本地当前目录存在同名文件，则分为2种情况：
     *             <ul>
     *                 <li>首先对比本地和远程FTP服务器同名文件大小，大小一致判定为同名文件，这时跳过该文件下载。</li>
     *                 <li>如果本地文件大小小于FTP服务器同名文件，则下载该文件。</li>
     *             </ul>
     *         </li>
     *     </ul>
     *     3.下载过程中使用文件名称+".tmp"在本地存储文件，下载完毕后则重命名文件。
     *     4.下载完毕后删除服务器文件，同时将下载后的本地文件按照文件名日期分类存放。
     * </p>
     * @param localFilePath 本地文件存储目录
     * @param remoteFilePath 远程FTP服务器文件目录
     * @param ftpClient FTP客户端对象
     * @param ftpFileFilter 下载文件时文件过滤器
     * @throws IOException 下载失败抛出该异常
     */
    private static void download(String localFilePath, String remoteFilePath, FTPClient ftpClient, FTPFileFilter ftpFileFilter) throws IOException {
        logger.debug(Thread.currentThread().getName() + "进入DownLoad方法,远程地址：" + remoteFilePath);
            //检查本地文件存储目录是否存在，不存在则创建
            if (!Files.exists(Paths.get(localFilePath))) {
                logger.warn("文件目录【{}】不存在", localFilePath);
                Files.createDirectory(Paths.get(localFilePath));
                logger.debug("已创建文件目录【{}】", localFilePath);
            }

        //只列出远程目录下以"BOSS"开头的文件
        FTPFile[] ftpFiles = ftpClient.mlistDir(remoteFilePath, ftpFileFilter);
        //设置文件传送监听器
        //ftpClient.setCopyStreamListener(FTPUtils.createListener(ftpClient));
        logger.debug(Thread.currentThread().getName() + "准备进ru for 循环，当前文件数量：" + ftpFiles.length);
        for (FTPFile file : ftpFiles) {
            //ftpClient.
            if (file.isDirectory()) { //如果为文件夹则通过递归调用遍历下载文件夹下所有符合条件的文件
                download(localFilePath + FILE_SEPARATOR + file.getName(), remoteFilePath + FTP_FILE_SEPARATOR + file.getName(), ftpClient, ftpFileFilter);
            } else {
                if (fileMap.get(file.getName()) != null && !fileMap.get(file.getName()).equals(Thread.currentThread().getName())) {
                    logger.debug("文件正在被其它线程下载");
                    continue;
                }
                Path p = Paths.get(localFilePath + FileSystems.getDefault().getSeparator() + file.getName());
                //首先判断当前目录下有没有该文件存在，如果有表示已经下载完成或者该文件正在被下载，将跳过该文件
                if (!Files.exists(p)) {
                    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
                        //processingFiles.add(file);
                            fileMap.put(file.getName(), Thread.currentThread().getName());
                        final boolean[] b = {false};
                            if (fileMap.get(file.getName()) != null && fileMap.get(file.getName()).equals(Thread.currentThread().getName())) {
                                logger.debug(Thread.currentThread().getName() + "开始下载全新文件，文件名【{}】", file.getName());
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            b[0] = ftpClient.retrieveFile(remoteFilePath + FTP_FILE_SEPARATOR + file.getName(), out);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                //t.start();
                               // b =
                            }
                            if (b[0]) {
                                fileMap.remove(file.getName(), Thread.currentThread().getName());
                                // processingFiles.remove(file); //从正在处理FTPFile集合中移除文件信息
                                //删除服务器文件
                                //ftpClient.deleteFile(remoteFilePath + "/" + file.getName());
                            }
                    } catch (CopyStreamException e) {
                        logger.error("下载文件【{}】失败！", file.getName());
                        Files.deleteIfExists(Paths.get(localFilePath + FileSystems.getDefault().getSeparator() + file.getName()));
                        throw new IOException(e);
                    }
                } else {
                    //如果存在但长度大小不等于源文件长度大小则有2中可能，一种是正在下载，另外一种是服务器宕机引起的下载未完成。
                    long size = file.getSize();
                    if (size != Files.size(p)) {
                        if (fileMap.get(file.getName()) == null) {
                            try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
                                //processingFiles.add(file);
                                //ftpClient.setRestartOffset(Files.size(p));
                                    fileMap.put(file.getName(), Thread.currentThread().getName());
                                    boolean b = false;
                                    if (fileMap.get(file.getName()) != null && fileMap.get(file.getName()).equals(Thread.currentThread().getName())) {
                                        logger.debug(Thread.currentThread().getName() + "开始下载当即文件文件，文件名【{}】", file.getName());
                                        b = ftpClient.retrieveFile(remoteFilePath + FTP_FILE_SEPARATOR + file.getName(), out);
                                    }
                                    if (b) {
                                        fileMap.remove(file.getName(), Thread.currentThread().getName());
                                        // processingFiles.remove(file); //从正在处理FTPFile集合中移除文件信息
                                        //删除服务器文件
                                        //ftpClient.deleteFile(remoteFilePath + "/" + file.getName());
                                    }

                            } catch (CopyStreamException e) {
                                logger.error("下载文件【{}】失败！", file.getName());
                                Files.deleteIfExists(Paths.get(localFilePath + FileSystems.getDefault().getSeparator() + file.getName()));
                                throw new IOException(e);
                            }
                        } else {
                            logger.debug("文件正在被其它线程下载");
                        }
                    } else {
                        //删除服务器上的文件
                        //ftpClient.deleteFile(remoteFilePath + "/" + file.getName());
                        logger.debug("文件已存在");
                    }
                }
            }
        }
    }

    public static synchronized  void addFile(FTPFile file) {
        processingFiles.add(file);
    }

    public static synchronized void removeFile(FTPFile file) {
        processingFiles.remove(file);
    }
    /**
     * 打开FTPClient链接
     * @return FTPClient
     * @throws IOException 链接失败抛出该异常
     */
    public static FTPClient getFTPClient() throws IOException {
        FTPClient ftpClient = ftpClientPools.get(FTP_CLIENT_POOLS_KEY);
        if (ftpClient == null) {
            ftpClient = new FTPClient();
            ftpClientPools.put(FTP_CLIENT_POOLS_KEY, ftpClient);
        }
        if (!myClient.isConnected()) {
            logger.debug(Thread.currentThread().getName() + "连接FTP服务器【{}】...", FTP_IP);
            myClient.connect(FTP_IP);
            logger.debug(Thread.currentThread().getName() + "已连接FTP服务器【{}】！", FTP_IP);
            logger.debug(Thread.currentThread().getName() +"登陆FTP服务器【{}】，用户名：【{}】, 密码：【{}】", FTP_IP, USER_NAME, PASSWORD);
            myClient.login(USER_NAME, PASSWORD);
            logger.debug(Thread.currentThread().getName() +"登陆成功!");
            myClient.setDataTimeout(10000);
            //设置传送文件头类型
            myClient.setFileType(FTP.BINARY_FILE_TYPE);
        }
        //clientCount += 1;
        clientCount.incrementAndGet();
        return myClient;
    }

    /**
     * 关闭FTPClient
     */
    public static void closeFTPClient() {
            clientCount.decrementAndGet();
            FTPClient ftpClient = ftpClientPools.get(FTP_CLIENT_POOLS_KEY);
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    if (clientCount.get() == 0) {
                        myClient.logout();
                        logger.debug(Thread.currentThread().getName() + "关闭ftpClient连接...");
                        //ftpClient.disconnect();
                        logger.debug(Thread.currentThread().getName() + "已成功关闭ftpClient连接！");
                    }
                } catch (IOException e) {
                    logger.error("FTPClient未能正常关闭！错误原因【{}】", e.getMessage());
                    e.printStackTrace();
                }
            }
    }

    public static void main(String[] args) {
        //FTPUtils.downloadFiles("/home/sofar/下载/ftp", "/447266");
        Thread t1 = new Thread(new FTPThread());
        Thread t2 = new Thread(new FTPThread());
        t1.start();
        t2.start();
        Thread t3 = new Thread(new FTPThread());
        t3.start();
        Thread t4 = new Thread(new FTPThread());
        t4.start();
        Thread t5 = new Thread(new FTPThread());
        t5.start();
    }

    private static CopyStreamListener createListener(final FTPClient ftpClient) {
        return new CopyStreamListener() {
            private long megsTotal = 0;
            //            @Override
            public void bytesTransferred(CopyStreamEvent event) {
                bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
            }

            //            @Override
            public void bytesTransferred(long totalBytesTransferred,
                                         int bytesTransferred, long streamSize) {
                long megs = totalBytesTransferred / 1000000;
                for (long l = megsTotal; l < megs; l++) {
                    //logger.debug("########{}", totalBytesTransferred);
                }
                //ftpClient.setRestartOffset(totalBytesTransferred);
                megsTotal = megs;
            }
        };
    }
}