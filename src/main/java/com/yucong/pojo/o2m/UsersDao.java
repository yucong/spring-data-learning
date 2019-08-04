package com.yucong.pojo.o2m;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 一对多关联关系操作
 */
public interface UsersDao extends JpaRepository<Users, Integer>,JpaSpecificationExecutor<Users>{
	
}
