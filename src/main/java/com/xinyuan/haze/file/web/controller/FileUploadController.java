package com.xinyuan.haze.file.web.controller;

import com.xinyuan.haze.file.utils.FileManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传 Controller
 * 
 */
@Controller
@RequestMapping(value = "/file")
public class FileUploadController {

	@RequestMapping(value = "upload")
	public String view(HttpServletRequest request, HttpServletResponse response) {
		return "file/fileUpload";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(MultipartHttpServletRequest request,
			HttpServletResponse response) {
       int n = request.getFiles("file").size();
       //throw new UnsupportedOperationException("n=" + n);
        request.getFiles("file").forEach(f -> {
        });
        try {
            request.getFiles("file").get(0).transferTo(new File("/home/sofar/" + request.getFiles("file").get(0).getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
