package com.ERP.sales_management.Service;



import com.ERP.sales_management.Model.Invoice;

import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(Long id);

    List<Invoice> getInvoicesByOrderId(Long orderId);

    Invoice updateInvoice(Long id, Invoice invoice);

    void deleteInvoice(Long id);
}
