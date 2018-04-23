package com.cjk.core.bean;

import com.cjk.core.bean.product.Sku;

/**
 * 购物项
 * @author cjk
 *
 */
public class BuyItem {
	//sku   number
	private Sku sku;
	
	private int amount = 1;

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)//判断地址
			return true;
		if (obj == null)//非空
			return false;
		if (getClass() != obj.getClass())//类型
			return false;
		BuyItem other = (BuyItem) obj;//强转
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
	
	
}
