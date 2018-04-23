package com.cjk.core.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjk.common.web.session.SessionProvider;
import com.cjk.core.bean.BuyCar;
import com.cjk.core.bean.BuyItem;
import com.cjk.core.bean.order.Order;
import com.cjk.core.bean.product.Sku;
import com.cjk.core.bean.user.Buyer;
import com.cjk.core.service.order.OrderService;
import com.cjk.core.service.product.SkuService;
import com.cjk.core.web.Constants;

/**
 * 提交订单
 * @author cjk
 *
 */
@Controller
public class FrontOrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired 
	private SessionProvider sessionProvider;
	@Autowired
	private SkuService skuService;
	
	//提交订单
	@RequestMapping(value = "/buyer/confirmOrder.shtml")
	public String confirmOrder(Order order,HttpServletRequest request,HttpServletResponse response){
		//接受前台的4个参数
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		
		//声明一个购物车
		BuyCar buyCar = null;
		//先要判断cookie中是否有购物车，若有，使用此购物车，没有则创建购物车
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length > 0){
			for(Cookie c : cookies){
				if(Constants.BUYCAR_COOKIE.equals(c.getName())){
					String value = c.getValue();
					//json转对象是    读 
					try {
						buyCar = om.readValue(value.toString(), BuyCar.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		//将购物车装满，展示购物车页面  , 将购物项装满，也就是将里面的sku装满，其他不需要装   装到对象的引用
		List<BuyItem> its = buyCar.getItems();
		for(BuyItem item : its){
			Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
			item.setSku(skuByKey);
		}
		
		Buyer buyer = (Buyer)sessionProvider.getAttribute(request, Constants.BUYER_SESSION);
		//用户id
		order.setBuyerId(buyer.getUsername());
		//保存订单和订单详情两张表
		orderService.addOrder(order,buyCar);
		//清空购物车
		Cookie cookie = new Cookie(Constants.BUYCAR_COOKIE,null); 
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "product/confirmOrder";
	}
	
}
