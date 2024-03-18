package com.cba.core.auth.dao;

import com.cba.core.auth.model.Permission;

import java.sql.SQLException;

public interface PermissionDao {

    Iterable<Permission> findAllPermissionsByUser(String username) throws SQLException;

}
