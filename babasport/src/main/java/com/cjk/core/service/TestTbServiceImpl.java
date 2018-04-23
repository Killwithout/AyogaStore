package com.cjk.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjk.core.bean.TestTb;
import com.cjk.core.dao.TestTbDao;

/**
 * 
 * @author cjk
 *
 */
@Service
@Transactional
public class TestTbServiceImpl implements TestTbService{

	@Resource
	private TestTbDao testTbDao;
	
	public void addTestTb(TestTb testTb) {
		testTbDao.addTestTb(testTb);
		
		throw new RuntimeException();
	}

}
