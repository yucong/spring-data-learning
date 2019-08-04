package com.yucong.dao.myrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yucong.pojo.Users;

/**
 * 用户自定义Repository接口讲解
 */
public interface UsersDao extends JpaRepository<Users, Integer>,
		JpaSpecificationExecutor<Users>,UsersRepository{
	
}
