package com.yucong.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.yucong.pojo.Users;

/**
 * JpaRepository接口讲解
 */
public interface UsersDaoExtendsJpaRepository extends JpaRepository<Users, Integer>{
	
}
