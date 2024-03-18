package com.cba.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto implements Serializable{

    private String id;
    private String name;
    private String mobile;
    private String status;

    private static final long serialVersionUID = 1L;
}
