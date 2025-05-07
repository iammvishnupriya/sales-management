package com.ERP.sales_management.DTO;

public class UpdateProcessingOrderStatusRequest {


    private Integer orderId;
    private String status;
    private String processingRemarks;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getProcessingRemarks() {
        return processingRemarks;
    }

    public void setProcessingRemarks(String processingRemarks) {
        this.processingRemarks = processingRemarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

