package com.xinyuan.haze.file.utils;

import com.xinyuan.haze.common.utils.HazeStringUtils;
import com.xinyuan.haze.common.utils.OSUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOExceptionWithCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.nio.file.FileSystem;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件管理器工具类
 * Created by Sofar on 2014/7/27.
 */
public final class FileManager {

    private static final long GB = 1024 * 1024 * 1024;

    private static  final Logger logger = LoggerFactory.getLogger(FileManager.class);

    /**
     * 文件系统分割符，取值与底层操作系统。
     */
    public static final String FILE_SEPARATOR = OSUtils.FILE_SEPARATOR;

    private FileManager() {
       throw new UnsupportedOperationException();
    }

    public static void getFileSystem1() {
        FileSystem fs = FileSystems.getDefault();
        fs.getFileStores().forEach(s -> {
        });
        for (FileStore store : fs.getFileStores())
            try {
                long total = store.getTotalSpace() / GB;
                long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / GB;
                long avail = store.getUsableSpace() / GB;
                Path p = fs.getPath(store.toString());
                Stream<Path> sp = Files.list(p);
                sp.forEach(s -> {
                    s.getFileName();
                });
                sp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * 获取默认文件系统
     *
     * @return 文件系统对象
     */
    public static FileSystem getDefaultFileSystem() {
        return FileSystems.getDefault();
    }

    /**
     * 根据文件节点路径获取节点路径下所有文件和文件夹
     *
     * @param parentPath 节点路径
     * @return fileList
     * @throws java.io.IOException
     */
    public static List<FileEntity> getSubPath(String parentPath) throws IOException {
        Path p = getDefaultFileSystem().getPath(parentPath);
        Stream<Path> sp = Files.list(p);
        List<FileEntity> fileList = new ArrayList<>();
        sp.forEach(s -> {
            if (Files.isReadable(s)) {
                fileList.add(FileEntity.fromPath(s));
            }
        });
        return fileList;
    }

    /**
     * 根据文件目录获取文件所在上级目录下所有文件和文件夹信息
     *
     * @param childrenPath 子目录路径信息
     * @return 上级目录下所有文件和文件夹信息
     * @throws java.io.IOException 路径错误抛出该异常
     */
    public static List<FileEntity> getParentPath(String childrenPath) throws IOException {
        Path p = getDefaultFileSystem().getPath(childrenPath);
        if (p == null || p.getParent() == null) {
            throw new FileSystemException("文件不存在或文件上级目录不存在");
        }
        Stream<Path> sp = Files.list(p.getParent());
        List<FileEntity> fileList = new ArrayList<>();
        sp.forEach(s -> {
            fileList.add(FileEntity.fromPath(s));
        });
        return fileList;
    }

    /**
     * 创建文件或者文件夹信息
     *
     * @param fileEntity 文件实体对象
     * @return 创建成功后的Path对象
     * @throws java.io.IOException 创建失败抛出该异常
     * @see java.nio.file.Path
     * @see java.nio.file.Files
     */
    public static Path createPath(FileEntity fileEntity) throws IOException {
        if (HazeStringUtils.isEmpty(fileEntity.getParentPath())) {
            throw new FileSystemException("创建失败，文件系统父节点不能为空！");
        }
        //获取要创建文件所在文件夹路径信息
        Path parentPath = getDefaultFileSystem().getPath(fileEntity.getParentPath());
        //创建文件Path信息
        Path path = getDefaultFileSystem().getPath(fileEntity.getParentPath() + FILE_SEPARATOR + fileEntity.getName());
        if (!Files.isWritable(parentPath)) {
            throw new FileSystemException("创建失败，文件系统父节点不能写入！");
        }
        if (Files.exists(path)) {
            throw new FileAlreadyExistsException("创建失败，文件系统已存在，请重新命名！");
        }

        if (fileEntity.getFileType() == FileType.DIRECTORY) { //创建文件夹
            return Files.createDirectory(path);
        } else { //创建文件
            return Files.createFile(path);
        }
    }

    /**
     * 将文件路径中的文件或文件夹压缩成zip格式。
     * <p>
     * <ul>
     * <li>压缩文件名称为文件名.zip或文件夹名称.zip</li>
     * <li>如果需要压缩的是文件夹，则将该压缩后的zip文件存放到该文件夹下，即压缩后的文件路径信息为{@code path + FILE_SEPARATOR} + 压缩文件名称</li>
     * </ul>
     * </p>
     *
     * @param path 要压缩的文件或文件夹路径信息
     * @return 压缩后的文件路径信息
     * @throws Exception 压缩失败抛出该异常
     */
    public static Path toZIP(String path) throws Exception {
        if (HazeStringUtils.isEmpty(path)) {
            throw new FileSystemException("文件路径信息不能为空！");
        }
        Path p = getDefaultFileSystem().getPath(path);
        if (!Files.exists(p)) {
            throw new FileNotFoundException("文件路径不存在！");
        }
        //压缩文件名称
        String zipFileName = p.getFileName().toString() + ".zip";
        //压缩的文件或文件夹对象
        File file = getDefaultFileSystem().getPath(path).toFile();
        String zipFilePath = file.getAbsolutePath();
        if (Files.isDirectory(p)) {
            zipFilePath += FILE_SEPARATOR + zipFileName;
        } else {
            zipFilePath = p.getParent().toAbsolutePath() + FILE_SEPARATOR + zipFileName;
        }
        try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(Paths.get(zipFilePath)));
             BufferedOutputStream bo = new BufferedOutputStream(out);) {
            compress(out, file, zipFileName, bo);
        }
        return Paths.get(zipFilePath);
    }

    /**
     * 压缩文件或文件夹至指定输出流
     *
     * @param out  zip输出流
     * @param f    要压缩的文件或文件夹对象
     * @param base 压缩文件名
     * @param bo   buffer输出流
     * @throws Exception
     */
    private static void compress(ZipOutputStream out, File f, String base,
                                 BufferedOutputStream bo) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                if (!fl[i].getName().equals(f.getName() + ".zip")) {
                    compress(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            try (FileInputStream in = new FileInputStream(f);
                 BufferedInputStream bi = new BufferedInputStream(in);) {
                int b;
                while ((b = bi.read()) != -1) {
                    bo.write(b); // 将字节流写入当前zip目录
                }
            }
        }
    }

    /**
     * 重命名文件夹或文件名称
     * @param path 原文件夹或文件路径信息
     * @param newFileName 命名后的文件夹或文件名称
     * @return Path 命名后的文件夹或文件名称路径对象
     * @throws java.io.IOException 命名失败抛出该异常
     */
    public static Path renamePath(String path, String newFileName) throws IOException {
        if (HazeStringUtils.isEmpty(path)) {
            throw new FileSystemException("文件或文件夹路径信息不能为空！");
        }
        if (HazeStringUtils.isEmpty(newFileName)) {
            throw new FileSystemException("文件或文件夹名称不能为空！");
        }

        Path p = Paths.get(path);
        Path parentPath = p.getParent();
        if (parentPath == null) {
            throw new FileSystemException("顶级文件路径不能被重命名！");
        } else {
            //获取命名后的文件路径信息
            Path newPath = Paths.get(parentPath.toAbsolutePath() + FILE_SEPARATOR + newFileName);
            if (!Files.isDirectory(p)) {
                //获取文件扩展名
                String fileExt = FilenameUtils.getExtension(path);
                newPath = Paths.get(parentPath.toAbsolutePath() + FILE_SEPARATOR + newFileName + FilenameUtils.EXTENSION_SEPARATOR + fileExt);
            }
            if (Files.exists(newPath)) {
                throw new FileAlreadyExistsException("文件或文件夹名称【" + newPath + "】已存在！");
            }
            Files.move(p, newPath);
            return newPath;
        }
    }

    /**
     * 删除文件夹或文件
     * @param path 文件夹或文件所在路径信息
     * @return 删除成功返回true,否则返回false
     * @throws java.io.IOException 删除失败抛出该异常
     */
    public static boolean deletePath(String path) throws IOException {
        logger.debug("准备删除{}文件", path);
        Path p = Paths.get(path);
        if (!Files.exists(p)) {
        	logger.debug("路径{}文件不存在", path);
        	return false;
        }
        Files.walkFileTree(p, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;
                }
            }
        });
        return true;
    }

    public static void main(String[] args) {
        try {
           // FileManager.toZIP("c:\\apache-maven-3.2.2");
            //FileManager.decompressFile("/home/sofar/下载/apache-maven-3.2.2-bin.tar.gz");

           /* OSUtils.OS_PROPERTIES_MAP.put("os.name","aaa");
            OSUtils.OS_PROPERTIES_MAP.forEach((k,v)-> {
                logger.debug("key={}, value={}", k, v);
            });*/

            /*String path = "/home/sofar/111.doc";
            System.out.println(Files.probeContentType(Paths.get(path)));
            System.out.println(DocumentManager.readWord(Paths.get(path)));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(Path path) throws IOException {
        if (!Files.isReadable(path)) {
            throw new IOException("文件不存在或不可读, Path=" + path);
        }
        if (Files.isDirectory(path)) {
            throw new IOException("当前路径为文件夹, Path=" + path);
        }
        return FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
    }
}
