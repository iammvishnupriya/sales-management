package com.ERP.sales_management.Model;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id", nullable = false)
    private String orderId;



    @Column(name = "invoice_no", nullable = false, unique = true)
    private String invoiceNo;

    @Column(nullable = false)
    private Double totalAmount;



    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issuedDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Invoice(){}
    public Invoice(Integer id, String orderId, String invoiceNo, Double totalAmount,LocalDateTime issuedDate, Customer customer) {
        this.id = id;
        this.orderId = orderId;
        this.invoiceNo = invoiceNo;
        this.totalAmount = totalAmount;
        this.issuedDate = issuedDate;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }



    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
