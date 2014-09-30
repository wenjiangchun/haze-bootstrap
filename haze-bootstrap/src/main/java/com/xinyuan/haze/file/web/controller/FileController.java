package com.xinyuan.haze.file.web.controller;

import com.xinyuan.haze.file.utils.FileEntity;
import com.xinyuan.haze.file.utils.FileManager;
import com.xinyuan.haze.web.utils.WebMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件管理 Controller
 * 
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    private static final long GB = 1024*1024*1024;

	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, HttpServletResponse response) {
        FileSystem fs = FileManager.getDefaultFileSystem();
        for (FileStore store: fs.getFileStores()) {
            try {
                long total = store.getTotalSpace() / GB;
                long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / GB;
                long avail = store.getUsableSpace() / GB;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("fileStores", fs.getFileStores());
        List<Path> pathList = new ArrayList<>();
        fs.getRootDirectories().forEach(path -> {
            pathList.add(path);
        });
        request.setAttribute("pathList", pathList);
		return "file/fileList";
	}
	
	@RequestMapping(value = "/getSubFile", method = RequestMethod.POST)
	@ResponseBody
	public List<FileEntity> getSubFile(String path, HttpServletRequest request,
			HttpServletResponse response) {
        try {
            return FileManager.getSubPath(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getParentFile", method = RequestMethod.POST)
    @ResponseBody
    public List<FileEntity> getParentFile(String path, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            return FileManager.getParentPath(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/addCreateDirectory", method = RequestMethod.GET)
    public String addCreateDirectory(String parentPath, Model model) {
        model.addAttribute("parentPath", parentPath);
        return "file/addDirectory";
    }

    @RequestMapping(value = "/addCreateFile", method = RequestMethod.GET)
    public String addCreateFile(String parentPath, Model model) {
        model.addAttribute("parentPath", parentPath);
        return "file/addFile";
    }

    @RequestMapping(value = "/createPath")
    @ResponseBody
    public WebMessage createPath(FileEntity fileEntity, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
             FileManager.createPath(fileEntity);
             return WebMessage.createSuccessWebMessage();
        } catch (IOException e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }

    /**
     * 下载文件或者文件夹，如果是文件夹则先压缩再下载，下载完成后删除压缩后的文件。
     * @param parentPath 文件或文件夹所在父目录
     * @param fileName 文件名称
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downLoadPath")
    public ResponseEntity<byte[]> downLoadPath(String parentPath, String fileName, HttpServletRequest request,
                             HttpServletResponse response) {
        String path = parentPath + FileManager.FILE_SEPARATOR + fileName;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            if (Files.isDirectory(Paths.get(path))) { //如果是文件夹则先压缩再下载
                Path p = FileManager.toZIP(path);
                headers.setContentDispositionFormData("attachment", p.getFileName().toString());
                return new ResponseEntity<byte[]>(Files.readAllBytes(p),
                        headers, HttpStatus.CREATED);
            } else { //如果是文件则直接下载
                headers.setContentDispositionFormData("attachment", Paths.get(path).getFileName().toString());
                return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(path)),
                        headers, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/renamePath")
     @ResponseBody
     public WebMessage renamePath(String path, String newFileName, HttpServletRequest request,
                                  HttpServletResponse response) {
        try {
            FileManager.renamePath(path, newFileName);
            return WebMessage.createSuccessWebMessage();
        } catch (IOException e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }

    @RequestMapping(value = "/deletePath")
    @ResponseBody
    public WebMessage deletePath(String path, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            FileManager.deletePath(path);
            return WebMessage.createSuccessWebMessage();
        } catch (IOException e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
    }

    /**
     * 文件预览
     * @param path 文件路径信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/previewPath")
    public ResponseEntity<byte[]> previewPath(String path, HttpServletRequest request,
                                 HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.valueOf(Files.probeContentType(Paths.get(path))));
            return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(path)),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
            String message = e.getMessage();
            message = "该文件不支持预览";
            try {
                return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(path)),
                        headers, HttpStatus.OK);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
}
