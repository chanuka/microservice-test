package com.cba.core.auth.dao.impl;

import com.cba.core.auth.dao.UserDao;
import com.cba.core.auth.exception.NotFoundException;
import com.cba.core.auth.model.User;
import com.cba.core.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository repository;

    @Override
    public User findByUserName(String userName) throws Exception {
        return repository.findByUserName(userName).orElseThrow(() -> new NotFoundException("User Not Found"));
    }
}
