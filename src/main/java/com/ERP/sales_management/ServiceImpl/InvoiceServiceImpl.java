package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.InvoiceResponseDTO;
import com.ERP.sales_management.DTO.OrderItemResponse;
import com.ERP.sales_management.DTO.ProductDto;
import com.ERP.sales_management.Model.Customer;
import com.ERP.sales_management.Model.Invoice;
import com.ERP.sales_management.Model.SalesOrder;
import com.ERP.sales_management.Model.SalesOrderItem;
import com.ERP.sales_management.Repository.InvoiceRepository;
import com.ERP.sales_management.Repository.SalesOrderItemRepository;
import com.ERP.sales_management.Repository.SalesOrderRepository;
import com.ERP.sales_management.Response.SuccessResponse;
import com.ERP.sales_management.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderItemRepository salesOrderItemRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                              SalesOrderRepository salesOrderRepository,
                              SalesOrderItemRepository salesOrderItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderItemRepository = salesOrderItemRepository;
    }

    @Override
    public SuccessResponse<InvoiceResponseDTO> createInvoiceForOrder(String inputOrderId) {
        if (inputOrderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
    public SuccessResponse<InvoiceResponseDTO> createInvoiceForOrder(Integer orderId) {
        try {
            if (orderId == null) {
                throw new IllegalArgumentException("Order ID cannot be null.");
            }

            SalesOrder order = salesOrderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Sales order not found for ID: " + orderId));

            // Customer customer = order.getCustomer();
            // if (customer == null) {
            //     throw new RuntimeException("Customer not associated with this order.");
            // }

            List<SalesOrderItem> orderItems = salesOrderItemRepository.findBySalesOrderId(orderId);
            if (orderItems == null || orderItems.isEmpty()) {
                throw new RuntimeException("No items found for this sales order.");
            }

            double totalAmount = orderItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            Invoice invoice = new Invoice();
            invoice.setOrderId(order.getId());
            invoice.setInvoiceNo("INVNO-" + order.getId());
            // invoice.setCustomer(customer);
            invoice.setTotalAmount(totalAmount);
            invoice.setStatus("Completed");
            invoice.setIssuedDate(LocalDateTime.now());

            Invoice savedInvoice = invoiceRepository.save(invoice);

            // Prepare ProductDto instead of OrderItemResponse
            List<ProductDto> productDtos = orderItems.stream().map(item -> {
                ProductDto productDto = new ProductDto();
                productDto.setId(item.getProductId()); // assuming you have productId in SalesOrderItem
                productDto.setName(item.getProductName());
                productDto.setSku(item.getProductSku());
                productDto.setPrice(item.getPrice());
                productDto.setStockQuantity(item.getQuantity()); // this may vary depending on your actual logic
                return productDto;
            }).toList();

            InvoiceResponseDTO invoiceResponse = new InvoiceResponseDTO();
            invoiceResponse.setId(savedInvoice.getId());
            invoiceResponse.setOrderId(savedInvoice.getOrderId());
            invoiceResponse.setInvoiceNo(savedInvoice.getInvoiceNo());
            invoiceResponse.setTotalAmount(savedInvoice.getTotalAmount());
            invoiceResponse.setStatus(savedInvoice.getStatus());
            invoiceResponse.setIssuedDate(savedInvoice.getIssuedDate());
            invoiceResponse.setCustomer(savedInvoice.getCustomer());
            invoiceResponse.setItems(productDtos); // Set the list of products

            return new SuccessResponse<>(200, "Invoice generated successfully.", invoiceResponse);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice: " + e.getMessage());
        }

        SalesOrder order = salesOrderRepository.findByOrderNumber(inputOrderId)
                .orElseThrow(() -> new RuntimeException("Sales order not found with ID: " + inputOrderId));

        Customer customer = order.getCustomer();
        if (customer == null) {
            throw new RuntimeException("Customer not associated with this order.");
        }

        List<SalesOrderItem> orderItems = salesOrderItemRepository.findBySalesOrderOrderNumber(inputOrderId);
        if (orderItems.isEmpty()) {
            throw new RuntimeException("No items found for this sales order.");
        }

        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Generate new IDs
        String invoiceNo = generateRandomInvoiceNo();
        String orderId = generateRandomOrderId();

        // Optional: Check for duplicate invoiceNo or orderId
        if (invoiceRepository.existsByInvoiceNo(invoiceNo)) {
            throw new RuntimeException("Invoice number already exists: " + invoiceNo);
        }

        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        invoice.setInvoiceNo(invoiceNo);
        invoice.setCustomer(customer);
        invoice.setTotalAmount(totalAmount);
        invoice.setIssuedDate(LocalDateTime.now());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<ProductDto> productDtos = orderItems.stream().map(item -> {
            ProductDto dto = new ProductDto();
            dto.setId(item.getProductId());
            dto.setName(item.getProductName());
            dto.setSku(item.getProductSku());
            dto.setPrice(item.getPrice());
            dto.setStockQuantity(item.getQuantity());
            return dto;
        }).toList();

        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();
        responseDTO.setId(savedInvoice.getId());
        responseDTO.setOrderId(savedInvoice.getOrderId());
        responseDTO.setOrderStatus(order.getStatus());
        responseDTO.setInvoiceNo(savedInvoice.getInvoiceNo());
        responseDTO.setTotalAmount(savedInvoice.getTotalAmount());
        responseDTO.setIssuedDate(savedInvoice.getIssuedDate());
        responseDTO.setCustomer(savedInvoice.getCustomer());
        responseDTO.setItems(productDtos);

        return new SuccessResponse<>(200, "Invoice generated successfully.", responseDTO);
    }
    private String generateRandomOrderId() {
        int randomNum = new Random().nextInt(9000) + 1000; // 100–999
        return "ORD-" + randomNum;
    }

    private String generateRandomInvoiceNo() {
        int randomNum = new Random().nextInt(9000) + 1000; // 100–999
        return "INV-" + randomNum;
    }



    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Integer id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> getInvoicesByOrderId(String orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    @Override
    public Invoice updateInvoice(Integer id, Invoice invoice) {
        Optional<Invoice> existing = invoiceRepository.findById(id);
        if (existing.isPresent()) {
            invoice.setId(id);
            return invoiceRepository.save(invoice);
        }
        return null;
    }

    @Override
    public void deleteInvoice(Integer id) {
        invoiceRepository.deleteById(id);
    }
}
