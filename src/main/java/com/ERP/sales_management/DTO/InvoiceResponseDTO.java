package com.ERP.sales_management.DTO;
import com.ERP.sales_management.Enum.OrderStatus;
import com.ERP.sales_management.Model.Customer;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

    @Data
    public class InvoiceResponseDTO {
        private Integer id;
        private String orderId;
        private OrderStatus orderStatus;
        private String invoiceNo;
        private Double totalAmount;
        private String status;
        private LocalDateTime issuedDate;
        private Customer customer;
        private List<ProductDto> items;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public List<ProductDto> getItems() {
            return items;
        }

        public void setItems(List<ProductDto> items) {
            this.items = items;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }
    }


