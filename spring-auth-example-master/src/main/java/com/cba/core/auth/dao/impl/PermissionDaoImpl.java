package com.cba.core.auth.dao.impl;

import com.cba.core.auth.dao.PermissionDao;
import com.cba.core.auth.model.Permission;
import com.cba.core.auth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class PermissionDaoImpl implements PermissionDao {

    private final PermissionRepository repository;

    @Override
//    @Cacheable("permissions")
    public Iterable<Permission> findAllPermissionsByUser(String username) throws SQLException {
        return repository.findAllPermissionsByUser(username);
    }

}
