package com.yucong.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yucong.pojo.o2m.Roles;
import com.yucong.pojo.o2m.Users;
import com.yucong.pojo.o2m.UsersDao;

/**
 * 一对多的关联关系测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OneToManyTest {

	@Autowired
	private UsersDao usersDao;
	
	
	/**
	 * 添加用户同时添加角色
	 * save方法可以实现新增和更新的逻辑，但是不能实现关系对象的数据是否存在的判断。
	 * 也就是关系对象Roles是否存在是不能通过save方法判断。
	 * 这是save方法的问题所在。
	 * 
	 * 如果真的需要一次性创建一对多关系的多个数据，建议在一次请求中完成多数据的新增。
	 * 
	 * 真正的商业开发中，一般是不允许做级联新增的。
	 * 只是新增用户数据，角色关系数据是可以维护关系的。
	 */
	@Test
	public void test1(){
		List<Users> entities = new ArrayList<>();
		
		//创建角色
		Roles roles = new Roles();
		roles.setRolename("开发");
		//创建用户
		Users users =new Users();
		users.setUserage(30);
		users.setUsername("小李");
		//建立关系
		roles.getUsers().add(users);
		users.setRoles(roles);
		
		entities.add(users);
		
		users =new Users();
		users.setUserage(30);
		users.setUsername("小凳子");
		roles.getUsers().add(users);
		users.setRoles(roles);
		
		entities.add(users);
		//保存数据
		this.usersDao.save(entities);
		
	}
	
	/**
	 * 根据用户ID查询用户信息，同时查询角色
	 * 在spring-data-jpa中，底层使用Hibernate实现。
	 * Hibernate默认在一对一关系中不延迟加载。
	 * 默认在一对多和多对多关系中使用延迟加载。
	 * spring-data-jpa中，动态代理提供的方法都是带有事务管理的。且事务一旦结束，会话关闭。
	 * 会话关闭后，延迟加载的关系数据对象就不能正常加载。
	 * 
	 * spring-data-jpa中底层Hibernate使用的会话session对象是通过sessionFactory.getCurrentSession方法获取的。
	 * 这个session对象必须有事务环境，且事务一旦结束，会话关闭。
	 */
	@Test
	@Transactional
	public void test2(){
		Users users = this.usersDao.findOne(6);
		System.out.println("用户姓名："+users.getUsername());
		Roles roles = users.getRoles();
		System.out.println(roles);
		System.out.println(roles.getUsers());
	}
}
