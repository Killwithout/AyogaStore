package com.cjk;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cjk.common.junit.SpringJunitTest;
import com.cjk.core.bean.product.Brand;
import com.cjk.core.query.product.BrandQuery;
import com.cjk.core.service.product.BrandService;

/**
 * 测试
 * @author cjk
 *
 */

public class TestBrand extends SpringJunitTest{

	@Autowired
	private BrandService brandService;
	@Test
	public void testGet() throws Exception {
		BrandQuery  brandQuery = new BrandQuery();
		
		//brandQuery.setFields("id");
		//brandQuery.setNameLike(true);
		//brandQuery.setName("金");
		brandQuery.orderbyId(false);
		
		brandQuery.setPageNo(2);
		brandQuery.setPageSize(2);
		
		List<Brand> brands = brandService.getBrandList(brandQuery);
		
		for (Brand b : brands) {
			System.out.println(b);
		}
	}
}
