package com.xinyuan.haze.system.web.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.xinyuan.haze.demo.task.MatrixDemo;
import com.xinyuan.haze.freemarker.FreemarkerUtils;
import com.xinyuan.haze.system.entity.Resource;
import com.xinyuan.haze.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 系统辅助操作Controller
 * @author Sofar
 *
 */
@Controller
@RequestMapping(value = "/system/support")
public class SupportController {
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		return "system/support/supportList";
	}
	
	@RequestMapping(value = "refreshMenus")
	@ResponseBody
	public Boolean refreshMenus(ServletRequest request, RedirectAttributes redirectAttributes) {
       /* List<Resource> menuResources = resourceService.findMenuResources();
        Map<String, Object> map = new HashMap<>();
        map.put("menuResources", menuResources);
        map.put("ctx", "${ctx}");
        //获取左边菜单模板所在文件路径
        String filePath = request.getServletContext().getRealPath("WEB-INF/layouts");
        FreemarkerUtils.generateTemplate(filePath,"left.ftl", filePath + "/left1.jsp", map);*/
		createMatrix();
        return true;
	}

	/**
	 * 创建二维码
	 */
	public void createMatrix() {
		String text = "http://www.baidu.com";
		int width = 300;
		int height = 300;
		//二维码的图片格式
		String format = "gif";
		Hashtable hints = new Hashtable();
		//内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
					BarcodeFormat.QR_CODE, width, height, hints);
			//生成二维码
			File outputFile = new File("d:"+File.separator+"new.gif");
			MatrixDemo.writeToFile(bitMatrix, format, outputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
