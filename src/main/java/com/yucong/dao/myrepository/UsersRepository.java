package com.yucong.dao.myrepository;

import com.yucong.pojo.Users;

/**
 * 自定义Repository， 定义需要的业务方法。
 */
public interface UsersRepository {

	public Users findUserById(Integer userid);
}
