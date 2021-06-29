package com.udacity.jdnd.course3.critter.employee;
import com.udacity.jdnd.course3.critter.dtos.EmployeeAvailabilityDTO;
import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dtos.EmployeeSaveDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public EmployeeDTO setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        Employee employeeUpdated = this.employeeRepository.save(employee);
        return this.mapToEmployeeDto(employeeUpdated);
    }

    public EmployeeDTO mapToEmployeeDto(Employee savedEmployee) {
        EmployeeDTO savedEmployeeDto = new EmployeeDTO();
        savedEmployeeDto.setId(savedEmployee.getId());
        savedEmployeeDto.setName(savedEmployee.getName());
        savedEmployeeDto.setDaysAvailable(savedEmployee.getDaysAvailable());
        savedEmployeeDto.setSkills(savedEmployee.getSkills());
        return savedEmployeeDto;
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeAvailabilityDTO employeeDTO) {

        Iterable<Employee> employees = this.employeeRepository.findByDaysAvailable(employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDtoList = new ArrayList<EmployeeDTO>();
        for (Employee employee : employees) {
            if  (employee.getSkills().containsAll(employeeDTO.getSkills()) ) {
                employeeDtoList.add(this.mapToEmployeeDto(employee));
            }
        }
        return employeeDtoList;
    }
}
