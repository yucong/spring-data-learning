package com.yucong.dao;



import org.springframework.data.repository.CrudRepository;

import com.yucong.pojo.Users;

/**
 * CrudRepository接口讲解
 */
public interface UsersDaoExtendsCrudRepository extends CrudRepository<Users, Integer> {
	
}
