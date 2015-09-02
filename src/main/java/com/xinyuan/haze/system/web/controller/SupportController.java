package com.xinyuan.haze.system.web.controller;

import com.xinyuan.haze.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;

/**
 * 系统辅助操作Controller
 *
 * @author Sofar
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
        return true;
    }
}
