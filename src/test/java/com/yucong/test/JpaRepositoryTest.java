package com.yucong.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.dao.UsersDaoExtendsJpaRepository;
import com.yucong.pojo.Users;

/**
 * JpaRepository接口测试
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JpaRepositoryTest {

	@Autowired
	private UsersDaoExtendsJpaRepository usersDao;
	
	/**
	 * 查询全部数据
	 */
	@Test
	public void test1(){
		List<Users> list  = this.usersDao.findAll();
		for (Users users : list) {
			System.out.println(users);
		}
	}
}
