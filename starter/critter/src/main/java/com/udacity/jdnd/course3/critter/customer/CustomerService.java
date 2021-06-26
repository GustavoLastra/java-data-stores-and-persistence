package com.udacity.jdnd.course3.critter.customer;


import com.udacity.jdnd.course3.critter.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.dtos.CustomerSaveDTO;
import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public List<CustomerDTO> getCustomers() {
        Iterable<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDtoList = new ArrayList<CustomerDTO>();
        for (Customer customer : customers) {
            customerDtoList.add(this.mapToCustomerDTO(customer));
        }
        return customerDtoList;
    }

    public CustomerDTO getCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        return this.mapToCustomerDTO(customer);
    }

    public CustomerDTO mapToCustomerDTO(Customer customer) {
        CustomerDTO savedCustomerDto = new CustomerDTO();
        savedCustomerDto.setId(customer.getId());
        savedCustomerDto.setName(customer.getName());
        savedCustomerDto.setPhoneNumber(customer.getPhoneNumber());
        Set<Pet> pets =  customer.getPets();
        List<Long> petsIds = new ArrayList<>();
        if (pets!= null) {
            pets.forEach((pet) -> petsIds.add(pet.getId()));
        }

        savedCustomerDto.setPetIds(petsIds);
        return savedCustomerDto;
    }
}
