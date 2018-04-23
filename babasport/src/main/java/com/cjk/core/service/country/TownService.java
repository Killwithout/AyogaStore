package com.cjk.core.service.country;

import java.util.List;

import cn.itcast.common.page.Pagination;
import com.cjk.core.bean.country.Town;
import com.cjk.core.query.country.TownQuery;

/**
 * 县/区
 * @author cjk
 */
public interface TownService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addTown(Town town);

	/**
	 * 根据主键查询
	 */
	public Town getTownByKey(Integer id);

	/**
	 * 根据主键批量查询
	 */
	public List<Town> getTownsByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateTownByKey(Town town);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param townQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getTownListWithPage(TownQuery townQuery);

	/**
	 * 根据条件查询
	 * 
	 * @param townQuery
	 *            查询条件
	 * @return
	 */
	public List<Town> getTownList(TownQuery townQuery);
}
