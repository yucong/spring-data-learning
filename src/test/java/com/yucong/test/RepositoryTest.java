package com.yucong.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.dao.UsersDaoExtendsRepository;
import com.yucong.pojo.Users;

/**
 * Repository接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RepositoryTest {

	@Autowired
	private UsersDaoExtendsRepository usersDao;
	
	/**
	 * 需求：使用用户名作为查询条件
	 */
	@Test
	public void test1(){
		List<Users> list = this.usersDao.findByUsernameIs("王五");
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 需求：根据用户姓名做Like处理
	 */
	@Test
	public void test2(){
		List<Users> list = this.usersDao.findByUsernameLike("王%");
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 需求：查询名称为王五，并且他的年龄大于等于22岁
	 */
	@Test
	public void test3(){
		System.out.println(this.usersDao);
		System.out.println(this.usersDao.getClass().getName());
		
		List<Users> list = this.usersDao.findByUsernameAndUserageGreaterThanEqual("王五", 22);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query查询 JPQL
	 */
	@Test
	public void test4(){
		List<Users> list = this.usersDao.queryUserByNameUseJPQL("王五");
		for (Users users : list) {
			System.out.println(users);
		}
		System.out.println("================================");
		List<Object> l = this.usersDao.queryTest();
		System.out.println(l);
		for(Object o : l){
			System.out.println(o);
			System.out.println(o.getClass().getName());
			// System.out.println(Arrays.toString(((Object[])o)));
		}
	}
	
	/**
	 * 测试@Query查询 JPQL
	 */
	@Test
	public void test5(){
		List<Users> list = this.usersDao.queryUserByLikeNameUseJPQL("王%");
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query查询 JPQL
	 */
	@Test
	public void test6(){
		List<Users> list = this.usersDao.queryUserByNameAndAge("王五", 22);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query查询 SQL
	 */
	@Test
	public void test7(){
		List<Users> list = this.usersDao.queryUserByNameUseSQL("王五");
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query查询 SQL
	 */
	@Test
	public void test8(){
		List<Users> list = this.usersDao.queryUserByLikeNameUseSQL("张%");
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query查询 SQL
	 */
	@Test
	public void test9(){
		List<Users> list = this.usersDao.queryUserByNameAndAgeUseSQL("王五", 22);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 测试@Query update
	 * 事务问题
	 * @Transactional(1.8.x) - 当前方法有事务环境。如果注解描述的是非测试代码，则代表方法执行成功提交事务，执行失败回滚事务。
	 *  如果注解描述的是测试方法，所有的事务都会回滚。
	 *  那么如果在测试方法中需要使用事务的话，定义@Rollback(false)代表取消回滚策略。使用提交策略。
	 * @Transactional(1.11.x) - 测试方法和其他方法，事务管理逻辑都是方法执行成功提交事务，方法执行失败回滚事务。
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test10(){
		this.usersDao.updateUserAgeById(22, 1);
	}
	
	/**
	 * 测试@Query update
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test11(){
		this.usersDao.deleteUserById(1);
	}
	
	/**
	 * 测试@Query update
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test12(){
		this.usersDao.saveUser("张三", 20);
	}
}
