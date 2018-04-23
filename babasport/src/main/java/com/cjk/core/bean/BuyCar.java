package com.cjk.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 购物车
 * @author cjk
 *
 */
public class BuyCar {
	
	//购物项  list  小计 将购物项实例化
	List<BuyItem> items = new ArrayList<BuyItem>();
	
	//添加方法
	public void addItem(BuyItem item){
		//判断是否重复   contains  是 基于equals ,所以两个比较的是不一样的地址，那么那必须重写equals
		if(items.contains(item)){
			for(BuyItem it : items){
				if(it.equals(item)){
					int result = it.getAmount()+item.getAmount();
					if(it.getSku().getSkuUpperLimit() >= result){
						it.setAmount(result);
					}else{
						it.setAmount(it.getSku().getSkuUpperLimit());
					}
					
				}
			}
		}else{
			items.add(item);
		}
	}
	//小计开始
	//商品数量
	@JsonIgnore
	public int getProductAumount(){
		int result = 0 ;
		for(BuyItem item : items){
			result += item.getAmount();
		}
		return result;
	}
	//商品金额
	@JsonIgnore
	public double getProductPrice(){
		double result = 0.00 ;
		for(BuyItem item : items){
			result += item.getSku().getSkuPrice()*item.getAmount();
		}
		return result;
	}
	//运费    >30mianf
	@JsonIgnore
	public double getFee(){
		double result = 0.00 ;
		if(getProductPrice() <= 39){
			result = 10.00;
		}
		return result; 	
	}
	//应付总额 = 商品金额 + 运费
	@JsonIgnore
	public double getTotalPrice(){
		double result = getFee() + getProductPrice();
		return result;
	}
	
	//清空购物车
	public void clearCar(){
		items.clear();
	}
	//从购物车中删除一款商品
	public void deleteItem(BuyItem item){
		items.remove(item);//因为我们重写了equals方法
	}
	
	
	//继续购买  最后一款
	private Integer productId;
	
	public List<BuyItem> getItems() {
		return items;
	}
	public void setItems(List<BuyItem> items) {
		this.items = items;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
