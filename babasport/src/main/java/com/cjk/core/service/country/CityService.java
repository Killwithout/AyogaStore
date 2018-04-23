package com.cjk.core.service.country;

import java.util.List;

import cn.itcast.common.page.Pagination;
import com.cjk.core.bean.country.City;
import com.cjk.core.query.country.CityQuery;

/**
 * city
 * @author cjk
 */
public interface CityService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addCity(City city);

	/**
	 * 根据主键查询
	 */
	public City getCityByKey(Integer id);

	/**
	 * 根据主键批量查询
	 */
	public List<City> getCitysByKeys(List<Integer> idList);

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
	public Integer updateCityByKey(City city);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param cityQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getCityListWithPage(CityQuery cityQuery);

	/**
	 * 根据条件查询
	 * 
	 * @param cityQuery
	 *            查询条件
	 * @return
	 */
	public List<City> getCityList(CityQuery cityQuery);
}
