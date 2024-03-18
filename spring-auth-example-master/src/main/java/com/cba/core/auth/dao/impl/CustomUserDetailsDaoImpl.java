package com.cba.core.auth.dao.impl;

import com.cba.core.auth.dao.CustomUserDetailsDao;
import com.cba.core.auth.exception.NotFoundException;
import com.cba.core.auth.model.User;
import com.cba.core.auth.model.UserType;
import com.cba.core.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomUserDetailsDaoImpl implements CustomUserDetailsDao {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userName, UserType userType) throws UsernameNotFoundException {
        try {
            return userRepository.findByUserNameAndUserType(userName, userType).orElseThrow(() -> new NotFoundException("User not found"));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found cause is : " + e.getMessage());
        }
    }
}
