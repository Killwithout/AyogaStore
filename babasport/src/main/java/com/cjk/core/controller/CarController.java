package com.cjk.core.controller;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjk.common.web.session.SessionProvider;
import com.cjk.core.bean.BuyCar;
import com.cjk.core.bean.BuyItem;
import com.cjk.core.bean.product.Sku;
import com.cjk.core.bean.user.Addr;
import com.cjk.core.bean.user.Buyer;
import com.cjk.core.query.user.AddrQuery;
import com.cjk.core.service.product.SkuService;
import com.cjk.core.service.user.AddrService;
import com.cjk.core.web.Constants;

/**
 * 购物车
 * 
 * @author cjk
 */
@Controller
public class CarController {
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private SessionProvider sessionProvider;
	
	//购买按钮
	@RequestMapping(value = "/shopping/buyCar.shtml")
	public String buyCar(Integer skuId,Integer amount , Integer buyLimit,Integer productId,HttpServletResponse response,ModelMap model,HttpServletRequest request){
			//1.创建sku对象,将传进来的skuId设置到sku中去
		
			//springmvc具备将对象转为json的能力    基于 jackson-mapper  jacksin-core  这两个包
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
			if(null == buyCar){
				//购物车，最后一款
				buyCar = new BuyCar();
			}
			if(null != skuId){
				Sku sku = new Sku();
				sku.setId(skuId);
				//购买限制
				if(null != buyLimit){
					sku.setSkuUpperLimit(buyLimit);
				}
				//2.创建购物项对象
				BuyItem buyItem = new BuyItem();
				buyItem.setSku(sku);
				buyItem.setAmount(amount);
				//3.把购物项添加到购物车中  
				//如果用list集合，每次进来都new一个新的元素，那么添加进来的只有最后的一件,所以这里就不要list集合
				buyCar.addItem(buyItem);
				//最后一款商品的ID
				if(null != productId){
					buyCar.setProductId(productId);
				}
				
					//输出流
					StringWriter str = new StringWriter(); 
					
					//对象转json是一个写的过程  Json是字符串
					try {
						om.writeValue(str, buyCar);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				//将它放到cookie中   cookie中的存储是key-value, 且key和value都是字符串形式的   对象转json
				Cookie cookie = new Cookie(Constants.BUYCAR_COOKIE,str.toString());
				
				//关闭浏览器  也要有cookie  我们可以设置时间  默认是-1，表示关闭浏览器就清空，  销毁 0 ，表示关不关浏览器马上就没  expiry 单位是 秒s
				cookie.setMaxAge(60*60*20);
				
				//路径设置   默认：/shopping  不设置"/"，其他页面必须是通过/shopping才能拿到cookie,跨路径
				cookie.setPath("/");
				
				//装进cookie后发送到前台去
				response.addCookie(cookie);
				
			} 
			//将购物车装满，展示购物车页面  , 将购物项装满，也就是将里面的sku装满，其他不需要装   装到对象的引用
			List<BuyItem> items = buyCar.getItems();
			for(BuyItem item : items){
				Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
				item.setSku(skuByKey);
			}
			model.addAttribute("buyCar",buyCar);
		return "product/cart";
	}
	/**
	 * 清空购物车
	 * 两种方法
	 * 1)清空cookie
	 * 2)清空购物车
	 */
	@RequestMapping(value = "/shopping/clearCart.shtml")
	public String cleanCar(HttpServletRequest request , HttpServletResponse response){
		//清空购物车
		
		Cookie cookie = new Cookie(Constants.BUYCAR_COOKIE,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "redirect:/shopping/buyCar.shtml";
	}
	
	/**
	 * 删除一个购物项
	 */
	@RequestMapping(value = "/shopping/deleteItem.shtml")
	public String deleteItem(HttpServletRequest request , Integer skuId ,HttpServletResponse response){
		
		//1.创建sku对象,将传进来的skuId设置到sku中去
		
		//springmvc具备将对象转为json的能力    基于 jackson-mapper  jacksin-core  这两个包
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
		if(null != buyCar){
			Sku sku = new Sku();
			sku.setId(skuId);
			//购物项对象
			BuyItem buyItem = new BuyItem();
			buyItem.setSku(sku);
			buyCar.deleteItem(buyItem);
			
			//输出流
			StringWriter str = new StringWriter(); 
			
			//对象转json是一个写的过程  Json是字符串
			try {
				om.writeValue(str, buyCar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//将它放到cookie中   cookie中的存储是key-value, 且key和value都是字符串形式的   对象转json
			Cookie cookie = new Cookie(Constants.BUYCAR_COOKIE,str.toString());
			
			//关闭浏览器  也要有cookie  我们可以设置时间  默认是-1，表示关闭浏览器就清空，  销毁 0 ，表示关不关浏览器马上就没  expiry 单位是 秒s
			cookie.setMaxAge(60*60*20);
			
			//路径设置   默认：/shopping  不设置"/"，其他页面必须是通过/shopping才能拿到cookie,跨路径
			cookie.setPath("/");
			
			//装进cookie后发送到前台去
			response.addCookie(cookie);
		}
		
		return "redirect:/shopping/buyCar.shtml";
	}
	/**
	 * 结算
	 */
	@RequestMapping(value = "/buyer/trueBuy.shtml")//1）先要判断用户登录。/buyer用户登录拦截
	public String trueBuy(HttpServletRequest request , HttpServletResponse response , ModelMap model){
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
		//2）判断购物车中是否有商品
		if(null != buyCar){
			//3）判断购物车中的商品是否有库存(zou 数据库)
			List<BuyItem> items = buyCar.getItems();
			int size2 = items.size();
			int size = size2;
			if(null != items && size > 0){
				//购物车的商品项
				Integer i = items.size();
				
				//3）判断购物车中的商品是否有库存
				for(BuyItem it : items){
					//获取sku
					Sku sku = skuService.getSkuByKey(it.getSku().getId());
					//判断库存
					if(sku.getStockInventory() < it.getAmount()){
						//清理购物车中的此商品
						buyCar.deleteItem(it);
					}
				}
				//清理后的商品项个数
				Integer l = items.size();
				//判断清理前后
				if( i > l ){//清理了
					//修改cookie中的购物车数据
					//输出流
					StringWriter str = new StringWriter(); 
					
					//对象转json是一个写的过程  Json是字符串
					try {
						om.writeValue(str, buyCar);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					//将它放到cookie中   cookie中的存储是key-value, 且key和value都是字符串形式的   对象转json
					Cookie cookie = new Cookie(Constants.BUYCAR_COOKIE,str.toString());
					
					//关闭浏览器  也要有cookie  我们可以设置时间  默认是-1，表示关闭浏览器就清空，  销毁 0 ，表示关不关浏览器马上就没  expiry 单位是 秒s
					cookie.setMaxAge(60*60*20);
					
					//路径设置   默认：/shopping  不设置"/"，其他页面必须是通过/shopping才能拿到cookie,跨路径
					cookie.setPath("/");
					
					//装进cookie后发送到前台去
					response.addCookie(cookie);
					
					
					return "redirect:/shopping/buyCar.shtml";//没商品，刷新购物车
				}else{
					//加载收货地址
					Buyer buyer = (Buyer)sessionProvider.getAttribute(request, Constants.BUYER_SESSION);
					AddrQuery addrQuery = new AddrQuery();
					addrQuery.setBuyerId(buyer.getUsername());
					//默认是1
					addrQuery.setIsDef(1);
					
					List<Addr> addrList = addrService.getAddrList(addrQuery);
					
					model.addAttribute("addr",addrList.get(0));
					
					//将购物车装满，展示购物车页面  , 将购物项装满，也就是将里面的sku装满，其他不需要装   装到对象的引用
					List<BuyItem> its = buyCar.getItems();
					for(BuyItem item : its){
						Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
						item.setSku(skuByKey);
					}
					model.addAttribute("buyCar",buyCar);
					
					return "product/productOrder";//没清理
				}
			}else{
				return "redirect:/shopping/buyCar.shtml";//没商品，刷新购物车
			}
		}else{
			return "redirect:/shopping/buyCar.shtml";//没商品，刷新购物车
		}
	}
}
