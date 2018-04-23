package com.cjk.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjk.common.encode.Md5Pwd;
import com.cjk.common.web.ResponseUtils;
import com.cjk.common.web.session.SessionProvider;
import com.cjk.core.bean.country.City;
import com.cjk.core.bean.country.Province;
import com.cjk.core.bean.country.Town;
import com.cjk.core.bean.user.Buyer;
import com.cjk.core.query.country.CityQuery;
import com.cjk.core.query.country.ProvinceQuery;
import com.cjk.core.query.country.TownQuery;
import com.cjk.core.service.country.CityService;
import com.cjk.core.service.country.ProvinceService;
import com.cjk.core.service.country.TownService;
import com.cjk.core.service.user.BuyerService;
import com.cjk.core.web.Constants;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 跳转登录页面
 * 登录
 * 个人中心
 * 收货地址
 * @author cjk
 *
 */
@Controller
public class ProfileController {
	
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private Md5Pwd md5Pwd;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	@Autowired
	private TownService townService;
	
	/**
	 * 	get  登陆的页面跳转
	 * @return
	 */
	@RequestMapping(value = "/shopping/login.shtml", method=RequestMethod.GET)
	public String login(){
		return "buyer/login";
	}
	
	/**
	 * post  登陆的请求处理
	 * @param buyer
	 * @param captcha
	 * @param returnUrl
	 * @return
	 */
	@RequestMapping(value = "/shopping/logins.shtml", method=RequestMethod.POST)
	public String logins(Buyer buyer , String captcha ,String returnUrl , ModelMap model,HttpServletRequest request){
		//1.验证码是否为空
		if(StringUtils.isNotBlank(captcha)){
			//开始验证  第一个参数是：jsessionid  第二个参数是：验证码
			//2.验证码是否正确
			if(imageCaptchaService.validateResponseForID(sessionProvider.getSessionId(request), captcha)){
				//3.用户名、密码是否为空
				if(null != buyer && StringUtils.isNotBlank(buyer.getUsername())){
					if(StringUtils.isNotBlank(buyer.getPassword())){
						Buyer names = buyerService.getBuyerByKey(buyer.getUsername());
						//4.账号、密码是否正确
						if(null != names){
							if(names.getPassword().equals(md5Pwd.encode(buyer.getPassword()))){
								//5.放进session,跳转  
								sessionProvider.setAttribute(request, Constants.BUYER_SESSION, names);
								if(StringUtils.isNotBlank(returnUrl)){
									return "redirect:"+returnUrl;
								}else{
									return "redirect:/buyer/index.shtml";
								}
							}else{
								model.addAttribute("error","密码错误！");
							}
						}else{
							model.addAttribute("error","用户名输入错误！");
						}
					}else{
						model.addAttribute("error","请输入密码！");
					}
				}else{
					model.addAttribute("error","请输入用户名！");
				}
			}else{
				model.addAttribute("error","验证码输入错误！");
			}
		}else{
			model.addAttribute("error","请填写验证码！");
		}
		return "buyer/login";
	}
	
	/**
	 * 个人中心
	 */
	@RequestMapping(value = "/buyer/index.shtml")
	public String index(){
		return "buyer/index";
	}
	
	/**
	 * 个人资料
	 */
	@RequestMapping(value = "/buyer/profile.shtml")
	public String profile(HttpServletRequest request , ModelMap model){
		//加载用户
		Buyer buyer = (Buyer)sessionProvider.getAttribute(request,Constants.BUYER_SESSION);
		model.addAttribute("buyer",buyer);
		//省市县级联查询
		List<Province> provinceList = provinceService.getProvinceList(null);
		model.addAttribute("provinceList",provinceList);
		//市
		CityQuery cityQuery = new CityQuery();
		//定位省
		cityQuery.setProvince(buyer.getProvince());
		List<City> cityList = cityService.getCityList(cityQuery);
		model.addAttribute("cityList",cityList);
		//县
		TownQuery townQuery = new TownQuery();
		//定位市
		townQuery.setCity(buyer.getCity());
		List<Town> townList = townService.getTownList(townQuery);
		model.addAttribute("townList",townList);
		
		
		
		
		
		return "buyer/profile";
	}
	/**
	 * 加载城市
	 */
	@RequestMapping(value = "/buyer/city.shtml")
	public void city(String code , HttpServletResponse response){
		CityQuery cityQuery = new CityQuery();
		//定位省
		cityQuery.setProvince(code);
		List<City> cityList = cityService.getCityList(cityQuery);
		JSONObject jo = new JSONObject();
		jo.put("cityList", cityList);
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	/**
	 * 加载区县
	 */
	@RequestMapping(value = "/buyer/town.shtml")
	public void town(String code , HttpServletResponse response){
		TownQuery townQuery = new TownQuery();
		//定位市
		townQuery.setCity(code);
		List<Town> townList = townService.getTownList(townQuery);
		
		JSONObject jo = new JSONObject();
		jo.put("townList", townList);
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	/**
	 * 收货地址
	 */
	@RequestMapping(value = "/buyer/deliver_address.shtml")
	public String deliverAddress(){
		
		
		return "buyer/deliver_address";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/buyer/change_password.shtml")
	public String changePassword(){
		
		
		return "buyer/change_password";
	}
	

	
	
}
