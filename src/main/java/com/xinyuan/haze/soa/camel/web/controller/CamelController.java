package com.xinyuan.haze.soa.camel.web.controller;

import com.xinyuan.haze.soa.camel.entity.MyRoute;
import com.xinyuan.haze.soa.camel.service.MyRouteService;
import com.xinyuan.haze.web.ui.datatable.DataTablePage;
import com.xinyuan.haze.web.ui.datatable.DataTableParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * camel Controller
 * @author sofar
 *
 */
@Controller
@RequestMapping(value = "/soa/camel")
public class CamelController {

	@Autowired
	private MyRouteService myRouteService;
	
	@RequestMapping(value = "view")
	public String list(Model model, ServletRequest request) {
		return "soa/camel/camelList";
	}
	
	/**
	 * 根据查询参数查询用户列表分页对象
	 * @param dataTableParams 包含分页对象和自定义查询对象的参数,其中PageSize
	 * @param request
	 * @return DataTablePage 前台DataTable组件使用的分页数据对象
	 */
	@RequestMapping(value = "search")
    @ResponseBody
	public DataTablePage search(DataTableParams dataTableParams, ServletRequest request) {
		PageRequest p = dataTableParams.getPageRequest(); //根据dataTableParames对象获取JPA分页查询使用的PageRequest对象
		Map<String, Object> queryVairables = dataTableParams.getQueryVairables(); //获取自定义查询参数
		Page<MyRoute> userList = this.myRouteService.findPage(p, queryVairables); //过滤掉"admin"对象
		DataTablePage dtp = DataTablePage.generateDataTablePage(userList, dataTableParams); //将查询结果封装成前台使用的DataTablePage对象
		return dtp;
	}


}
