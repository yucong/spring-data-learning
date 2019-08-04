package com.yucong.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.dao.UsersDaoExtendsCrudRepository;
import com.yucong.pojo.Users;

/**
 * CrudRepository接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CrudRepositoryTest {

	@Autowired
	private UsersDaoExtendsCrudRepository usersDao;
	
	/**
	 * 添加单条数据
	 * CrudRepository提供的数据写方法，其具体实现由SimpleJpaRepository提供。
	 * 在SimpleJpaRepository中提供的写方法，已经开启了事务环境，所以测试的过程可以直接调用，不需要考虑事务问题。
	 * save方法中，其实现逻辑是先执行exists，再执行后续的save或update逻辑。
	 * exists逻辑 - 根据传入参数，检查参数中的ID属性是否有数据，如果有数据执行findById逻辑，查看返回结果是否为null。
	 *  如果返回结果为null，则代表数据不存在，执行新增。如果返回结果不为null，代表数据存在，执行更新。
	 */
	@Test
	public void test1(){
		Users user = new Users();
		user.setUserid(1);
		user.setUserage(22);
		user.setUsername("赵小丽");
		this.usersDao.save(user);
	}
	
	/**
	 * 批量添加数据
	 */
	@Test
	public void test2(){
		Users user = new Users();
		user.setUserage(21);
		user.setUsername("赵小丽");
		
		Users user1 = new Users();
		user1.setUserage(25);
		user1.setUsername("王小虎");
		
		List<Users> list= new ArrayList<>();
		list.add(user);
		list.add(user1);
		
		this.usersDao.save(list);
		
	}
	
	/**
	 * 根据ID查询单条数据
	 */
	@Test
	public void test3(){
		Users users = this.usersDao.findOne(5);
		System.out.println(users);
	}
	
	/**
	 * 查询全部数据
	 */
	@Test
	public void test4(){
		List<Users> list = (List<Users>)this.usersDao.findAll();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 删除数据
	 */
	@Test
	public void test5(){
		this.usersDao.delete(7);
	}
	
	/**
	 * 更新数据 方式一
	 */
	@Test
	public void test6(){
		Users user = this.usersDao.findOne(2);
		user.setUsername("王小红");
		this.usersDao.save(user);
	}
	
	/**
	 * 更新数据 方式二
	 * 不推荐。是使用的Hibernate对缓存对象状态的管理实现数据更新的。需要对事物的边界做控制，且有数据不一致的可能。
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void test7(){
		Users user = this.usersDao.findOne(2);//持久化状态的
		user.setUsername("王小小");
	}
}
