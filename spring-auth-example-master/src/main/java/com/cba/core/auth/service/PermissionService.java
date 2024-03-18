package com.cba.core.auth.service;

import com.cba.core.auth.dto.PermissionResponseDto;

import java.util.List;

public interface PermissionService {

    List<PermissionResponseDto> findAllPermissionsByUser(String username) throws Exception;

}
