package com.cjk.core.service.user;

import java.util.List;

import cn.itcast.common.page.Pagination;
import com.cjk.core.bean.user.Addr;
import com.cjk.core.query.user.AddrQuery;

/**
 * 
 * @author cjk
 */
public interface AddrService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addAddr(Addr addr);

	/**
	 * 根据主键查询
	 */
	public Addr getAddrByKey(Integer id);

	/**
	 * 根据主键批量查询
	 */
	public List<Addr> getAddrsByKeys(List<Integer> idList);

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
	public Integer updateAddrByKey(Addr addr);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param addrQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getAddrListWithPage(AddrQuery addrQuery);

	/**
	 * 根据条件查询
	 * 
	 * @param addrQuery
	 *            查询条件
	 * @return
	 */
	public List<Addr> getAddrList(AddrQuery addrQuery);
}
