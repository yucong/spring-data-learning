package com.yucong.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.dao.myrepository.UsersDao;
import com.yucong.pojo.Users;

/**
 * 自定义Repository接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyRepositoryTest {

	@Autowired
	private UsersDao usersDao;
	
	/**
	 * 需求：根据用户ID查询数据
	 */
	@Test
	public void test1(){
		Users users = this.usersDao.findUserById(5);
		System.out.println(users);
	}
}
