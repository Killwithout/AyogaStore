package com.cjk.core.service.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import com.cjk.core.bean.order.Detail;
import com.cjk.core.dao.order.DetailDao;
import com.cjk.core.query.order.DetailQuery;
/**
 * 订单子项(订单详情)
 * @author cjk
 */
@Service
@Transactional
public class DetailServiceImpl implements DetailService {

	@Resource
	DetailDao detailDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addDetail(Detail detail) {
		return detailDao.addDetail(detail);
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Detail getDetailByKey(Integer id) {
		return detailDao.getDetailByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Detail> getDetailsByKeys(List<Integer> idList) {
		return detailDao.getDetailsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return detailDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return detailDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateDetailByKey(Detail detail) {
		return detailDao.updateDetailByKey(detail);
	}
	
	@Transactional(readOnly = true)
	public Pagination getDetailListWithPage(DetailQuery detailQuery) {
		Pagination p = new Pagination(detailQuery.getPageNo(),detailQuery.getPageSize(),detailDao.getDetailListCount(detailQuery));
		p.setList(detailDao.getDetailListWithPage(detailQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Detail> getDetailList(DetailQuery detailQuery) {
		return detailDao.getDetailList(detailQuery);
	}
}
