package com.cba.core.product.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto implements Serializable{

    private String id;
    private String name;
    private String mobile;
    private String status;

    private static final long serialVersionUID = 1L;
}
