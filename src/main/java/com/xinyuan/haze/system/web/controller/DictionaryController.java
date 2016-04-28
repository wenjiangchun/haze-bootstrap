package com.xinyuan.haze.system.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xinyuan.haze.system.entity.Dictionary;
import com.xinyuan.haze.system.service.DictionaryService;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParams;
import com.xinyuan.haze.web.utils.WebMessage;


/**
 * 字典对象Controller
 * 
 * @author sofar
 */
@Controller
@RequestMapping(value = "/system/dictionary")
public class DictionaryController {

	@Autowired
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		String parentId = request.getParameter("parentId");
		if(StringUtils.isNotBlank(parentId)){
			model.addAttribute("parentId", parentId);
		}
		return "system/dictionary/dictionaryList";
	}
	
	@RequestMapping(value = "search")
	@ResponseBody
	public DataTablePage search(DataTableParams dataTableParams, ServletRequest request) {
		PageRequest p = dataTableParams.getPageRequest(); //根据dataTableParames对象获取JPA分页查询使用的PageRequest对象
		Map<String, Object> map = dataTableParams.getQueryVairables();
		if(map.get("parent") != null){ //查询parent字典下面的所有子字典列表
			Dictionary d = new Dictionary();
			d.setId((String) map.get("parent"));
			map.put("parent", d);
		} else {
			map.put("parent_isNull", null); //默认查询顶级字典列表
		}
		if ( map.get("isEnabled") != null) {
			String value = (String) map.get("isEnabled");
			map.put("isEnabled", Boolean.valueOf(value));
		}
		Page<Dictionary> dictionaryList = this.dictionaryService.findPage(p,map); 
		DataTablePage dtp = DataTablePage.generateDataTablePage(dictionaryList, dataTableParams); //将查询结果封装成前台使用的DataTablePage对象
		return dtp;
	}
	
	@RequestMapping(value = "getDictionaryTree")
	@ResponseBody
	public List<Dictionary> getDictionaryTree(ServletRequest request, ServletResponse response) {
		List<Dictionary> dictionarys =  dictionaryService.findAll();
		Set<Dictionary> newDictionary = new HashSet<Dictionary>();
		Dictionary root = new Dictionary();
		root.setName("字典树");
		for (Dictionary g : dictionarys) {
			g.setChilds(null);
			if (g.getPid() == null) {
				g.setParent(root);
			}
			newDictionary.add(g);
		}
		newDictionary.add(root);
		if(dictionarys.size() > 0) {
			newDictionary.add(root);
		}
		return new ArrayList<Dictionary>(newDictionary);
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(@RequestParam(value="parentId",required=false) String parentId, Model model, ServletRequest request) {
		if (parentId != null) {
			Dictionary parent = this.dictionaryService.findById(parentId);
			model.addAttribute("parent", parent);
		}
		return "system/dictionary/addDictionary";
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public WebMessage save(Dictionary dictionary, ServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			this.dictionaryService.saveOrUpdate(dictionary);
			return WebMessage.createSuccessWebMessage();
		} catch (Exception e) {
			return WebMessage.createErrorWebMessage(e.getMessage());
		}
	}
	
	@RequestMapping(value = "delete/{ids}")
	@ResponseBody
	public WebMessage delete(@PathVariable("ids") String[] ids, ServletRequest request) {
		try {
            this.dictionaryService.batchDelete(ids);
            return WebMessage.createSuccessWebMessage();
        } catch (Exception e) {
            return WebMessage.createErrorWebMessage(e.getMessage());
        }
	}
	
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id, Model model, ServletRequest request) {
		Dictionary dictionary = this.dictionaryService.findById(id);
		model.addAttribute("dictionary", dictionary);
		return "system/dictionary/editDictionary";
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public WebMessage update(Dictionary dictionary, ServletRequest request) {
		try {
			this.dictionaryService.saveOrUpdate(dictionary);
			return WebMessage.createSuccessWebMessage();
		} catch (Exception e) {
			return WebMessage.createErrorWebMessage(e.getMessage());
		}
	}
	
	/**
	 * 设置字典启用状态
	 * @param id 字典Id
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "updateDictionaryIsEnabled/{id}", method = RequestMethod.GET)
	public String updateDictionaryIsEnabled(@PathVariable String id, ServletRequest request, RedirectAttributes redirectAttributes) {
		Dictionary dictionary = this.dictionaryService.findById(id);
		this.dictionaryService.updateDictionaryIsEnabled(dictionary, !dictionary.getIsEnabled());
		if (dictionary.getParent() != null) {
			redirectAttributes.addFlashAttribute("parentId", dictionary.getParent().getId());
		}
		return "redirect:/system/dictionary/view";
	}
	
	/**
	 * 判断字典代码是否存在  
	 * @param parentId 字典所在节点Id
	 * @param code 字典代码
	 * @return true/false
	 */
	@RequestMapping(value = "isNotExistCode", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isNotExistCode(@RequestParam(required=false)String parentId, String code) {
		String pa = null;
		String[] codes = code.split(",");
		if (codes.length >= 2) {
			pa = codes[1];
		}
		Boolean isExist = this.dictionaryService.isExistDictionaryCode(pa, codes[0]);
		return !isExist;
	}
	
	/**
	 * 根据代码名称获取代码字典中所有子字典
	 * @param codeName 字典代码名称
	 * @return List<Dictionary>
	 */
	@RequestMapping(value = "getChilds/{codeName}", method = RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getChilds(@PathVariable String codeName) {
		return this.dictionaryService.findChildsByRootCode(codeName);
	}
}
