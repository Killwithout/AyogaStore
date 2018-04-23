package com.cjk;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;

import com.cjk.core.bean.BuyCar;
import com.cjk.core.bean.user.Buyer;

/**
 * 
 * @author cjk
 *
 */
public class TestCookie {
	
	@Test
	public void  testCookie() throws Exception{
		
		Buyer buyer = new Buyer();
		buyer.setUsername("admin");
		
		//springmvc具备将对象转为json的能力    基于 jackson-mapper  jacksin-core  这两个包
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		//输出流
		StringWriter str = new StringWriter(); 
		
		//对象转json是一个写的过程  Json是字符串
		om.writeValue(str, buyer);
		
		System.out.println(str.toString());
		
		
		//json转对象是    读 
		Buyer readValue = om.readValue(str.toString(), Buyer.class);
		
		System.out.println(readValue);
		
	}
}
