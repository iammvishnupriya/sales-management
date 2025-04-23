package com.ERP.sales_management.ServiceImpl;

import com.ERP.sales_management.DTO.CreateCustomerRequest;
import com.ERP.sales_management.DTO.CustomerDTO;
import com.ERP.sales_management.Model.Customer;
import com.ERP.sales_management.Repository.CustomerRepository;
import com.ERP.sales_management.Service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;

    public CustomerServiceImpl(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        if (customerRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Customer already exists with email: " + request.getEmail());
        }

        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        customer = customerRepo.save(customer);
        return mapToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();

        for (Customer customer : customers) {
            dtos.add(mapToDTO(customer));
        }

        return dtos;
    }

    @Override
    public CustomerDTO getCustomerById(int id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(int id, CreateCustomerRequest request) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        return mapToDTO(customerRepo.save(customer));
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;
    }
}
