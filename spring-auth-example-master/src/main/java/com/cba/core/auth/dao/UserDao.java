package com.cba.core.auth.dao;

import com.cba.core.auth.model.User;

public interface UserDao {

    User findByUserName(String userName) throws Exception;

}
