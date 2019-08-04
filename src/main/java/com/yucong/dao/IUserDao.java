package com.yucong.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yucong.pojo.Users;

/**
 * 很多人初学spring-data-jpa的时候，直接学习JpaRepository
 * JpaRepository不是标记接口，其中有若干方法定义，且有父接口中继承的若干方法。
 * 所以接口中有很多的数据访问逻辑。容易造成幻觉，认为spring-data-jpa本身提供若干数据访问方法。
 */
public interface IUserDao extends JpaRepository<Users, Integer> {

}
