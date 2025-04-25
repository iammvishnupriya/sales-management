package com.ERP.sales_management.Model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String status;

    @Column(name = "tracking_no", nullable = false, unique = true)
    private String trackingNo;
}
