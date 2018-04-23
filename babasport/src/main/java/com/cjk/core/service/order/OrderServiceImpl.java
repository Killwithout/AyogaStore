package com.cjk.core.service.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;

import com.cjk.core.bean.BuyCar;
import com.cjk.core.bean.BuyItem;
import com.cjk.core.bean.order.Detail;
import com.cjk.core.bean.order.Order;
import com.cjk.core.dao.order.OrderDao;
import com.cjk.core.query.order.OrderQuery;
/**
 * 订单主
 * @author cjk
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Resource
	OrderDao orderDao;
	@Resource
	DetailService detailService;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addOrder(Order order,BuyCar buyCar) {
		//生成订单号
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String oid = df.format(new Date());
		order.setOid(oid);
		//运费
		order.setDeliverFee(buyCar.getFee());
		//应付金额
		order.setPayableFee(buyCar.getTotalPrice());
		//订单金额
		order.setTotalPrice(buyCar.getProductPrice());
		//支付状态
		if(order.getPaymentWay()==0){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
		}
		//订单状态   提交订单
		order.setState(0);
		//订单生成时间
		order.setCreateDate(new Date());
		//保存订单
		Integer Orders = orderDao.addOrder(order);
		//购物项集合
		List<BuyItem> items = buyCar.getItems();
		
		for(BuyItem item : items){
			//保存订单详情  List
			Detail detail = new Detail();
			//设置一个订单id
			detail.setOrderId(order.getId());
			//items.sku.product.name
			//商品名称
			detail.setProductName(item.getSku().getProduct().getName());
			//商品编号
			detail.setProductNo(item.getSku().getProduct().getNo());
			//颜色名称
			detail.setColor(item.getSku().getColor().getName());
			//尺码
			detail.setSize(item.getSku().getSize());
			//售价
			detail.setSkuPrice(item.getSku().getSkuPrice());
			//购买数量
			detail.setAmount(item.getAmount());
			//保存
			detailService.addDetail(detail);
		}
		return Orders;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Order getOrderByKey(Integer id) {
		return orderDao.getOrderByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrdersByKeys(List<Integer> idList) {
		return orderDao.getOrdersByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return orderDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return orderDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order order) {
		return orderDao.updateOrderByKey(order);
	}
	
	@Transactional(readOnly = true)
	public Pagination getOrderListWithPage(OrderQuery orderQuery) {
		Pagination p = new Pagination(orderQuery.getPageNo(),orderQuery.getPageSize(),orderDao.getOrderListCount(orderQuery));
		p.setList(orderDao.getOrderListWithPage(orderQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrderList(OrderQuery orderQuery) {
		return orderDao.getOrderList(orderQuery);
	}
}
