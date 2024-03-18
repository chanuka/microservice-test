package com.cba.core.auth.mapper;

import com.cba.core.auth.dto.PermissionResponseDto;
import com.cba.core.auth.model.Permission;

public class PermissionMapper {

    public static PermissionResponseDto toDto(Permission entity) {
        PermissionResponseDto responseDto = new PermissionResponseDto();
        responseDto.setId(entity.getId());
        responseDto.setCreated(entity.getCreated().intValue());
        responseDto.setDeleted(entity.getDeleted().intValue());
        responseDto.setReadd(entity.getReadd().intValue());
        responseDto.setUpdated(entity.getUpdated().intValue());
        responseDto.setRoleId(entity.getRole().getId());
        responseDto.setRoleName(entity.getRole().getRoleName());
        responseDto.setResourceId(entity.getResource().getId());
        responseDto.setResourceName(entity.getResource().getName());
        return responseDto;
    }

}
