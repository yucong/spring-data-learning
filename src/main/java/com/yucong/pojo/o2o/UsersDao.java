package com.yucong.pojo.o2o;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 一对一关联关系操作
 */
public interface UsersDao extends JpaRepository<Users, Integer>,JpaSpecificationExecutor<Users>{
	
}
