package com.ERP.sales_management.Service;



import com.ERP.sales_management.Model.Invoice;
import com.ERP.sales_management.Response.SuccessResponse;

import java.util.List;

public interface InvoiceService {

//    Invoice createInvoice(Invoice invoice);
SuccessResponse<Invoice> createInvoiceForOrder(Integer orderId);

    List<Invoice> getAllInvoices();

    Invoice getInvoiceById(Integer id);

    List<Invoice> getInvoicesByOrderId(Integer orderId);

    Invoice updateInvoice(Integer id, Invoice invoice);

    void deleteInvoice(Integer id);
}
