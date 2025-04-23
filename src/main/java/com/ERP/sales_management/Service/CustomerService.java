package com.ERP.sales_management.Service;

import com.ERP.sales_management.DTO.CreateCustomerRequest;
import com.ERP.sales_management.DTO.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(CreateCustomerRequest request);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(int id);
    CustomerDTO updateCustomer(int id, CreateCustomerRequest request);
    void deleteCustomer(int id);
}
