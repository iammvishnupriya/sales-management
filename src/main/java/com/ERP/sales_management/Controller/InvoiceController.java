package com.ERP.sales_management.Controller;


import com.ERP.sales_management.Model.Invoice;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales_management/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

<<<<<<< HEAD
//    @PostMapping
//    public ResponseEntity<SuccessResponse<Invoice>> createInvoice(@RequestBody Invoice invoice) {
//        Invoice created = invoiceService.createInvoice(invoice);
//        return ResponseEntity.ok(new SuccessResponse<>(200,"Invoice created successfully", created));
//    }
    @PostMapping("/{orderId}")
    public SuccessResponse<Invoice> createInvoice(@PathVariable Integer orderId) {
        return invoiceService.createInvoiceForOrder(orderId);
=======
    @PostMapping
    public ResponseEntity<SuccessResponse<Invoice>> createInvoice(@RequestBody Invoice invoice) {
        Invoice created = invoiceService.createInvoice(invoice);
        return ResponseEntity.ok(new SuccessResponse<>(200, "Invoice created successfully", created));
>>>>>>> f4471c5c534a9365c894a68efbe37d49c881891c
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<Invoice>>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(new SuccessResponse<>(200, "All invoices fetched", invoices));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Invoice>> getInvoiceById(@PathVariable Integer id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Invoice fetched", invoice));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<SuccessResponse<List<Invoice>>> getInvoicesByOrderId(@PathVariable Integer orderId) {
        List<Invoice> invoices = invoiceService.getInvoicesByOrderId(orderId);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Invoices for order ID fetched", invoices));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Invoice>> updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
        Invoice updated = invoiceService.updateInvoice(id, invoice);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Invoice updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<String>> deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(new SuccessResponse<>(200,"Invoice deleted", "Deleted ID: " + id));
    }
}
