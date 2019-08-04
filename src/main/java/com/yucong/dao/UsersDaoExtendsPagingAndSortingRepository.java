package com.yucong.dao;



import org.springframework.data.repository.PagingAndSortingRepository;

import com.yucong.pojo.Users;

/**
 * PagingAndSortingRepository接口讲解
 */
public interface UsersDaoExtendsPagingAndSortingRepository extends PagingAndSortingRepository<Users, Integer>{
	
}
