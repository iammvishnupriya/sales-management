package com.ERP.sales_management.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesOrderItemDTO {
    private int id;
    private int salesOrderId;
    private int productId;
    private int quantity;
    private double price;
}
