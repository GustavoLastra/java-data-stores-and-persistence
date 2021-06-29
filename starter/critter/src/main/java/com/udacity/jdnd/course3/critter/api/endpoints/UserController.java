package com.udacity.jdnd.course3.critter.api.endpoints;

import com.udacity.jdnd.course3.critter.api.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.api.dtos.CustomerCreateDTO;
import com.udacity.jdnd.course3.critter.customer.CustomerService;
import com.udacity.jdnd.course3.critter.api.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.api.dtos.EmployeeAvailabilityDTO;
import com.udacity.jdnd.course3.critter.api.dtos.EmployeeSaveDTO;
import com.udacity.jdnd.course3.critter.employee.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService,EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerCreateDTO customerDTO){
        return customerService.save(customerDTO);
    }

    @GetMapping("/customer/{customerId}")
    public CustomerDTO getCustomer(@PathVariable long customerId){
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
       return customerService.getOwnerByPet(petId);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeSaveDTO employeeDTO) {
        return employeeService.save(employeeDTO);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        return employeeService.setAvailability(daysAvailable,employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeAvailabilityDTO employeeDTO) {
        return employeeService.findEmployeesForService(employeeDTO);
    }

}
