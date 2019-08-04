package com.yucong.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yucong.dao.UsersDaoExtendsPagingAndSortingRepository;
import com.yucong.pojo.Users;

/**
 * CrudRepository接口测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PagingAndSortingRepositoryTest {

	@Autowired
	private UsersDaoExtendsPagingAndSortingRepository usersDao;
	
	/**
	 * 分页
	 */
	@Test
	public void test1(){
		int page = 0; //page:当前页的索引。索引从0开始。
		int size = 3;// size:每页显示3条数据
		Pageable pageable= new PageRequest(page, size);
		Page<Users> p = this.usersDao.findAll(pageable);
		System.out.println("数据的总条数："+p.getTotalElements());
		System.out.println("总页数："+p.getTotalPages());
		List<Users> list = p.getContent();
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 对单列做排序处理
	 */
	@Test
	public void test2(){
		//Sort:该对象封装了排序规则以及指定的排序字段(对象的属性来表示)
		//direction:排序规则
		//properties:指定做排序的属性
		Sort sort = new Sort(Direction.DESC,"userid");
		List<Users> list = (List<Users>)this.usersDao.findAll(sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
	
	/**
	 * 多列的排序处理
	 */
	@Test
	public void test3(){
		//Sort:该对象封装了排序规则以及指定的排序字段(对象的属性来表示)
		//direction:排序规则
		//properties:指定做排序的属性
		Order order1 = new Order(Direction.DESC,"userage");
		Order order2 = new Order(Direction.ASC,"username");
		Sort sort = new Sort(order1,order2);
		List<Users> list = (List<Users>)this.usersDao.findAll(sort);
		for (Users users : list) {
			System.out.println(users);
		}
	}
}
