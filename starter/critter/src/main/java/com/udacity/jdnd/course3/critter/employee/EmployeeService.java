package com.udacity.jdnd.course3.critter.employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO save(EmployeeSaveDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        Employee savedEmployee = this.employeeRepository.save(employee);
        return this.mapToEmployeeDto(savedEmployee);
    }

    public EmployeeDTO mapToEmployeeDto(Employee savedEmployee) {
        EmployeeDTO savedEmployeeDto = new EmployeeDTO();
        savedEmployeeDto.setId(savedEmployee.getId());
        savedEmployeeDto.setName(savedEmployee.getName());
        savedEmployeeDto.setDaysAvailable(savedEmployee.getDaysAvailable());
        savedEmployeeDto.setSkills(savedEmployee.getSkills());
        return savedEmployeeDto;
    }

    public List<EmployeeDTO> getCustomers() {
        Iterable<Employee> employees = this.employeeRepository.findAll();
        List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();
        for (Employee employee : employees) {
            employeeDtoList.add(this.mapToEmployeeDto(employee));
        }
        return employeeDtoList;
    }

    public EmployeeDTO getEmployee(Long employeeId) {
        Optional<Employee> employee = this.employeeRepository.findById(employeeId);
        return this.mapToEmployeeDto(employee.get());
    }
}
