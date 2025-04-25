package com.ERP.sales_management.Service;


import com.ERP.sales_management.Model.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Payment payment);

    List<Payment> getAllPayments();

    Payment getPaymentById(Long id);

    List<Payment> getPaymentsByOrderId(Long orderId);

    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);
}
