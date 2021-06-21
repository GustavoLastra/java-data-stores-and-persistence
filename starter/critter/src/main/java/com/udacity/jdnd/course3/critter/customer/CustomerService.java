package com.udacity.jdnd.course3.critter.customer;


import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.dtos.CustomerSaveDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO save(CustomerSaveDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        Customer savedCustomer = this.customerRepository.save(customer);
        return this.mapToCustomerDTO(savedCustomer);
    }

    public CustomerDTO mapToCustomerDTO(Customer savedCustomer) {
        CustomerDTO savedCustomerDto = new CustomerDTO();
        savedCustomerDto.setId(savedCustomer.getId());
        savedCustomerDto.setName(savedCustomer.getName());
        savedCustomerDto.setPhoneNumber(savedCustomer.getPhoneNumber());
        return savedCustomerDto;
    }

    public List<CustomerDTO> getCustomers() {
        Iterable<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDtoList = new ArrayList<CustomerDTO>();
        for (Customer customer : customers) {
            customerDtoList.add(this.mapToCustomerDTO(customer));
        }
        return customerDtoList;
    }
}
