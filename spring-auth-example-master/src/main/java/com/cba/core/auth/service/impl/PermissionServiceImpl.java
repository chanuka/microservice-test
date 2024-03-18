package com.cba.core.auth.service.impl;

import com.cba.core.auth.dao.PermissionDao;
import com.cba.core.auth.dto.PermissionResponseDto;
import com.cba.core.auth.mapper.PermissionMapper;
import com.cba.core.auth.model.Permission;
import com.cba.core.auth.service.PermissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDao dao;

    @Override
    public List<PermissionResponseDto> findAllPermissionsByUser(String username) throws SQLException {
        Iterable<Permission> irt = dao.findAllPermissionsByUser(username);
        List<PermissionResponseDto> result =
                StreamSupport.stream(irt.spliterator(), false)
                        .map(PermissionMapper::toDto)
                        .collect(Collectors.toList());
        return result;
    }
}
