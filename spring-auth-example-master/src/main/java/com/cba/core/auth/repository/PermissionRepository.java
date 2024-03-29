package com.cba.core.auth.repository;

import com.cba.core.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("SELECT p FROM Permission p WHERE p.role = (SELECT ur.role from UserRole ur WHERE ur.userByUserId=(SELECT u FROM User u WHERE u.userName=:username))")
    Iterable<Permission> findAllPermissionsByUser(String username);

}
