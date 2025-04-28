package com.ERP.sales_management.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuccessResponse<T> implements Serializable {
<<<<<<< HEAD

    private int statusCode = 200;
    private String statusMessage = "Success";
    private transient T data;
    public SuccessResponse(){}
=======
    private int statusCode;
    private String statusMessage;
    private T data;
    
    public SuccessResponse() {}
    
>>>>>>> f4471c5c534a9365c894a68efbe37d49c881891c
    public SuccessResponse(int statusCode, String statusMessage, T data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
