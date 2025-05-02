package com.ERP.sales_management.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalesOrderRequest {

    private String customer;
    private Integer categoryId;
    private String productName;
    private Integer price_per_unit;
    private Integer quantity;
    private Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(Integer price_per_unit) {
        this.price_per_unit = price_per_unit;
    }     
}
