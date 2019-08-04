package com.yucong.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.yucong.pojo.Users;

/**
 * Repository接口讲解
 * Repository<访问数据库时操作的java实体类型, 实体中主键的类型>
 */
public interface UsersDaoExtendsRepository extends Repository<Users, Integer> {
	
	// 方法名称命名规则实现查询
	List<Users> findByUsernameIs(String string);
	List<Users> findByUsernameLike(String string);
	List<Users> findByUsernameAndUserageGreaterThanEqual(String name, Integer age);

	// 基于@Query注解查询 - JPQL
	@Query("select new Users(username, userage) from Users")
	List<Object> queryTest();
	@Query(value = "from Users where username = :username")
	List<Users> queryUserByNameUseJPQL(@Param("username") String name);
	@Query("from Users where username like :username")
	List<Users> queryUserByLikeNameUseJPQL(@Param("username") String name);
	@Query("from Users where username = :username and userage >= :userage")
	List<Users> queryUserByNameAndAge(@Param("username") String name, @Param("userage") Integer age);

	// 基于@Query注解查询 - SQL
	// nativeQuery:代表value属性的值是否是本地查询，也就是value属性的值是否是SQL。默认为false。
	@Query(value = "select * from tb_users where username = ?", nativeQuery = true)
	List<Users> queryUserByNameUseSQL(String name);
	@Query(value = "select * from tb_users where username like ?", nativeQuery = true)
	List<Users> queryUserByLikeNameUseSQL(String name);
	@Query(value = "select * from tb_users where username = ? and userage >= ?", nativeQuery = true)
	List<Users> queryUserByNameAndAgeUseSQL(String name, Integer age);

	// 基于@Query注解实现更新，JPQL语法只能实现更新操作，可以使用nativeQuery实现新增和删除操作。
	@Query("update Users set userage = :userage where userid = :userid")
	@Modifying // @Modifying当前语句是一个写操作
	void updateUserAgeById(@Param("userage") Integer age, @Param("userid") Integer id);
	@Query(value="delete from tb_users where userid = ?", nativeQuery=true)
	@Modifying 
	void deleteUserById(Integer id);
	@Query(value="insert into tb_users(username, userage) values(?, ?)", nativeQuery=true)
	@Modifying 
	void saveUser(String username, Integer userage);

}
