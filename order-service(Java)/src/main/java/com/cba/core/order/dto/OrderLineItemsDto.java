package com.cba.core.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDto implements Serializable {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    private static final long serialVersionUID = 1L;

}
