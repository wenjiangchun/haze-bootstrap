package com.xinyuan.haze.demo.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xinyuan.haze.qrcode.QrCodeUtils;
import com.xinyuan.haze.qrcode.common.LogoConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

/**
 * Created by Sofar on 2015/1/28.
 */
@RequestMapping(value = "/demo")
@Controller
public class DemoController {

    @RequestMapping(value = "/matrix/view")
    public String viewMatrix(Model model) {
        return "demo/matrix";
    }

    /**
     * 二维码预览
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/showMatrix")
    public ResponseEntity<byte[]> previewPath(@RequestParam String text, HttpServletRequest request,
                                              HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE));
            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "jpeg";
            // 用于设置QR二维码参数
            Hashtable<EncodeHintType, Object> qrParam = new Hashtable<>();
            // 设置QR二维码的纠错级别——这里选择最高H级别
            qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 设置编码方式
            qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                        BarcodeFormat.QR_CODE, width, height, qrParam);
                //生成二维码
                File outputFile = new File("d:"+File.separator+"new.jpeg");
            QrCodeUtils.writeToFile(bitMatrix, format, outputFile);
            QrCodeUtils.addLogo(outputFile, new File("d:\\1.jpg"), new LogoConfig());
            return new ResponseEntity<byte[]>(Files.readAllBytes(Paths.get(outputFile.getPath())),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
            String message = e.getMessage();
            message = "该文件不支持预览";
            try {
                return new ResponseEntity<byte[]>(message.getBytes(),
                        headers, HttpStatus.OK);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
}
