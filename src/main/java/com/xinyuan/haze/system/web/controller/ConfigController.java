package com.xinyuan.haze.system.web.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.xinyuan.haze.web.utils.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyuan.haze.system.entity.Config;
import com.xinyuan.haze.system.service.ConfigService;
import com.xinyuan.haze.system.utils.ConfigType;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParames;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 系统配置Controller
 *
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/system/config")
public class ConfigController {

	@Autowired
	private ConfigService configService;
		
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		model.addAttribute("configTypes", ConfigType.values());
		return "system/config/configList";
	}
	
	@RequestMapping(value = "search")
	@ResponseBody
	public DataTablePage search(DataTableParames dataTableParames,ServletRequest request) {
		PageRequest p = dataTableParames.getPageRequest();
		Map<String, Object> queryVaribles = dataTableParames.getQueryVairables();
		if (queryVaribles != null && queryVaribles.get("configType") != null) {
			String value = (String) queryVaribles.get("configType");
			queryVaribles.put("configType", ConfigType.valueOf(value));
		}
		Page<Config> configList = this.configService.findPage(p,dataTableParames.getQueryVairables());
		DataTablePage dtp = DataTablePage.generateDataTablePage(configList, dataTableParames);		
		return dtp;
	}
	
	@RequestMapping(value = "add")
	public String add(Model model, ServletRequest request) {
		return "system/config/addConfig";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
	public WebMessage save(Config config, HttpServletRequest request) {
		config.setConfigType(ConfigType.B);
		try {
			this.configService.saveOrUpdate(config);
            return WebMessage.createSuccessWebMessage();
		} catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
		}
		
	}

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable String id, Model model) {
        Config config = this.configService.findById(id);
        model.addAttribute("config", config);
        return "system/config/editConfig";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public WebMessage update(Config config, HttpServletRequest request) {
        try {
            Config g = this.configService.findById(config.getId());
            g.setDescription(config.getDescription());
            g.setValue(config.getValue());
            g.setName(config.getName());
            this.configService.saveOrUpdate(g);
            return WebMessage.createLocaleSuccessWebMessage(RequestContextUtils.getLocale(request));
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage(), RequestContextUtils.getLocale(request));
        }
    }

	@RequestMapping(value = "delete/{ids}")
    @ResponseBody
	public WebMessage delete(@PathVariable("ids") String[] ids, HttpServletRequest request) {
		try {
			this.configService.deleteConfigs(ids);
			return WebMessage.createSuccessWebMessage();
		} catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value="updateConfigValue")
    @ResponseBody 
	public WebMessage updateConfigValue(@RequestParam(value="id")String id,@RequestParam(value="value") String value, HttpServletRequest request) {
        try {
            Config config = this.configService.findById(id);
            config.setValue(value);
            this.configService.updateConfig(config);
            return WebMessage.createLocaleSuccessWebMessage(RequestContextUtils.getLocale(request));
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage(), RequestContextUtils.getLocale(request));
        }
    }
	
	/**
	 * 判断配置代码是否存在
	 * @param code 配置名称
	 * @return 不存在返回 true / 存在返回 false
	 */
	@RequestMapping(value = "notExistCode", method = RequestMethod.POST)
	@ResponseBody
	public Boolean notExistCode(String code) {
		Boolean isExist = this.configService.existCode(code);
		return !isExist;
	}
}
