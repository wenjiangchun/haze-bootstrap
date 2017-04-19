package com.xinyuan.haze.demo.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xinyuan.haze.core.spring.utils.SpringContextUtils;
import com.xinyuan.haze.demo.drools.Access;
import com.xinyuan.haze.qrcode.QrCodeUtils;
import com.xinyuan.haze.qrcode.common.LogoConfig;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import java.util.Collection;
import java.util.Hashtable;

@RequestMapping(value = "/map")
@Controller
public class MapController {

    @RequestMapping(value = "/view")
    public String viewMatrix(Model model) {
        return "demo/map";
    }
}
