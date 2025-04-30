package com.ERP.sales_management.Service;
import com.ERP.sales_management.DTO.InvoiceResponseDTO;
import com.ERP.sales_management.Model.Invoice;
import com.ERP.sales_management.Response.SuccessResponse;
import java.util.List;

public interface InvoiceService {

SuccessResponse<InvoiceResponseDTO> createInvoiceForOrder(String orderId);

    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(Integer id);

    List<Invoice> getInvoicesByOrderId(String orderId);

    Invoice updateInvoice(Integer id, Invoice invoice);

    void deleteInvoice(Integer id);
}
