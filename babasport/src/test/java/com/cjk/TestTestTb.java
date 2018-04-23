package com.cjk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cjk.common.junit.SpringJunitTest;
import com.cjk.core.bean.TestTb;
import com.cjk.core.service.TestTbService;

/**
 * 测试
 * @author cjk
 *
 */

public class TestTestTb extends SpringJunitTest{

	@Autowired
	private TestTbService testTbService;
	@Test
	public void testAdd() throws Exception {
		TestTb testTb = new TestTb();
		testTb.setName("金乐乐");
		
		testTbService.addTestTb(testTb);
	}
}
