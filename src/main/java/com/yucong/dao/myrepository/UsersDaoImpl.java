package com.yucong.dao.myrepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.yucong.pojo.Users;

public class UsersDaoImpl implements UsersRepository {

	@PersistenceContext(name="entityManagerFactory")
	private EntityManager em;
	
	@Override
	public Users findUserById(Integer userid) {
		System.out.println("MyRepository......");
		return this.em.find(Users.class, userid);
	}

}
