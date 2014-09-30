package com.xinyuan.haze.file.utils;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * 文件实体类
 * <p><b>说明：</b>在文件管理功能中，使用NIO操作文件系统，将读取到的文件系统信息使用{@code FileEntity}封装并和前台交互。</p>
 * @author Sofar
 */
public class FileEntity {

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件类型
     */
    private FileType fileType;

    /**
     * 文件内容类型
     */
    private String contentType;

    /**
     * 当文件类型为文件夹时，该文件夹下所有文件集合
     */
    private Set<FileEntity> fileEntitySet = new HashSet<>();

    /**
     * 文件绝对路径
     */
    private String absolutePath;

    /**
     * 文件或文件夹是否隐藏
     */
    private boolean isHidden;

    /**
     * 上级文件路径
     */
    private String parentPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Set<FileEntity> getFileEntitySet() {
        return fileEntitySet;
    }

    public void setFileEntitySet(Set<FileEntity> fileEntitySet) {
        this.fileEntitySet = fileEntitySet;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public static FileEntity fromPath(Path path) {
        FileEntity entity = new FileEntity();
        try {
            entity.setName(path.getFileName().toString());
            if (Files.isDirectory(path)) {
                entity.setFileType(FileType.DIRECTORY);
                long size = FileSystems.getDefault().provider().getFileStore(path).getUnallocatedSpace();
                entity.setSize(FileSystems.getDefault().provider().getFileStore(path).getTotalSpace());
            } else {
                entity.setFileType(FileType.FILE);
               // String nameExt = FilenameUtils.getExtension(path.getFileName().toString());
               // entity.setContentType(FilenameUtils.getExtension(path.getFileName().toString()));
                entity.setSize(Files.size(path)/1024+1);
            }
            //根据文件路径信息获取文件图标
            entity.setContentType(FileTypeIconUtils.getIcon(path));
            if (path.getParent() != null) {
                entity.setParentPath(formatPathSeperator(path.getParent().toAbsolutePath().toString()));
            }
            entity.setAbsolutePath(formatPathSeperator(path.toAbsolutePath().toString()));
            entity.setHidden(Files.isHidden(path));
            Map<String,Object> map  = Files.readAttributes(path,"*");
            map.forEach((k,v) -> {
                System.out.println(k + " = " + v);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 格式化文件路径
     * @param path
     * @return
     */
    private static String formatPathSeperator(String path) {
        if (path == null) {
            return null;
        }
        path = path.replaceAll("\\\\","\\\\\\\\");
        return path;
    }
}
