package com.yucong.test;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.dao.UsersDaoExtendsJpaSpecificationExecutor;
import com.yucong.pojo.Users;

/**
 * JpaSpecificationExecutor接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JpaSpecificationExecutorTest {

	@Autowired
	private UsersDaoExtendsJpaSpecificationExecutor usersDao;
	
	/**
	 * 需求：根据用户姓名查询数据
	 */
	@Test
	public void test1(){
		final String username = "王五";
		Specification<Users> spec = new Specification<Users>() {

			/**
			 * @return Predicate:定义了查询条件
			 * @param Root<Users> root:根对象。封装了查询条件的对象，封装了Users类型的特性。
			 * @param CriteriaQuery<?> query:定义了一个基本的查询。一般不使用
			 * @param CriteriaBuilder cb:创建查询条件的构建器
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 定义查询条件，使用构建器构建，构建过程中，需要针对root根对象的查询条件特性提供查询。
				// 案例代码没有业务背景，所以查询条件直接传递的固定数据"王五"，项目中应该传递的是外部变量。
				Predicate pre = cb.equal(root.get("username"), username);
				return pre;
			}
		};
		List<Users> list = this.usersDao.findAll(spec);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 多条件查询
	 * 需求：使用用户姓名或者年龄查询数据
	 */
	@Test
	public void test3(){
		Specification<Users> spec = new Specification<Users>() {

			/**
			 * CriteriaBuilder中有方法and和or，用于拼接多个查询条件。
			 * and是用于拼接并且逻辑的，or拼接或者逻辑的。
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.or(cb.equal(root.get("username"),"王五"),cb.equal(root.get("userage"), 25));
			}
			
		};
		List<Users> list = this.usersDao.findAll(spec);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 需求：查询王姓用户，并且做分页处理
	 */
	@Test
	public void test4(){
		//条件
		Specification<Users> spec = new Specification<Users>() {

			/**
			 * root.get() -> 方法得到的返回结果是Path类型的对象。Path类型对象如果实现等值判断，不等值判断都可以直接使用。
			 * 如果path类型的对象实现模糊匹配则会抛出异常。
			 * 因为predicate实现模糊匹配的时候，需要的参数是一个字符串类型的属性名，而不是一个path类型的对象。
			 * 所以必须做类型转换。
			 * Path类型中提供了转换方法，使用类对象决定转换结果为什么类型的数据。
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username").as(String.class), "王%");
			}
			
		};
		
		//分页
		Pageable pageable = new PageRequest(0, 2);
		Page<Users> page = this.usersDao.findAll(spec, pageable);
		System.out.println("总条数："+page.getTotalElements());
		System.out.println("总页数："+page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 需求：查询数据库中王姓的用户，并且根据用户id做倒序排序
	 */
	@Test
	public void test5(){
		//条件
		Specification<Users> spec = new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username").as(String.class), "王%");
			}
			
		};
		//排序
		Sort sort = new Sort(Direction.DESC,"userid");
		List<Users> list = this.usersDao.findAll(spec, sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 需求：查询数据库中王姓的用户，做分页处理，并且根据用户id做倒序排序
	 */
	@Test
	public void test6(){
		//排序等定义
		Sort sort = new Sort(Direction.DESC,"userid");
		//分页的定义
		Pageable pageable = new PageRequest(1,1, sort);
		
		//查询条件
		Specification<Users> spec = new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username").as(String.class), "王%");
			}
			
		};
		Page<Users> page = this.usersDao.findAll(spec, pageable);
		System.out.println("总条数："+page.getTotalElements());
		System.out.println("总页数："+page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
}
