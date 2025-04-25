package com.ERP.sales_management.Controller;


import com.ERP.sales_management.Model.Payment;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales_management/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<Payment>> createPayment(@RequestBody Payment payment) {
        Payment created = paymentService.createPayment(payment);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Payment created successfully", created));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<Payment>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(new SuccessResponse<>(200,"All payments fetched", payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Payment>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Payment fetched", payment));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<SuccessResponse<List<Payment>>> getPaymentsByOrderId(@PathVariable Long orderId) {
        List<Payment> payments = paymentService.getPaymentsByOrderId(orderId);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Payments for order ID fetched", payments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Payment>> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Payment updated = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Payment updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Payment deleted", "Deleted ID: " + id));
    }
}
