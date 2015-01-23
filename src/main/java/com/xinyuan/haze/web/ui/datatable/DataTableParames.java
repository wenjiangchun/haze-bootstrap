package com.xinyuan.haze.web.ui.datatable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

public class DataTableParames {

	private String sEcho ;

    /// 过滤文本
	private String sSearch ;

    /// 每页显示的数量
	private int iDisplayLength ;

    /// 分页时每页跨度数量
	private int iDisplayStart ;

    /// 列数
	private int[] iColumns ;

    /// 排序列的数量
	private int iSortingCols;

  
	private String[] sColumns ;
	
	/**
	 * 排序列值
	 */
	private int iSortCol_0;
	
	/**
	 * 排序字符串 "asc" or "desc"
	 */
	private String sSortDir_0;
	
	/**
	 * 自定义查询参数
	 */
	private Map<String, Object> queryVairables = new HashMap<String, Object>();

	
	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int[] getiColumns() {
		return iColumns;
	}

	public void setiColumns(int[] iColumns) {
		this.iColumns = iColumns;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String[] getsColumns() {
		return sColumns;
	}

	public void setsColumns(String[] sColumns) {
		this.sColumns = sColumns;
	}

	public int getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public Map<String, Object> getQueryVairables() {
		return queryVairables;
	}

	public void setQueryVairables(Map<String, Object> queryVairables) {
		this.queryVairables = queryVairables;
	}

	/**
	 * 根据参数对象获取查询分页对象
	 * @return PageRequest
	 */
	public PageRequest getPageRequest() {
		/*String[] columns = sColumns[0].split(",");
		String sortColunmName = columns[iSortCol_0];*/
		Sort sort = new Sort(Sort.Direction.fromString(sSortDir_0),sColumns[iSortCol_0]);
		return new PageRequest(iDisplayStart/iDisplayLength, iDisplayLength, sort);
	}
}
