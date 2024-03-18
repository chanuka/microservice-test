package com.cba.core.auth.dao;

import com.cba.core.auth.model.User;
import com.cba.core.auth.model.UserType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsDao {

    User loadUserByUsername(String username, UserType userType) throws UsernameNotFoundException;
}
