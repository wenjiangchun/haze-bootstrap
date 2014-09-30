package com.xinyuan.haze.file.utils;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件类型图标工具类
 *
 * @author sofar
 */
public final class FileTypeIconUtils {


    public static final Map<String, String> iconMap = new HashMap<>();

    public static final String DEFAULT_FOLDER = "defaultFolder";

    public static final String DEFAULT_FILE = "defaultFile";

    static {
        iconMap.put("jpg","picture");
        iconMap.put("jpeg","picture");
        iconMap.put("txt","text");
        iconMap.put("pdf","pdf");
        iconMap.put("xml","xml");
        iconMap.put("htm","html32");
        iconMap.put("html","html32");
        iconMap.put("doc","word");
        iconMap.put("docx","word");
        iconMap.put("ppt","ppt");
        iconMap.put("pptx","pptx");
        iconMap.put("xls","excel");
        iconMap.put("xlsx","excel");
        iconMap.put("pptx","pptx");
        iconMap.put("avi","video");
        iconMap.put("rmvb","video");
        iconMap.put("mkv","video");
        iconMap.put("3gp","video");
        iconMap.put("mp4","video");
        iconMap.put("iso","cd");
        iconMap.put("js","script");
        iconMap.put("zip","zip");
        iconMap.put("rar","zip");
        iconMap.put("java","java");
        iconMap.put("sql","sql");
        iconMap.put(DEFAULT_FILE,"file");
        iconMap.put(DEFAULT_FOLDER,"folder");
    }

    public FileTypeIconUtils() {
        throw new UnsupportedOperationException("FileContentTypeUtils不能被实例化！");
    }

    /**
     * 根据文件路径对象获取文件类型对应的图标信息
     * <p>
     *     <ul>
     *         <li>如果文件路径对象为文件夹路径，则返回默认文件夹图标</li>
     *         <li>如果文件路径对象为单个文件路径，则根据文件后缀名返回文件夹图标，
     *             <b>注意：</b>因为文件名后缀有可能为大写，所以获取到的文件后缀需要转化为小写。
     *         </li>
     *     </ul>
     * </p>
     * @param path 文件路径信息
     * @return 文件路径信息对应文件类型图标
     */
    public static String getIcon(Path path) {
        if (Files.isDirectory(path)) {
            return iconMap.get(DEFAULT_FOLDER);
        } else {
            //根据文件路径信息获取文件扩展名并转化为小写
            String nameExt = FilenameUtils.getExtension(path.getFileName().toString()).toLowerCase();
            return iconMap.containsKey(nameExt) ? iconMap.get(nameExt) : iconMap.get(DEFAULT_FILE);
        }
    }
}
