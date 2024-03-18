package com.cba.core.auth.repository;

import com.cba.core.auth.model.User;
import com.cba.core.auth.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameAndUserType(String userName, UserType userType);

}
