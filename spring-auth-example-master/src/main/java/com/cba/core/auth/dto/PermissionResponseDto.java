package com.cba.core.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionResponseDto implements java.io.Serializable {

    private Integer id;
    private Integer roleId;
    private String roleName;
    private Integer resourceId;
    private String resourceName;
    private Integer readd;
    private Integer created;
    private Integer updated;
    private Integer deleted;

    private static final long serialVersionUID = 1L;

}
