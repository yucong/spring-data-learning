package com.yucong.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.pojo.o2o.Roles;
import com.yucong.pojo.o2o.Users;
import com.yucong.pojo.o2o.UsersDao;


/**
 * 一对一关联关系测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OneToOneTest {

	@Autowired
	private UsersDao usersDao;
	
	/**
	 * 添加用户同时添加角色
	 */
	@Test
	public void test1(){
		//创建角色
		Roles roles = new Roles();
		roles.setRolename("管理员");
		
		//创建用户
		Users users = new Users();
		users.setUserage(30);
		users.setUsername("赵小刚");
		
		//建立关系
		users.setRoles(roles);
		roles.setUsers(users);
		
		//保存数据
		this.usersDao.save(users);
		
		System.out.println(usersDao);
		System.out.println(usersDao.getClass().getName());
	}
	
	/**
	 * 根据用户ID查询用户，同时查询用户角色
	 * 因为案例中用户和角色是双向关联关系。也就是用户中有角色的引用，角色中有用户的引用。
	 * 那么查询用户的时候，会查询角色。
	 * 底层Hibernate认为，角色在初始化对象的时候，不能认为用户查询的关联数据是一定准确且完整的，必须为角色再次查询一下关系数据对象用户。
	 * 所以，会执行两次查询。
	 * 1 ： select * from t_users left join t_roles on t_users.role_id = t_roles.roleid
	 *  		where t_users.id = ?
	 *   查询用户数据的同时查询角色。
	 * 2 ： select * from t_users left join t_roles on t_users.role_id = t_roles.roleid
	 *  		where t_users.role_id = ?
	 *   初始化角色对象的时候，需要再次查询一下角色对应的用户是什么。保证数据的绝对正确。
	 */
	@Test
	public void test2(){
		Users users = this.usersDao.findOne(1);
		System.out.println("用户信息："+users);
//		Roles roles = users.getRoles();
//		System.out.println(roles);
	}
}
