package com.ERP.sales_management.Model;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "invoice_no", nullable = false, unique = true)
    private String invoiceNo;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private String status;

    @Column(name = "issued_date", nullable = false)
    private String issuedDate;
}
