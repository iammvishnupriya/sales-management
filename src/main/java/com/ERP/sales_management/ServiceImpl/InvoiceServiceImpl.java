package com.ERP.sales_management.ServiceImpl;


import com.ERP.sales_management.Model.Invoice;
import com.ERP.sales_management.Repository.InvoiceRepository;
import com.ERP.sales_management.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Invoice> getInvoicesByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        Optional<Invoice> existing = invoiceRepository.findById(id);
        if (existing.isPresent()) {
            invoice.setId(id);
            return invoiceRepository.save(invoice);
        }
        return null;
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
