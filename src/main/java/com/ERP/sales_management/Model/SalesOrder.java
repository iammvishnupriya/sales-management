package com.ERP.sales_management.Model;

import com.ERP.sales_management.Enum.OrderStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "sales_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderNumber;

    // @ManyToOne
    // @JoinColumn(name = "customer_id")
    // private Customer customer;
    private String customer;

    private LocalDate orderDate;

    private Integer categoryId;

    private String productName;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer quantity;

    private Integer price_per_unit;

    private double totalAmount;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column(length = 1000)
    private String remarks;

    private LocalDate deliveryDate;


    @ManyToOne
    @JoinColumn(name = "created_by", nullable = true)
    private User createdBy;

    public String getProcessingRemarks() {
        return ProcessingRemarks;
    }

    public void setProcessingRemarks(String processingRemarks) {
        ProcessingRemarks = processingRemarks;
    }

    @Column(length = 1000)
     private String ProcessingRemarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(Integer price_per_unit) {
        this.price_per_unit = price_per_unit;
    }     
}
