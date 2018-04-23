package com.cjk.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjk.core.bean.order.Detail;
import com.cjk.core.bean.order.Order;
import com.cjk.core.bean.user.Addr;
import com.cjk.core.query.order.DetailQuery;
import com.cjk.core.query.order.OrderQuery;
import com.cjk.core.query.user.AddrQuery;
import com.cjk.core.service.order.DetailService;
import com.cjk.core.service.order.OrderService;
import com.cjk.core.service.user.AddrService;

/**
 * 订单列表
 * 订单查看
 * @author cjk
 *
 */
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private DetailService detailService;
	@Autowired
	private AddrService addrService;

	//订单列表
	//支付状态
	//订单状态
	@RequestMapping(value = "/order/list.do")
	public String list(Integer isPaiy , Integer state ,ModelMap model){
		
		OrderQuery orderQuery = new OrderQuery();
		//支付状态
		if(null != isPaiy){
			orderQuery.setIsPaiy(isPaiy);
		}		
		//订单状态
		if(null != state){
			orderQuery.setState(state);
		}
		
		List<Order> orderList = orderService.getOrderList(orderQuery);
		
		model.addAttribute("orders",orderList);
		
		return "order/list";
	}
	
	/**
	 * 订单查看
	 */
	@RequestMapping(value = "/order/view.do")
	public String view(Integer id , ModelMap model){
		
		//查询订单主表
		Order order = orderService.getOrderByKey(id);
		//查询订单详情表
		DetailQuery detailQuery = new DetailQuery();
		detailQuery.setOrderId(id);
		List<Detail> details = detailService.getDetailList(detailQuery);
		
		//查看收货地址
		AddrQuery addrQuery = new AddrQuery();
		addrQuery.setBuyerId(order.getBuyerId());
		addrQuery.setIsDef(1);
		
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		
		//
		model.addAttribute("order",order);
		model.addAttribute("addr",addrs.get(0));
		model.addAttribute("details",details);
		
		return "order/view";
	}




}
